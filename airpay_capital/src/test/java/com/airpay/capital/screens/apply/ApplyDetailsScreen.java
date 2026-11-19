package com.airpay.capital.screens.apply;

import com.airpay.capital.data.CapitalExcel;
import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.crawl.DummyFormFiller;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Shared PAN / name / email / loan amount form for all products. Values come from Capital Excel. */
public class ApplyDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Enter your details as per official records')]")
    @iOSXCUITFindBy(accessibility = "Enter your details as per official records")
    private WebElement banner;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'PAN') or contains(@hint,'Enter PAN')]")
    @iOSXCUITFindBy(accessibility = "Enter PAN")
    private WebElement panField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Name as per PAN') or contains(@hint,'Enter Name')]")
    @iOSXCUITFindBy(accessibility = "Enter Name")
    private WebElement nameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Email') or contains(@hint,'email')]")
    @iOSXCUITFindBy(accessibility = "Enter email")
    private WebElement emailField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Desired loan amount') or contains(@hint,'Enter amount')]")
    @iOSXCUITFindBy(accessibility = "Enter amount")
    private WebElement loanAmountField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Income') or contains(@hint,'Salary') or contains(@hint,'income')]")
    @iOSXCUITFindBy(accessibility = "Income")
    private WebElement monthlyIncomeField;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Continue']")
    @iOSXCUITFindBy(accessibility = "Continue")
    private WebElement continueButton;

    public ApplyDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        if (sourceContains("For Self-Employed") && sourceContains("Loan Amount")
                && !sourceContains(ScreenMessages.DETAILS_BANNER)) {
            return false;
        }
        return sourceContains(ScreenMessages.DETAILS_BANNER, "Date of birth as per PAN");
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return waitUntilSourceContains(timeout, ScreenMessages.DETAILS_BANNER, "Enter PAN", "Date of birth as per PAN")
                || firstPresent(timeout, MobileWaits.xpath("//android.widget.EditText[contains(@hint,'PAN')]")) != null;
    }

    public ApplyDetailsScreen fillStandardDetails() {
        enterIdentityFromExcel();
        chooseDateOfBirth();
        chooseGender();
        return this;
    }

    /** Fills every box on Personal Details: PAN, name, email, loan amount, date of birth, gender, and I Agree. */
    public ApplyDetailsScreen enterIdentityFromExcel() {
        typeField("pan", CapitalExcel.text("PAN", "AQIPV5654R"), true);
        typeField("name", CapitalExcel.text("Name", "Shubham Verma"), false);
        typeField("mail", CapitalExcel.text("Email", "shubham.verma.inform@gmail.com"), false);
        typeField("amount", CapitalExcel.text("LoanAmount", "500000"), false);
        chooseDateOfBirth();
        chooseGender();
        tickAgree();
        return this;
    }

    public boolean addressVisible() {
        return sourceContains("Address Details");
    }

    /** Address Details comes after personal details on this build. */
    public void fillAddressIfDisplayed() {
        if (!addressVisible()) {
            return;
        }
        String[] values = {
                CapitalExcel.text("Pincode", "110001"),
                CapitalExcel.text("House", "12A"),
                CapitalExcel.text("Street", "MG Road"),
                CapitalExcel.text("City", "New Delhi"),
                CapitalExcel.text("State", "Delhi")
        };
        if (addressBoxes().isEmpty()) {
            typeField("digit", values[0], false);
            typeField("flat", values[1], false);
            typeField("road", values[2], false);
            typeField("city", values[3], false);
            typeField("state", values[4], false);
        } else {
            for (int i = 0; i < values.length; i++) {
                typeAddressBox(i, values[i]);
            }
        }
        tapContinue();
    }

    /** Address boxes have an empty hint. Top to bottom: pincode, house, street, city, state. */
    private void typeAddressBox(int index, String value) {
        List<WebElement> boxes = addressBoxes();
        if (value == null || value.isBlank() || index >= boxes.size() || addressShows(boxes.get(index), value)) {
            return;
        }
        var rect = boxes.get(index).getRect();
        int x = rect.x + Math.max(rect.width / 2, 1);
        int y = rect.y + Math.max(rect.height / 2, 1);
        String udid = context.config().get("android.udid", "");
        adbTap(x, y);
        AdbCommands.shell(udid, "cmd", "clipboard", "set-text", value);
        AdbCommands.shell(udid, "input", "keyevent", "279");
        boxes = addressBoxes();
        if (index >= boxes.size() || !addressShows(boxes.get(index), value)) {
            AdbCommands.shell(udid, "input", "text", value.replace(" ", "%s"));
        }
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // The keyboard is already closed.
        }
    }

    private List<WebElement> addressBoxes() {
        List<WebElement> boxes = new ArrayList<>();
        for (WebElement field : driver.findElements(MobileWaits.xpath("//android.widget.EditText[@clickable='true']"))) {
            try {
                if (field.isDisplayed()) {
                    boxes.add(field);
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        boxes.sort(Comparator.comparingInt(field -> field.getRect().y));
        return boxes;
    }

    private static boolean addressShows(WebElement field, String value) {
        try {
            String hint = field.getAttribute("hint");
            String desc = field.getAttribute("content-desc");
            String text = field.getAttribute("text");
            String label = ((hint == null ? "" : hint) + " " + (desc == null ? "" : desc) + " "
                    + (text == null ? "" : text)).toLowerCase().replace(",", "");
            return label.contains(value.replace(",", "").toLowerCase());
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Flutter ignores element.sendKeys. Tap the box, clear it, then send a real keystroke.
     * The name field also contains "PAN", so the PAN search skips it.
     */
    private void typeField(String needle, String value, boolean excludeName) {
        if (value == null || value.isBlank() || fieldShows(value)) {
            return;
        }
        WebElement field = findField(needle, excludeName);
        if (field == null) {
            return;
        }
        try {
            if (!field.isDisplayed()) {
                return;
            }
        } catch (Exception ignored) {
            return;
        }
        var rect = field.getRect();
        int x = rect.x + Math.max(rect.width / 2, 1);
        int y = rect.y + Math.max(rect.height / 2, 1);
        String udid = context.config().get("android.udid", "");
        adbTap(x, y);
        com.airpay.mobile.utilities.android.AdbCommands.shell(udid, "cmd", "clipboard", "set-text", value);
        com.airpay.mobile.utilities.android.AdbCommands.shell(udid, "input", "keyevent", "279");
        if (!fieldShows(value)) {
            com.airpay.mobile.utilities.android.AdbCommands.shell(
                    udid, "input", "text", value.replace(" ", "%s"));
        }
        if (!fieldShows(value)) {
            WebElement again = findField(needle, excludeName);
            if (again != null) {
                type(again, value);
            }
        }
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // The keyboard is already closed.
        }
    }

    private WebElement findField(String needle, boolean excludeName) {
        WebElement fallback = null;
        for (WebElement candidate : driver.findElements(MobileWaits.xpath("//android.widget.EditText"))) {
            String label = labelOf(candidate);
            if (excludeName && label.contains("name")) {
                continue;
            }
            if (!label.contains(needle)) {
                continue;
            }
            try {
                if (candidate.isDisplayed()) {
                    return candidate;
                }
            } catch (Exception ignored) {
                // stale
            }
            if (fallback == null) {
                fallback = candidate;
            }
        }
        return fallback;
    }

    /** True when an EditText already shows this Excel value, or the Indian grouped amount. */
    private boolean fieldShows(String value) {
        String wanted = value.toLowerCase();
        String grouped = groupedAmount(value);
        for (WebElement field : driver.findElements(MobileWaits.xpath("//android.widget.EditText"))) {
            String label = labelOf(field);
            if (label.contains(wanted) || (!grouped.isBlank() && label.contains(grouped))) {
                return true;
            }
        }
        return false;
    }

    private static String labelOf(WebElement field) {
        try {
            String hint = field.getAttribute("hint");
            String desc = field.getAttribute("content-desc");
            String text = field.getAttribute("text");
            return ((hint == null ? "" : hint) + " " + (desc == null ? "" : desc) + " " + (text == null ? "" : text))
                    .toLowerCase();
        } catch (Exception ignored) {
            return "";
        }
    }

    /**
     * Excel values that are not in an EditText.
     * Loan amount is required only when this form has an amount field. Credit Card does not.
     */
    public String missingIdentityValues() {
        StringBuilder missing = new StringBuilder();
        expectValue(missing, "PAN", CapitalExcel.text("PAN", "AQIPV5654R"));
        expectValue(missing, "Name", CapitalExcel.text("Name", "Shubham Verma"));
        expectValue(missing, "Email", CapitalExcel.text("Email", "shubham.verma.inform@gmail.com"));
        String amount = CapitalExcel.text("LoanAmount", "500000");
        if (findField("amount", false) != null && !fieldShows(amount)) {
            if (missing.length() > 0) {
                missing.append(", ");
            }
            missing.append("Desired loan amount ").append(amount);
        }
        return missing.toString();
    }

    private void expectValue(StringBuilder missing, String label, String value) {
        if (value == null || value.isBlank() || fieldShows(value)) {
            return;
        }
        if (missing.length() > 0) {
            missing.append(", ");
        }
        missing.append(label).append(' ').append(value);
    }

    /** 500000 → 5,00,000 so a formatted amount still counts as the Excel value. */
    static String groupedAmount(String digits) {
        String raw = digits.replaceAll("[^0-9]", "");
        if (raw.length() <= 3) {
            return raw;
        }
        String tail = raw.substring(raw.length() - 3);
        String head = raw.substring(0, raw.length() - 3);
        StringBuilder grouped = new StringBuilder();
        while (head.length() > 2) {
            grouped.insert(0, "," + head.substring(head.length() - 2));
            head = head.substring(0, head.length() - 2);
        }
        if (!head.isEmpty()) {
            grouped.insert(0, head);
        }
        return grouped + "," + tail;
    }

    /** Filler that types the Capital Excel PAN and name, including the "Name as per PAN" hint. */
    public DummyFormFiller formFiller() {
        String pan = CapitalExcel.text("PAN", "AQIPV5654R");
        String name = CapitalExcel.text("Name", "Shubham Verma");
        return new DummyFormFiller(driver, name, CapitalExcel.text("LoanAmount", "500000"))
                .override("name as per pan", name)
                .override("name", name)
                .override("pan", pan);
    }

    /** Opens the calendar and selects the Excel day, month, and year. */
    public void chooseDateOfBirth() {
        String target = targetDate();
        List<WebElement> buttons = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[contains(@content-desc,'Date of birth')]"));
        if (buttons.isEmpty()) {
            return;
        }
        String desc = buttons.get(0).getAttribute("content-desc");
        if (desc != null && desc.contains(target)) {
            return;
        }
        var opener = buttons.get(0).getRect();
        tapAt(opener.x + opener.width / 2, opener.y + opener.height / 2);
        sleep(600);
        String year = CapitalExcel.text("DateOfBirthYear", "1994").replaceAll("[^0-9]", "");
        String month = dateOfBirthMonth();
        tapYear(year);
        showMonth(month, year);
        tapCenter(MobileWaits.xpath("//android.widget.Button[starts-with(@content-desc,'" + dateOfBirthDay()
                + ",') and contains(@content-desc,'" + month + "')]"));
        tapCenter(MobileWaits.xpath("//android.widget.Button[@content-desc='OK']"));
        sleep(400);
    }

    /** The year in the header opens the year list. Earlier years sit above the open year. */
    private void tapYear(String year) {
        if (year.isBlank()) {
            year = "1994";
        }
        if (tapCenter(MobileWaits.xpath("//*[@content-desc='" + year + "']"))) {
            return;
        }
        tapCenter(MobileWaits.xpath("//*[contains(@content-desc,'Select year')]"));
        List<WebElement> headerYears = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[string-length(@content-desc)=4]"));
        if (!headerYears.isEmpty()) {
            var rect = headerYears.get(0).getRect();
            tapAt(rect.x + rect.width / 2, rect.y + rect.height / 2);
            sleep(400);
        }
        for (int i = 0; i < 8 && !safeSource().contains("content-desc=\"" + year + "\""); i++) {
            swipeDown();
        }
        tapCenter(MobileWaits.xpath("//*[@content-desc='" + year + "']"));
        sleep(400);
    }

    /** Previous month until the open calendar shows the Excel month. */
    private void showMonth(String month, String year) {
        for (int i = 0; i < 12; i++) {
            String src = safeSource();
            if (src.contains(month) && src.contains(year)) {
                return;
            }
            if (!tapLabel("Previous month")) {
                return;
            }
        }
    }

    private static String targetDate() {
        String year = CapitalExcel.text("DateOfBirthYear", "1994").replaceAll("[^0-9]", "");
        if (year.isBlank()) {
            year = "1994";
        }
        int month = monthNumber(CapitalExcel.text("DateOfBirthMonth", "May"));
        return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", Integer.parseInt(dateOfBirthDay()));
    }

    private static String dateOfBirthMonth() {
        String[] names = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return names[monthNumber(CapitalExcel.text("DateOfBirthMonth", "May")) - 1];
    }

    private static int monthNumber(String raw) {
        String[] names = {"january", "february", "march", "april", "may", "june",
                "july", "august", "september", "october", "november", "december"};
        String text = raw == null ? "" : raw.trim().toLowerCase();
        for (int i = 0; i < names.length; i++) {
            if (text.equals(names[i]) || text.equals(names[i].substring(0, 3))) {
                return i + 1;
            }
        }
        String digits = text.replaceAll("[^0-9]", "");
        if (!digits.isBlank()) {
            try {
                int n = Integer.parseInt(digits);
                if (n >= 1 && n <= 12) {
                    return n;
                }
            } catch (NumberFormatException ignored) {
                // fall through
            }
        }
        return 5;
    }

    /** The dropdown opens from the trailing icon, not the centre of the field. */
    public void chooseGender() {
        String gender = genderFromExcel();
        if (sourceContains("Close Gender selection")) {
            tapGenderOption(gender);
            return;
        }
        List<WebElement> buttons = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[contains(@content-desc,'Gender')]"));
        if (buttons.isEmpty()) {
            return;
        }
        String desc = buttons.get(0).getAttribute("content-desc");
        if (desc != null && !desc.contains("Select gender") && desc.contains(gender)) {
            return;
        }
        var r = buttons.get(0).getRect();
        int x = r.x + r.width - 40;
        int y = r.y + r.height / 2;
        tapAt(x, y);
        sleep(500);
        if (!sourceContains(gender)) {
            adbTap(x, y);
            sleep(500);
        }
        tapGenderOption(gender);
    }

    private void tapGenderOption(String gender) {
        if (!tapCenter(MobileWaits.xpath("//*[@content-desc='" + gender + "']"))) {
            adbTap(360, "Female".equals(gender) ? 1318 : "Others".equals(gender) ? 1432 : 1204);
        }
        sleep(400);
    }

    /** Excel Gender: Male, Female, or Others. Anything else uses Male. */
    private static String genderFromExcel() {
        String gender = CapitalExcel.text("Gender", "Male").trim();
        if ("Female".equalsIgnoreCase(gender)) {
            return "Female";
        }
        if ("Others".equalsIgnoreCase(gender) || "Other".equalsIgnoreCase(gender)) {
            return "Others";
        }
        return "Male";
    }

    /** Excel DateOfBirthDay, kept inside 1–28 so the open adult month always has that day. */
    private static String dateOfBirthDay() {
        String raw = CapitalExcel.text("DateOfBirthDay", "16").replaceAll("[^0-9]", "");
        if (raw.isBlank()) {
            return "16";
        }
        int day;
        try {
            day = Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            return "16";
        }
        if (day < 1 || day > 28) {
            return "16";
        }
        return Integer.toString(day);
    }

    public boolean acceptTermsAndTapApply() {
        for (int pass = 0; pass < 6; pass++) {
            tickTermsCheckboxes();
            if (tapEnabledApplyOrContinue()) {
                waitUntilSourceGone(Duration.ofSeconds(8), "Terms and conditions");
                return true;
            }
            swipeUp();
        }
        tickTermsCheckboxes();
        return tapEnabledApplyOrContinue() || tapContinue();
    }

    private int tickTermsCheckboxes() {
        int n = 0;
        List<WebElement> boxes = driver.findElements(MobileWaits.xpath(
                "//android.widget.CheckBox[contains(@content-desc,'Terms and conditions')]"));
        if (boxes.isEmpty()) {
            boxes = driver.findElements(MobileWaits.xpath(
                    "//android.widget.CheckBox[@clickable='true' or @checkable='true']"));
        }
        for (WebElement box : boxes) {
            try {
                if (!box.isDisplayed()) {
                    continue;
                }
                if ("true".equalsIgnoreCase(String.valueOf(box.getAttribute("checked")))) {
                    continue;
                }
                var r = box.getRect();
                tapAt(r.x + Math.min(48, Math.max(r.width / 2, 1)), r.y + Math.max(r.height / 2, 1));
                n++;
            } catch (Exception ignored) {
                // stale
            }
        }
        return n;
    }

    private boolean tapEnabledApplyOrContinue() {
        List<WebElement> btns = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[@content-desc='Apply Now' or @content-desc='Continue']"));
        for (WebElement b : btns) {
            try {
                if (b.isDisplayed() && b.isEnabled()) {
                    var r = b.getRect();
                    tapAt(r.x + Math.max(r.width / 2, 1), r.y + Math.max(r.height / 2, 1));
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }

    public boolean tapContinue() {
        tickAgree();
        return tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Continue')]"))
                || tapAny(List.of("Continue", "Next", "Proceed", "Save & Continue"));
    }

    /** The personal-details consent box. Continue stays disabled until it is checked. */
    private void tickAgree() {
        for (WebElement box : driver.findElements(MobileWaits.xpath("//android.widget.CheckBox"))) {
            try {
                if (!box.isDisplayed()) {
                    continue;
                }
                if ("true".equalsIgnoreCase(String.valueOf(box.getAttribute("checked")))) {
                    continue;
                }
                var rect = box.getRect();
                adbTap(rect.x + Math.min(36, Math.max(rect.width / 2, 1)), rect.y + Math.max(rect.height / 2, 1));
            } catch (Exception ignored) {
                // stale
            }
        }
    }
}
