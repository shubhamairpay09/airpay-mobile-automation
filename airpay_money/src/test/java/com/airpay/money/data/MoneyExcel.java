package com.airpay.money.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Functional input for each module lives in money-functional-data.xlsx.
 * One worksheet per module. Change the Value cell for a test case to change what the test types.
 */
public final class MoneyExcel {

    private static final Pattern CASE_TAG = Pattern.compile("@(FHC|DG|FD|MF|NPS|CALC|PROF|DASH|NW)_TC_(\\d+)");
    private static final Pattern HOME_TAG = Pattern.compile("@HOME_(\\d+)");
    private static final Map<String, String> SHEETS = Map.of(
            "FHC", "FHC",
            "DG", "Digital Gold",
            "FD", "Fixed Deposit",
            "MF", "Mutual Fund",
            "NPS", "NPS",
            "CALC", "Calculator",
            "PROF", "Profile settings",
            "DASH", "Dashboard",
            "NW", "Sync Net Worth");

    private static final Map<String, String> CELLS = new LinkedHashMap<>();
    private static final Map<String, List<String[]>> LISTS = new LinkedHashMap<>();
    private static final ThreadLocal<String> SHEET = new ThreadLocal<>();
    private static final ThreadLocal<String> CASE = new ThreadLocal<>();
    private static boolean loaded;

    private MoneyExcel() {
    }

    public static void bind(Collection<String> tags) {
        SHEET.remove();
        CASE.remove();
        if (tags == null) {
            return;
        }
        for (String tag : tags) {
            String normalized = tag.startsWith("@") ? tag : "@" + tag;
            Matcher match = CASE_TAG.matcher(normalized);
            if (match.matches()) {
                SHEET.set(SHEETS.get(match.group(1)));
                CASE.set("TC_" + match.group(2));
                return;
            }
            Matcher home = HOME_TAG.matcher(normalized);
            if (home.matches()) {
                SHEET.set("Dashboard");
                CASE.set("TC_" + home.group(1));
                return;
            }
        }
    }

    /** Value for the running test case. A blank cell falls back to the DEFAULT row, then to {@code fallback}. */
    public static String text(String key, String fallback) {
        load();
        String sheet = SHEET.get();
        String testCase = CASE.get();
        if (sheet == null || testCase == null) {
            return fallback == null ? "" : fallback;
        }
        String specific = CELLS.get(id(sheet, testCase, key));
        if (specific != null && !specific.isBlank()) {
            return specific;
        }
        String shared = CELLS.get(id(sheet, "DEFAULT", key));
        if (shared != null && !shared.isBlank()) {
            return shared;
        }
        return fallback == null ? "" : fallback;
    }

    /** Session PIN from the Login sheet. Every module uses this row. */
    public static String pin(String fallback) {
        load();
        String value = CELLS.get(id("Login", "DEFAULT", "Pin"));
        if (value != null && !value.isBlank()) {
            return value;
        }
        return fallback == null ? "" : fallback;
    }

    /** Repeated rows for the running case, or the ALL rows when this case has none. */
    public static List<String[]> rows(String key) {
        load();
        String sheet = SHEET.get();
        String testCase = CASE.get();
        if (sheet == null) {
            return List.of();
        }
        List<String[]> specific = LISTS.get(id(sheet, testCase == null ? "ALL" : testCase, key));
        if (specific != null && !specific.isEmpty()) {
            return specific;
        }
        List<String[]> shared = LISTS.get(id(sheet, "ALL", key));
        return shared == null ? List.of() : shared;
    }

    public static void main(String[] args) throws Exception {
        Path path = Path.of(args.length > 0 ? args[0] : "src/test/resources/testdata/money-functional-data.xlsx");
        Files.createDirectories(path.getParent());
        if (Files.isRegularFile(path)) {
            ensureLoginPin(path);
            System.out.println("Login PIN is on " + path.toAbsolutePath());
            return;
        }
        try (Workbook book = new XSSFWorkbook(); OutputStream out = Files.newOutputStream(path)) {
            fill(book);
            book.write(out);
        }
        System.out.println("Wrote " + path.toAbsolutePath());
    }

    /** Adds the Login sheet PIN without rewriting the other module rows. */
    public static void ensureLoginPin(Path path) throws Exception {
        Workbook book;
        try (InputStream in = Files.newInputStream(path)) {
            book = new XSSFWorkbook(in);
        }
        Sheet sheet = book.getSheet("Login");
        if (sheet == null) {
            sheet = book.createSheet("Login");
            header(sheet);
        }
        DataFormatter formatter = new DataFormatter();
        boolean found = false;
        for (Row row : sheet) {
            if ("DEFAULT".equals(cell(formatter, row, 0)) && "Pin".equals(cell(formatter, row, 1))) {
                if (row.getCell(2) == null) {
                    row.createCell(2);
                }
                row.getCell(2).setCellValue("880272");
                found = true;
                break;
            }
        }
        if (!found) {
            Row row = sheet.createRow(Math.max(sheet.getLastRowNum(), 0) + 1);
            row.createCell(0).setCellValue("DEFAULT");
            row.createCell(1).setCellValue("Pin");
            row.createCell(2).setCellValue("880272");
            row.createCell(3).setCellValue("Used when the app asks for the Airpay Money PIN");
        }
        try (OutputStream out = Files.newOutputStream(path)) {
            book.write(out);
        }
        book.close();
    }

    private static void load() {
        if (loaded) {
            return;
        }
        loaded = true;
        Path file = locate();
        if (file == null) {
            return;
        }
        ingest(file);
        String pin = CELLS.get(id("Login", "DEFAULT", "Pin"));
        Path login = file.getParent().resolve("money-login.xlsx");
        if ((pin == null || pin.isBlank()) && Files.isRegularFile(login)) {
            ingest(login);
        }
    }

    private static void ingest(Path file) {
        DataFormatter formatter = new DataFormatter();
        try (InputStream in = Files.newInputStream(file); Workbook book = new XSSFWorkbook(in)) {
            for (int s = 0; s < book.getNumberOfSheets(); s++) {
                Sheet sheet = book.getSheetAt(s);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    String testCase = cell(formatter, row, 0);
                    String key = cell(formatter, row, 1);
                    String value = cell(formatter, row, 2);
                    String value2 = cell(formatter, row, 3);
                    if (testCase.isBlank() || key.isBlank()) {
                        continue;
                    }
                    CELLS.put(id(sheet.getSheetName(), testCase, key), value);
                    LISTS.computeIfAbsent(id(sheet.getSheetName(), testCase, key), ignored -> new ArrayList<>())
                            .add(new String[] {value, value2});
                }
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Could not read " + file, ex);
        }
    }

    private static Path locate() {
        Path[] candidates = {
                Path.of("src/test/resources/testdata/money-functional-data.xlsx"),
                Path.of("airpay_money/src/test/resources/testdata/money-functional-data.xlsx")
        };
        for (Path candidate : candidates) {
            if (!Files.isRegularFile(candidate)) {
                continue;
            }
            Path updated = candidate.resolveSibling(candidate.getFileName().toString() + ".tmp");
            try {
                if (Files.isRegularFile(updated)
                        && Files.getLastModifiedTime(updated).compareTo(Files.getLastModifiedTime(candidate)) > 0) {
                    return updated;
                }
            } catch (Exception ignored) {
                // The saved workbook is still readable.
            }
            return candidate;
        }
        return null;
    }

    private static String id(String sheet, String testCase, String key) {
        return sheet + "|" + testCase + "|" + key;
    }

    private static String cell(DataFormatter formatter, Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    private static void fill(Workbook book) {
        header(book.createSheet("FHC"));
        put(book, "FHC", "DEFAULT", "Pincode", "247001", "");
        put(book, "FHC", "DEFAULT", "City", "Saharanpur", "");
        put(book, "FHC", "DEFAULT", "State", "Uttar Pradesh", "");
        put(book, "FHC", "DEFAULT", "Income", "100000", "");
        put(book, "FHC", "DEFAULT", "Expenses", "40000", "");
        put(book, "FHC", "DEFAULT", "Principal", "500000", "");
        put(book, "FHC", "DEFAULT", "EMI", "10000", "");
        put(book, "FHC", "DEFAULT", "InterestRate", "12", "");
        put(book, "FHC", "DEFAULT", "CurrentValue", "100000", "");
        put(book, "FHC", "DEFAULT", "BankBalance", "100000", "");
        put(book, "FHC", "DEFAULT", "LoanType", "Education Loan", "");
        put(book, "FHC", "DEFAULT", "Investment", "Public Provident Fund (PPF)", "");
        put(book, "FHC", "TC_012", "LoanSearchHome", "Home", "");
        put(book, "FHC", "TC_012", "LoanSearchPersonal", "Personal", "");
        put(book, "FHC", "TC_012", "LoanSearchGold", "Gold", "");

        header(book.createSheet("Digital Gold"));
        put(book, "Digital Gold", "TC_004", "BuyRupees", "100", "");
        put(book, "Digital Gold", "TC_005", "Grams", "1.0", "");
        put(book, "Digital Gold", "TC_007", "BuyRupees", "100", "");
        for (int tc = 8; tc <= 21; tc++) {
            if (tc == 9) {
                continue;
            }
            put(book, "Digital Gold", "TC_" + String.format(Locale.ROOT, "%03d", tc), "BuyRupees", "200", "");
        }

        header(book.createSheet("Fixed Deposit"));
        put(book, "Fixed Deposit", "DEFAULT", "SuryodayRate", "8.25", "");
        put(book, "Fixed Deposit", "DEFAULT", "MahindraRate", "7.50", "");
        put(book, "Fixed Deposit", "DEFAULT", "BajajRate", "7.40", "");
        put(book, "Fixed Deposit", "DEFAULT", "UnityRate", "8.00", "");
        put(book, "Fixed Deposit", "DEFAULT", "SeniorRate", "8.5", "");
        put(book, "Fixed Deposit", "DEFAULT", "Payout", "Monthly", "");
        put(book, "Fixed Deposit", "DEFAULT", "PayoutDefault", "Maturity", "");
        put(book, "Fixed Deposit", "TC_004", "Tenure", "0-1 year", "");
        put(book, "Fixed Deposit", "TC_005", "Tenure", "1-3 year", "");
        put(book, "Fixed Deposit", "TC_006", "Tenure", ">3 years", "");
        put(book, "Fixed Deposit", "TC_010", "Amount", "50000", "");

        header(book.createSheet("Mutual Fund"));
        put(book, "Mutual Fund", "TC_003", "GoalName", "bike", "");
        put(book, "Mutual Fund", "TC_003", "GoalAmount", "50000", "");
        put(book, "Mutual Fund", "TC_003", "GoalTenure", "3 year", "");
        put(book, "Mutual Fund", "TC_007", "ListName", "Long Term Investment", "");
        put(book, "Mutual Fund", "TC_008", "ListName", "Tax Saving Fund", "");
        put(book, "Mutual Fund", "TC_009", "ListName", "Emergency Fund", "");
        put(book, "Mutual Fund", "TC_010", "ListName", "Bluechip Fund", "");
        put(book, "Mutual Fund", "TC_011", "ListName", "Nifty 50", "");
        put(book, "Mutual Fund", "TC_012", "ListName", "Nifty Mid Cap", "");
        put(book, "Mutual Fund", "TC_013", "ListName", "Nifty 100", "");
        put(book, "Mutual Fund", "TC_014", "ListName", "Nifty Small Cap 50", "");
        put(book, "Mutual Fund", "TC_015", "ListName", "Nifty Large Midcap 250", "");
        put(book, "Mutual Fund", "TC_016", "ListName", "Global Fund", "");
        put(book, "Mutual Fund", "TC_020", "Search", "HDFC", "");

        header(book.createSheet("Calculator"));
        String[][] calculators = {
                {"EMI Calculator", "EMI Calculator"},
                {"SIP Calculator", "SIP Calculator"},
                {"Lumpsum Calculator", "Lumpsum Calculator"},
                {"PPF Calculator", "PPF Calculator"},
                {"EPF Calculator", "EPF Calculator"},
                {"FD Calculator", "Fixed Deposit"},
                {"NPS Calculator", "NPS Calculator"},
                {"Retirement Calculator", "Retirement Calculator"},
                {"Crorepati Calculator", "Crorepati Calculator"},
                {"Term Insurance Calculator", "Retirement Calculator"},
                {"Buy vs Rent Calculator", "Buy Vs Rent"},
                {"Taxation Indian Equity", "Taxation Indian Equity"},
                {"Taxation International Equity", "Taxation International Equity"},
                {"Inflation Calculator", "Inflation Calculator"},
                {"Budget Calculator", "Budget Calculator"}
        };
        for (String[] pair : calculators) {
            put(book, "Calculator", "ALL", "Option", pair[0], pair[1]);
        }

        header(book.createSheet("NPS"));
        put(book, "NPS", "ALL", "Note", "TC_001 to TC_010 check navigation only. No amount is typed.", "");

        header(book.createSheet("Profile settings"));
        put(book, "Profile settings", "ALL", "Skipped", "TC_023", "No steps on the sheet");
        put(book, "Profile settings", "ALL", "Skipped", "TC_025", "Log Out Yes is not tapped");
        put(book, "Profile settings", "ALL", "Skipped", "TC_027", "Log Out Yes is not tapped");

        header(book.createSheet("Dashboard"));
        put(book, "Dashboard", "ALL", "Note", "TC_001 to TC_008 check the dashboard. No amount is typed.", "");

        header(book.createSheet("Sync Net Worth"));
        put(book, "Sync Net Worth", "ALL", "Note",
                "The sheet listed Allow Consent twice. That case is automated once as NW_TC.", "");

        header(book.createSheet("Login"));
        put(book, "Login", "DEFAULT", "Pin", "880272", "Used when the app asks for the Airpay Money PIN");
    }

    private static void header(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("TestCase");
        row.createCell(1).setCellValue("Key");
        row.createCell(2).setCellValue("Value");
        row.createCell(3).setCellValue("Value2");
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 5000);
        sheet.setColumnWidth(2, 14000);
        sheet.setColumnWidth(3, 14000);
    }

    private static void put(Workbook book, String sheetName, String testCase, String key, String value, String value2) {
        Sheet sheet = book.getSheet(sheetName);
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(testCase);
        row.createCell(1).setCellValue(key);
        row.createCell(2).setCellValue(value);
        row.createCell(3).setCellValue(value2);
    }
}
