package com.airpay.capital.screens.apply;

import com.airpay.capital.data.CapitalExcel;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SalariedIncomeScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Work Details')]")
    @iOSXCUITFindBy(accessibility = "Work Details")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'monthly salary') or contains(@hint,'Monthly In-hand')]")
    @iOSXCUITFindBy(accessibility = "monthly salary")
    private WebElement monthlySalaryField;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Continue')]")
    @iOSXCUITFindBy(accessibility = "Continue")
    private WebElement continueButton;

    public SalariedIncomeScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        // The occupation card says "monthly salary", so that phrase is not this screen.
        return sourceContains("Work Details");
    }

    public void fillIfDisplayed() {
        if (!isDisplayed()) {
            return;
        }
        typeBoxes(
                CapitalExcel.text("MonthlySalary", CapitalExcel.text("MonthlyIncome", "75000")),
                CapitalExcel.text("ExistingEmi", "0"));
        chooseList("salary mode", CapitalExcel.text("SalaryMode", "Bank credit"));
        chooseList("Employer", CapitalExcel.text("EmployerCategory", "Pvt ltd co"));
        chooseList("Years", CapitalExcel.text("ExperienceYears", "5"));
        chooseList("Months", CapitalExcel.text("ExperienceMonths", "0"));
    }

    /** The two boxes have an empty hint. The first is salary and the second is EMI. */
    private void typeBoxes(String salary, String emi) {
        typeBox(0, salary);
        typeBox(1, emi);
    }

    private void typeBox(int index, String value) {
        List<WebElement> boxes = clickableBoxes();
        if (value == null || value.isBlank() || index >= boxes.size() || shows(boxes.get(index), value)) {
            return;
        }
        var rect = boxes.get(index).getRect();
        int x = rect.x + Math.max(rect.width / 2, 1);
        int y = rect.y + Math.max(rect.height / 2, 1);
        String udid = context.config().get("android.udid", "");
        adbTap(x, y);
        AdbCommands.shell(udid, "cmd", "clipboard", "set-text", value);
        AdbCommands.shell(udid, "input", "keyevent", "279");
        boxes = clickableBoxes();
        if (index >= boxes.size() || !shows(boxes.get(index), value)) {
            AdbCommands.shell(udid, "input", "text", value.replace(" ", "%s"));
        }
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // The keyboard is already closed.
        }
    }

    private void chooseList(String buttonNeedle, String option) {
        if (option == null || option.isBlank() || listShows(buttonNeedle, option)) {
            return;
        }
        WebElement button = listButton(buttonNeedle);
        if (button == null) {
            return;
        }
        var rect = button.getRect();
        adbTap(rect.x + Math.max(rect.width / 2, 1), rect.y + Math.max(rect.height / 2, 1));
        WebElement choice = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[@content-desc=" + xpathLiteral(option) + "]"));
        if (choice == null) {
            return;
        }
        var choiceRect = choice.getRect();
        adbTap(choiceRect.x + Math.max(choiceRect.width / 2, 1),
                choiceRect.y + Math.max(1, Math.min(choiceRect.height / 2, choiceRect.height - 8)));
    }

    private boolean listShows(String buttonNeedle, String option) {
        String needle = buttonNeedle.toLowerCase();
        String wanted = option.toLowerCase();
        for (WebElement button : driver.findElements(MobileWaits.xpath("//android.widget.Button"))) {
            String desc = textOf(button);
            if (desc.contains(needle) && (desc.startsWith(wanted + ",") || desc.startsWith(wanted + " "))) {
                return true;
            }
        }
        return false;
    }

    private WebElement listButton(String buttonNeedle) {
        String needle = buttonNeedle.toLowerCase();
        for (WebElement button : driver.findElements(MobileWaits.xpath("//android.widget.Button"))) {
            try {
                if (textOf(button).contains(needle) && button.isDisplayed()) {
                    return button;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return null;
    }

    private List<WebElement> clickableBoxes() {
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

    private static boolean shows(WebElement field, String value) {
        String text = textOf(field).replace(",", "");
        return text.contains(value.replace(",", "").toLowerCase());
    }

    private static String textOf(WebElement field) {
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
}
