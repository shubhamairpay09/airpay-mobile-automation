package com.airpay.capital.data;

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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Functional input for Capital lives in capital-functional-data.xlsx.
 * One worksheet per module. Change the Value cell for a test case to change what the test types.
 * Tags map as {@code @CA_LO_001} → sheet Login / case CA_LO_001 (or TC alias LO_001).
 */
public final class CapitalExcel {

    private static final Pattern CASE_TAG = Pattern.compile(
            "@CA_(LO|PL|HL|LAP|GL|CC|MF)_(\\d+)");

    private static final Map<String, String> SHEETS = Map.of(
            "LO", "Login",
            "PL", "Personal Loan",
            "HL", "Home Loan",
            "LAP", "Loan Against Property",
            "GL", "Gold Loan",
            "CC", "Credit Card",
            "MF", "Muthoot Fincorp");

    private static final Map<String, String> CELLS = new LinkedHashMap<>();
    private static final Map<String, List<String[]>> LISTS = new LinkedHashMap<>();
    private static final ThreadLocal<String> SHEET = new ThreadLocal<>();
    private static final ThreadLocal<String> CASE = new ThreadLocal<>();
    private static boolean loaded;

    private CapitalExcel() {
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
                String module = match.group(1);
                String num = match.group(2);
                SHEET.set(SHEETS.get(module));
                CASE.set("CA_" + module + "_" + String.format("%03d", Integer.parseInt(num)));
                return;
            }
        }
    }

    public static String boundSheet() {
        return SHEET.get();
    }

    public static String boundCase() {
        return CASE.get();
    }

    /** Value for the running test case. Blank falls back to DEFAULT, then {@code fallback}. */
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
        String loginShared = CELLS.get(id("Login", "DEFAULT", key));
        if (loginShared != null && !loginShared.isBlank()) {
            return loginShared;
        }
        return fallback == null ? "" : fallback;
    }

    public static String mobile(String fallback) {
        return text("Mobile", fallback);
    }

    public static String otp(String fallback) {
        return text("OTP", fallback);
    }

    public static String invalidOtp(String fallback) {
        return text("InvalidOTP", fallback);
    }

    public static String expiredOtp(String fallback) {
        return text("ExpiredOTP", fallback);
    }

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
        Path path = Path.of(args.length > 0 ? args[0]
                : "airpay_capital/src/test/resources/testdata/capital-functional-data.xlsx");
        Files.createDirectories(path.getParent());
        try (Workbook book = new XSSFWorkbook(); OutputStream out = Files.newOutputStream(path)) {
            fill(book);
            book.write(out);
        }
        System.out.println("Wrote " + path.toAbsolutePath());
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
                Path.of("src/test/resources/testdata/capital-functional-data.xlsx"),
                Path.of("airpay_capital/src/test/resources/testdata/capital-functional-data.xlsx")
        };
        for (Path candidate : candidates) {
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
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
        header(book.createSheet("Login"));
        put(book, "Login", "DEFAULT", "Mobile", "8802729089", "Real device number — OTP is read from notifications");
        put(book, "Login", "DEFAULT", "OTP", "", "Leave blank to read OTP from phone notifications");
        put(book, "Login", "DEFAULT", "InvalidOTP", "0000", "Used by CA_LO_002");
        put(book, "Login", "DEFAULT", "ExpiredOTP", "9999", "Used by CA_LO_003 — supply a known expired OTP");
        put(book, "Login", "CA_LO_001", "Summary", "Verify login using a mobile number", "");
        put(book, "Login", "CA_LO_002", "Summary", "Verify login with invalid OTP", "");
        put(book, "Login", "CA_LO_003", "Summary", "Verify login with an expired OTP", "");
        put(book, "Login", "CA_LO_004", "Summary", "Verify the Edit option on the OTP verification page", "");
        put(book, "Login", "CA_LO_005", "Summary", "Verify login using the newly resent OTP", "");

        String[] products = {
                "Personal Loan", "Home Loan", "Loan Against Property", "Gold Loan", "Credit Card"
        };
        String[][] productCases = {
                {"Personal Loan", "CA_PL_001", "Salaried"},
                {"Personal Loan", "CA_PL_002", "Self-Employed"},
                {"Home Loan", "CA_HL_001", "Salaried"},
                {"Home Loan", "CA_HL_002", "Self-Employed"},
                {"Loan Against Property", "CA_LAP_001", "Salaried"},
                {"Loan Against Property", "CA_LAP_002", "Self-Employed"},
                {"Gold Loan", "CA_GL_001", "Salaried"},
                {"Gold Loan", "CA_GL_002", "Self-Employed"},
                {"Credit Card", "CA_CC_001", "Salaried"},
                {"Credit Card", "CA_CC_002", "Self-Employed"}
        };

        for (String product : products) {
            header(book.createSheet(product));
            putDefaults(book, product);
        }
        for (String[] row : productCases) {
            put(book, row[0], row[1], "Occupation", row[2], "");
            put(book, row[0], row[1], "Product", row[0], "");
        }

        header(book.createSheet("Muthoot Fincorp"));
        putDefaults(book, "Muthoot Fincorp");
        put(book, "Muthoot Fincorp", "DEFAULT", "LenderName", "Muthoot", "Fragment used to locate the offer card");
        put(book, "Muthoot Fincorp", "DEFAULT", "Occupation", "Salaried", "");
        put(book, "Muthoot Fincorp", "DEFAULT", "Product", "Personal Loan", "Product journey that surfaces Muthoot");
        put(book, "Muthoot Fincorp", "CA_MF_001", "OfferTier", "BRE1", "");
        put(book, "Muthoot Fincorp", "CA_MF_002", "OfferTier", "BRE2", "");
    }

    private static void putDefaults(Workbook book, String sheet) {
        put(book, sheet, "DEFAULT", "PAN", "ABCDE1234F", "");
        put(book, sheet, "DEFAULT", "Name", "Test User", "");
        put(book, sheet, "DEFAULT", "Email", "test.user@airpay.example", "");
        put(book, sheet, "DEFAULT", "LoanAmount", "500000", "");
        put(book, sheet, "DEFAULT", "MonthlyIncome", "75000", "");
        put(book, sheet, "DEFAULT", "MonthlySalary", "75000", "");
        put(book, sheet, "DEFAULT", "ExistingEmi", "0", "");
    }

    private static void header(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("TestCase");
        row.createCell(1).setCellValue("Key");
        row.createCell(2).setCellValue("Value");
        row.createCell(3).setCellValue("Value2");
        sheet.setColumnWidth(0, 5000);
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
