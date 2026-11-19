package com.airpay.capital.data;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
 * Functional input for Capital lives in capital-functional-data.xlsx.
 * One worksheet per module. DEFAULT applies to every case on that sheet.
 * A row whose TestCase is CA_HL_001 overrides only that key for that case.
 * A blank Value is ignored, so the test keeps the DEFAULT and then the code fallback.
 * Key lookup ignores case, so PAN and pan are the same field.
 * Tags map as {@code @CA_HL_001} → sheet Home Loan / case CA_HL_001.
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
                    if (row.getRowNum() == 0 || isGuide(sheet.getSheetName())) {
                        continue;
                    }
                    String testCase = cell(formatter, row, 0);
                    String key = cell(formatter, row, 1);
                    String value = cell(formatter, row, 2);
                    String value2 = cell(formatter, row, 3);
                    if (testCase.isBlank() || key.isBlank() || testCase.startsWith("#")) {
                        continue;
                    }
                    if (value.isBlank()) {
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

    private static boolean isGuide(String sheetName) {
        return sheetName != null && sheetName.trim().equalsIgnoreCase("How to use");
    }

    private static String id(String sheet, String testCase, String key) {
        String caseId = testCase == null ? "" : testCase.trim();
        if (caseId.equalsIgnoreCase("DEFAULT")) {
            caseId = "DEFAULT";
        } else {
            caseId = caseId.toUpperCase(Locale.ROOT);
        }
        String sheetId = sheet == null ? "" : sheet.trim().toLowerCase(Locale.ROOT);
        String keyId = key == null ? "" : key.trim().toLowerCase(Locale.ROOT);
        return sheetId + "|" + caseId + "|" + keyId;
    }

    private static String cell(DataFormatter formatter, Row row, int index) {
        Cell cell = row.getCell(index);
        return cell == null ? "" : formatter.formatCellValue(cell).trim();
    }

    private static void fill(Workbook book) {
        guide(book);
        header(book.createSheet("Login"));
        put(book, "Login", "DEFAULT", "Mobile", "8802729089",
                "10-digit number typed on the login screen. Change this cell to use another number.");
        put(book, "Login", "DEFAULT", "OTP", "",
                "Leave blank. The bat file sets capital.test.otp=1234. This cell is used only when that property is empty and the SMS cannot be read.");
        put(book, "Login", "DEFAULT", "InvalidOTP", "0000",
                "Typed by CA_LO_002. The user must stay on Verify OTP.");
        put(book, "Login", "DEFAULT", "ExpiredOTP", "9999",
                "Typed by CA_LO_003. The user must stay on Verify OTP.");
        put(book, "Login", "CA_LO_001", "Summary", "Verify login using a mobile number",
                "Welcome, mobile, consent, Send OTP, valid OTP, Home.");
        put(book, "Login", "CA_LO_002", "Summary", "Verify login with invalid OTP",
                "Uses InvalidOTP. Does not open Home.");
        put(book, "Login", "CA_LO_003", "Summary", "Verify login with an expired OTP",
                "Uses ExpiredOTP. Does not open Home.");
        put(book, "Login", "CA_LO_004", "Summary", "Verify the Edit option on the OTP verification page",
                "Edit returns to the mobile number screen.");
        put(book, "Login", "CA_LO_005", "Summary", "Verify login using the newly resent OTP",
                "Resend, then the same valid OTP, then Home.");

        String[][] productCases = {
                {"Personal Loan", "CA_PL_001", "Salaried", "Verify the Personal Loan journey for a Salaried employee"},
                {"Personal Loan", "CA_PL_002", "Self-Employed", "Verify the Personal Loan journey for a Self-Employed customer"},
                {"Home Loan", "CA_HL_001", "Salaried", "Verify the Home Loan journey for a Salaried employee"},
                {"Home Loan", "CA_HL_002", "Self-Employed", "Verify the Home Loan journey for a Self-Employed customer"},
                {"Loan Against Property", "CA_LAP_001", "Salaried", "Verify the LAP journey for a Salaried employee"},
                {"Loan Against Property", "CA_LAP_002", "Self-Employed", "Verify the LAP journey for a Self-Employed customer"},
                {"Gold Loan", "CA_GL_001", "Salaried", "Verify the Gold Loan journey for a Salaried employee"},
                {"Gold Loan", "CA_GL_002", "Self-Employed", "Verify the Gold Loan journey for a Self-Employed customer"},
                {"Credit Card", "CA_CC_001", "Salaried", "Verify the Credit Card journey for a Salaried employee"},
                {"Credit Card", "CA_CC_002", "Self-Employed", "Verify the Credit Card journey for a Self-Employed customer"}
        };
        String current = "";
        for (String[] row : productCases) {
            if (!current.equals(row[0])) {
                current = row[0];
                header(book.createSheet(current));
                putDefaults(book, current);
            }
            put(book, row[0], row[1], "Summary", row[3],
                    "Scenario title. Not typed into the app.");
            put(book, row[0], row[1], "Occupation", row[2],
                    "Salaried or Self-Employed. This row selects the radio for this case only.");
            put(book, row[0], row[1], "Product", row[0],
                    "Home-grid card title. Must match the label on the phone.");
        }

        header(book.createSheet("Muthoot Fincorp"));
        putDefaults(book, "Muthoot Fincorp");
        put(book, "Muthoot Fincorp", "DEFAULT", "LenderName", "Muthoot",
                "Text used to find the offer card. Change this if the lender label changes.");
        put(book, "Muthoot Fincorp", "DEFAULT", "Occupation", "Salaried",
                "Used when a case does not set its own Occupation.");
        put(book, "Muthoot Fincorp", "DEFAULT", "Product", "Personal Loan",
                "Journey that shows the Muthoot offer. Change the card title here if the path changes.");
        put(book, "Muthoot Fincorp", "CA_MF_001", "Summary", "Verify the Muthoot FinCorp journey for a BRE1 offer",
                "Same form data as DEFAULT, then opens the lender named in LenderName.");
        put(book, "Muthoot Fincorp", "CA_MF_001", "OfferTier", "BRE1",
                "Label for this case. Not typed unless a screen asks for it.");
        put(book, "Muthoot Fincorp", "CA_MF_002", "Summary", "Verify the Muthoot FinCorp journey for a BRE2 offer",
                "Add an Occupation row on CA_MF_002 if this case should choose Self-Employed.");
        put(book, "Muthoot Fincorp", "CA_MF_002", "OfferTier", "BRE2",
                "Label for this case. Not typed unless a screen asks for it.");

        for (int s = 0; s < book.getNumberOfSheets(); s++) {
            Sheet sheet = book.getSheetAt(s);
            if (sheet.getLastRowNum() >= 1 && !isGuide(sheet.getSheetName())) {
                sheet.setAutoFilter(new CellRangeAddress(0, sheet.getLastRowNum(), 0, 3));
            }
        }
    }

    private static void guide(Workbook book) {
        Sheet sheet = book.createSheet("How to use");
        header(sheet);
        String[][] rows = {
                {"#", "Rule", "Change the Value cell. Do not rename the Key.",
                        "The test reads this workbook at the start of the run."},
                {"#", "DEFAULT", "Applies to every case on that sheet.",
                        "Edit DEFAULT when every Salaried and Self-Employed case should type the same PAN, name, email, or amount."},
                {"#", "One case", "Add a row with TestCase CA_HL_001 and the key you want to change.",
                        "Example: CA_HL_001 / LoanAmount / 2500000 types a different amount only for Home Loan salaried. Other keys still come from DEFAULT."},
                {"#", "Blank value", "Leave Value empty to keep the DEFAULT.",
                        "A blank cell is ignored. The test then uses DEFAULT, and only then the value written in code."},
                {"#", "Keys", "PAN, Name, Email, LoanAmount, Gender, DateOfBirthDay, DateOfBirthMonth, DateOfBirthYear, Occupation, Product.",
                        "Also MonthlyIncome, MonthlySalary, ExistingEmi, SalaryMode, EmployerCategory, ExperienceYears, ExperienceMonths, Pincode, House, Street, City, State, LenderName. Key matching ignores case."},
                {"#", "Gender", "Male, Female, or Others.",
                        "This is the option tapped in the gender list."},
                {"#", "Date of birth", "DateOfBirthDay, DateOfBirthMonth, and DateOfBirthYear.",
                        "The test opens the calendar, taps the year, moves to the month, then taps the day. Example: 16, May, 1994."},
                {"#", "Login OTP", "Leave OTP blank.",
                        "scripts\\capital\\functional.bat sets capital.test.otp=1234. Fill OTP here only when that property is not set and the SMS cannot be read."}
        };
        for (String[] row : rows) {
            put(book, "How to use", row[0], row[1], row[2], row[3]);
        }
        sheet.setColumnWidth(1, 22 * 256);
    }

    private static void putDefaults(Workbook book, String sheet) {
        put(book, sheet, "DEFAULT", "PAN", "AQIPV5654R",
                "Typed into Enter PAN. 5 letters, 4 digits, 1 letter.");
        put(book, sheet, "DEFAULT", "Name", "Shubham Verma",
                "Typed into Name as per PAN.");
        put(book, sheet, "DEFAULT", "Email", "shubham.verma.inform@gmail.com",
                "Typed into Enter email id.");
        put(book, sheet, "DEFAULT", "LoanAmount", "500000",
                "Typed into Desired loan amount. Digits only. The phone may show it as 5,00,000.");
        put(book, sheet, "DEFAULT", "Gender", "Male",
                "Male, Female, or Others. This option is tapped after the identity fields.");
        put(book, sheet, "DEFAULT", "DateOfBirthDay", "16",
                "Day tapped on the calendar after the year and month are selected.");
        put(book, sheet, "DEFAULT", "DateOfBirthMonth", "May",
                "Month name on the calendar. Use January through December.");
        put(book, sheet, "DEFAULT", "DateOfBirthYear", "1994",
                "Year tapped in the calendar year list.");
        put(book, sheet, "DEFAULT", "MonthlyIncome", "75000",
                "Typed when a monthly income field is on screen.");
        put(book, sheet, "DEFAULT", "MonthlySalary", "75000",
                "Typed on the salaried income screen. Falls back to MonthlyIncome when this cell is blank.");
        put(book, sheet, "DEFAULT", "ExistingEmi", "0",
                "Typed into Existing EMI on Work Details.");
        put(book, sheet, "DEFAULT", "SalaryMode", "Bank credit",
                "Option tapped for Mode of salary credit. Cash, Cheque, or Bank credit.");
        put(book, sheet, "DEFAULT", "EmployerCategory", "Pvt ltd co",
                "Option tapped for Employer Category.");
        put(book, sheet, "DEFAULT", "ExperienceYears", "5",
                "Years tapped for Total Work Experience.");
        put(book, sheet, "DEFAULT", "ExperienceMonths", "0",
                "Months tapped for Total Work Experience.");
        put(book, sheet, "DEFAULT", "Pincode", "110001",
                "Typed into the address pincode. A valid 6-digit code so city and state can fill.");
        put(book, sheet, "DEFAULT", "House", "12A",
                "Typed into House/Building/Apartment.");
        put(book, sheet, "DEFAULT", "Street", "MG Road",
                "Typed into Street/Road/Lane.");
        put(book, sheet, "DEFAULT", "City", "New Delhi",
                "Typed into City when the pincode does not fill it.");
        put(book, sheet, "DEFAULT", "State", "Delhi",
                "Typed into State when the pincode does not fill it.");
    }

    private static void header(Sheet sheet) {
        Workbook book = sheet.getWorkbook();
        CellStyle headerStyle = book.createCellStyle();
        Font font = book.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        Row row = sheet.createRow(0);
        String[] titles = {"TestCase", "Key", "Value", "Notes"};
        for (int i = 0; i < titles.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(headerStyle);
        }
        sheet.createFreezePane(0, 1);
        sheet.setColumnWidth(0, 18 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 42 * 256);
        sheet.setColumnWidth(3, 88 * 256);
    }

    private static void put(Workbook book, String sheetName, String testCase, String key, String value, String notes) {
        Sheet sheet = book.getSheet(sheetName);
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(testCase);
        row.createCell(1).setCellValue(key);
        row.createCell(2).setCellValue(value);
        Cell note = row.createCell(3);
        note.setCellValue(notes);
        CellStyle wrap = book.createCellStyle();
        wrap.setWrapText(true);
        note.setCellStyle(wrap);
        row.setHeightInPoints(32);
    }
}
