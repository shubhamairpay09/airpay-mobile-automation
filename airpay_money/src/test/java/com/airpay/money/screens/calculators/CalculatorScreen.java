package com.airpay.money.screens.calculators;

import com.airpay.mobile.utilities.waits.MobileWaits;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;

/**
 * Live dump 2026-09-21 — the 15 calculator detail screens behind the Calculators tab.
 *
 * <p>Two widget shapes recur across the module and drive how these locators are written:
 *
 * <ul>
 *   <li>A <b>slider row</b> is a clickable container whose {@code content-desc} is
 *       {@code "<value>, <label>\n<value>\n<label>"}, immediately followed by a sibling
 *       {@code SeekBar} whose {@code content-desc} is the fill <i>percentage</i>, not the value.
 *       Read the value off the container and assert the label with {@code contains()}; never
 *       assert on the SeekBar's percentage.
 *   <li>On the EMI calculator only, the same row is an {@code android.widget.Button} and its
 *       {@code content-desc} is prefixed {@code "<value>, Edit <label>"}. Tapping either shape
 *       opens the shared edit bottom sheet in {@link #EDIT_SHEET_TITLE} below.
 * </ul>
 *
 * <p>Every calculator's screen title lives at the same node, so {@link #title(String)} is the
 * canonical "am I on the right screen" assertion. Note that three titles do not match their hub
 * tile label: FD → "Fixed Deposit", Buy vs Rent → "Buy Vs Rent calculator", and the Term Insurance
 * tile opens "Retirement Calculator" outright (see the module analysis doc, defect F1).
 */
public class CalculatorScreen extends com.airpay.money.screens.base.BaseScreen {

    public CalculatorScreen(com.airpay.mobile.utilities.context.TestContext context) {
        super(context);
    }

    // ---------------------------------------------------------------- shared chrome

    /** Header chevron. Unlabelled, so it can only be matched structurally. */
    public static final String BACK = "//android.widget.Button[@content-desc='' and @clickable='true']";

    public static String title(String screenTitle) {
        return "//android.view.View[@content-desc='" + screenTitle + "']";
    }

    /** Sticky footer call to action; every calculator that has one reuses these bounds. */
    public static String cta(String label) {
        return "//android.widget.Button[@content-desc='" + label + "']";
    }

    /** Slider row container, matched on its label. Works for every calculator except EMI. */
    public static String sliderRow(String label) {
        return "//android.view.View[contains(@content-desc,'" + label + "')]";
    }

    /** EMI's slider rows are Buttons and carry an "Edit <label>" affordance in the name. */
    public static String editableRow(String label) {
        return "//android.widget.Button[contains(@content-desc,'Edit " + label + "')]";
    }

    /** The SeekBar that follows a slider row. Its content-desc is a percentage, not the value. */
    public static final String ANY_SLIDER = "//android.widget.SeekBar";

    public static String resultLabel(String label) {
        return "//android.view.View[@content-desc='" + label + "']";
    }

    // --------------------------------------------- shared "edit value" bottom sheet

    /**
     * Tapping a value chip opens a bottom sheet with a title, an unlabelled close button, a single
     * EditText pre-filled with the <i>formatted</i> value (currency symbol and grouping included),
     * and "Save Changes". The title is the bare field label on every calculator except EMI, which
     * prefixes it with "Edit ".
     */
    public static String editSheetTitle(String label) {
        return "//android.view.View[@content-desc='" + label + "']";
    }

    public static String emiEditSheetTitle(String label) {
        return "//android.view.View[@content-desc='Edit " + label + "']";
    }

    public static final String EDIT_SHEET_INPUT = "//android.widget.EditText";
    public static final String EDIT_SHEET_SAVE = "//android.widget.Button[@content-desc='Save Changes']";
    public static final String EDIT_SHEET_CLOSE = "//android.widget.Button[@content-desc='' and @clickable='true']";
    public static final String EDIT_SHEET_SCRIM = "//android.view.View[@content-desc='Scrim']";

    // ------------------------------------------------------------------ 1. EMI

    public static final String EMI_TITLE = title("EMI Calculator");
    public static final String EMI_LOAN_AMOUNT = editableRow("Loan Amount");
    public static final String EMI_INTEREST_RATE = editableRow("Interest Rate");
    public static final String EMI_TENURE = editableRow("Tenure");
    public static final String EMI_RESULT_LOAN_AMOUNT = resultLabel("Loan Amount");
    public static final String EMI_RESULT_INTEREST_AMOUNT = resultLabel("Interest Amount");
    public static final String EMI_RESULT_TOTAL_PAYMENT = resultLabel("Total Payment");
    public static final String EMI_RESULT_EMI_AMOUNT = resultLabel("EMI Amount");
    // No call to action on this screen.

    // ------------------------------------------------------------------ 2. SIP

    public static final String SIP_TITLE = title("SIP Calculator");
    public static final String SIP_MONTHLY_INVESTMENT = sliderRow("Monthly Investment");
    public static final String SIP_INVESTMENT_PERIOD = sliderRow("Investment Period");
    public static final String SIP_DESIRED_RETURN = sliderRow("Desired Return");
    public static final String SIP_RESULT_INVESTED_AMOUNT = resultLabel("Invested Amount");
    public static final String SIP_RESULT_EST_RETURNS = resultLabel("Est. Returns");
    public static final String SIP_RESULT_TOTAL_VALUE = resultLabel("Total Value");
    public static final String SIP_CTA_START_SIP = cta("Start SIP");

    // -------------------------------------------------------------- 3. Lumpsum

    public static final String LUMPSUM_TITLE = title("Lumpsum Calculator");
    public static final String LUMPSUM_TOTAL_INVESTMENT = sliderRow("Total investment");
    public static final String LUMPSUM_EXPECTED_RETURN = sliderRow("Expected Return (p.a)");
    public static final String LUMPSUM_TENURE = sliderRow("Tenure");
    public static final String LUMPSUM_RESULT_INVESTMENT_AMOUNT = resultLabel("Investment Amount");
    public static final String LUMPSUM_RESULT_EST_RETURNS = resultLabel("Est. Returns");
    public static final String LUMPSUM_RESULT_TOTAL_VALUE = resultLabel("Total value");
    public static final String LUMPSUM_CTA_START_INVESTMENT = cta("Start Investment");

    // ------------------------------------------------------------------ 4. PPF

    public static final String PPF_TITLE = title("PPF Calculator");
    public static final String PPF_ANNUAL_INVESTMENT = sliderRow("Annual Investment");
    public static final String PPF_INVESTMENT_PERIOD = sliderRow("Investment Period");
    public static final String PPF_EXPECTED_RETURN = sliderRow("Expected Return (p.a)");
    public static final String PPF_RESULT_INVESTMENT_AMOUNT = resultLabel("Investment Amount");
    public static final String PPF_RESULT_TOTAL_INTEREST = resultLabel("Total Interest Earned");
    public static final String PPF_RESULT_MATURITY_VALUE = resultLabel("Maturity Value");
    // No call to action on this screen.

    // ------------------------------------------------------------------ 5. EPF

    public static final String EPF_TITLE = title("EPF Calculator");
    public static final String EPF_CURRENT_AGE = sliderRow("Current Age");
    public static final String EPF_RETIREMENT_AGE = sliderRow("Retirement Age");
    public static final String EPF_MONTHLY_SALARY = sliderRow("Monthly Salary (Basic + DA)");
    public static final String EPF_ANNUAL_SALARY_INCREASE = sliderRow("Annual Increase in Salary");
    public static final String EPF_YOUR_CONTRIBUTION = sliderRow("Your Contribution");
    public static final String EPF_COMPANY_CONTRIBUTION = sliderRow("Company's Contribution");
    /** Read-only, rendered as a label plus a "Fixed" pill rather than a slider. */
    public static final String EPF_INTEREST_RATE_LABEL = resultLabel("EPF Interest Rate");
    public static final String EPF_INTEREST_RATE_FIXED_PILL = resultLabel("Fixed");
    public static final String EPF_RESULT_TOTAL_CONTRIBUTION_RATE = resultLabel("Total Contribution Rate");
    public static final String EPF_RESULT_TOTAL_INTEREST = resultLabel("Total interest earned");
    public static final String EPF_RESULT_ESTIMATED_VALUE = resultLabel("Estimated EPF Value");
    public static final String EPF_CTA_SAVE_TAX_WITH_FUNDS = cta("Save Tax with Funds");

    // ------------------------------------------------------------------- 6. FD

    /** Title is "Fixed Deposit", not "FD Calculator". */
    public static final String FD_TITLE = title("Fixed Deposit");
    public static final String FD_TAB_BY_DEPOSIT_AMOUNT = resultLabel("By Deposit Amount");
    public static final String FD_TAB_BY_TARGET_AMOUNT = resultLabel("By Target Amount");
    public static final String FD_AMOUNT = sliderRow("Amount (in INR)");
    public static final String FD_TARGET_AMOUNT = sliderRow("Target amount");
    public static final String FD_INTEREST_RATE = sliderRow("Interest Rate %");
    public static final String FD_TENURE = sliderRow("Tenure");
    public static final String FD_RESULT_MATURITY_VALUE = resultLabel("Maturity Value");
    public static final String FD_RESULT_DEPOSIT_AMOUNT = resultLabel("Deposit Amount");
    public static final String FD_RESULT_TOTAL_INTEREST = resultLabel("Total Interest Earned");
    public static final String FD_RATE_TABLE_HEADER_TENURE = resultLabel("Tenure");
    public static final String FD_RATE_TABLE_HEADER_AT_MATURITY = resultLabel("At Maturity");
    public static final String FD_RATE_TABLE_HEADER_MONTHLY = resultLabel("Monthly");
    public static final String FD_RATE_TABLE_HEADER_QUARTERLY = resultLabel("Quarterly");
    public static final String FD_RATE_TABLE_HEADER_HALF_YEARLY = resultLabel("Half-yearly");
    public static final String FD_RATE_TABLE_FOOTNOTE =
            "//android.view.View[contains(@content-desc,'All interest rate shown above annually')]";
    public static final String FD_CTA_APPLY_NOW = cta("Apply Now");

    // ------------------------------------------------------------------ 7. NPS

    public static final String NPS_TITLE = title("NPS Calculator");
    public static final String NPS_MONTHLY_AMOUNT = sliderRow("Amount Invested Monthly (in INR)");
    public static final String NPS_AGE = sliderRow("Age (in years)");
    public static final String NPS_EXPECTED_RETURN = sliderRow("Expected Rate of Return (in %)");
    public static final String NPS_RESULT_MATURITY_AMOUNT = resultLabel("Maturity Amount");
    public static final String NPS_RESULT_TOTAL_TAX_SAVING = resultLabel("Total Tax Saving");
    public static final String NPS_RESULT_INVESTMENT_AMOUNT = resultLabel("Investment Amount");
    public static final String NPS_RESULT_GROWTH_AMOUNT = resultLabel("Growth Amount");
    public static final String NPS_RESULT_ANNUAL_TAX_SAVING = resultLabel("Annual Tax Saving");
    // No call to action on this screen.

    // ----------------------------------------------------------- 8. Retirement

    public static final String RETIREMENT_TITLE = title("Retirement Calculator");
    public static final String RETIREMENT_MONTHLY_EXPENSES = sliderRow("Household expenses per month");
    public static final String RETIREMENT_CURRENT_AGE = sliderRow("Current age");
    public static final String RETIREMENT_RETIREMENT_AGE = sliderRow("Retirement age");
    public static final String RETIREMENT_RETURNS_AFTER = sliderRow("Returns expectations after retirement");
    /** Read-only derived values, no slider. */
    public static final String RETIREMENT_LIFE_EXPECTANCY = resultLabel("Life Expectancy");
    public static final String RETIREMENT_INFLATION = resultLabel("Inflation");
    public static final String RETIREMENT_RESULT_REQUIREMENT = resultLabel("Retirement Requirement");
    public static final String RETIREMENT_CTA_START_SAVING = cta("Start Saving");

    // ------------------------------------------------------------ 9. Crorepati

    public static final String CROREPATI_TITLE = title("Crorepati Calculator");
    public static final String CROREPATI_SIP_AMOUNT = sliderRow("SIP Amount");
    public static final String CROREPATI_EXPECTED_ROI = sliderRow("Expected ROI (p.a)");
    public static final String CROREPATI_RESULT_INVESTED_AMOUNT = resultLabel("Invested Amount");
    public static final String CROREPATI_RESULT_EXPECTED_AMOUNT = resultLabel("Expected Amount");
    public static final String CROREPATI_RESULT_AVG_HISTORICAL_RETURN = resultLabel("Avg. Historical Return");
    public static final String CROREPATI_RESULT_TIME_REQUIRED =
            resultLabel("Time required to become a Crorepati");
    // No call to action on this screen.

    // ------------------------------------------------------ 10. Term Insurance

    /**
     * The "Term Insurance Calculator" hub tile navigates to the Retirement Calculator, so there is
     * no Term Insurance screen to locate. Assert on this to pin the defect until it is fixed.
     */
    public static final String TERM_INSURANCE_ACTUAL_TITLE = RETIREMENT_TITLE;

    // --------------------------------------------------------- 11. Buy vs Rent

    /** Title is "Buy Vs Rent calculator" — different casing from the hub tile. */
    public static final String BUY_VS_RENT_TITLE = title("Buy Vs Rent calculator");
    public static final String BUY_VS_RENT_PROPERTY_PRICE = sliderRow("Property Price (in INR)");
    public static final String BUY_VS_RENT_DOWN_PAYMENT = sliderRow("Down Payment (in INR)");
    public static final String BUY_VS_RENT_LOAN_TENURE = sliderRow("Loan Tenure (in Years)");
    public static final String BUY_VS_RENT_LOAN_INTEREST = sliderRow("Rate of Interest on Loan (in %)");
    public static final String BUY_VS_RENT_MONTHLY_RENT = sliderRow("Monthly Rent (in INR)");
    public static final String BUY_VS_RENT_RENT_INCREASE = sliderRow("Rent Increase Every Year (in %)");
    public static final String BUY_VS_RENT_RESULT_HOUSE_VALUE =
            "//android.view.View[contains(@content-desc,'Value of your house in')]";
    public static final String BUY_VS_RENT_RESULT_INVESTMENT_VALUE =
            "//android.view.View[contains(@content-desc,'Value of investment in')]";
    public static final String BUY_VS_RENT_VERDICT =
            "//android.view.View[contains(@content-desc,'You may be better off')]";
    public static final String BUY_VS_RENT_VERDICT_DETAIL =
            "//android.view.View[contains(@content-desc,'more as a return on your investment')]";
    // No call to action on this screen.

    // ----------------------------------------------- 12. Taxation Indian Equity

    public static final String TAX_INDIAN_TITLE = title("Taxation Indian Equity");
    /**
     * This screen splits each field across three nodes: a label View, an unlabelled NAF View that
     * holds the tappable value chip, and a separate value View. The chip has no accessible name,
     * so drive it by the sibling label or by coordinates.
     */
    public static final String TAX_INDIAN_INVESTMENT_AMOUNT_LABEL = resultLabel("Investment Amount");
    public static final String TAX_INDIAN_SALE_AMOUNT_LABEL = resultLabel("Sale Amount");
    public static final String TAX_INDIAN_HOLDING_PERIOD_LABEL = resultLabel("Holding Period");
    public static final String TAX_INDIAN_INVESTMENT_AMOUNT_SLIDER_ROW = sliderRow("Investment Amount");
    public static final String TAX_INDIAN_SALE_AMOUNT_SLIDER_ROW = sliderRow("Sale Amount");
    public static final String TAX_INDIAN_HOLDING_PERIOD_SLIDER_ROW = sliderRow("Holding Period");
    public static final String TAX_INDIAN_HOLDING_PERIOD_HINT =
            "//android.view.View[contains(@content-desc,'More than 1 year is Long Term')]";
    public static final String TAX_INDIAN_RESULT_CAPITAL_GAIN = resultLabel("Capital Gain");
    public static final String TAX_INDIAN_RESULT_TAX_PAYABLE = resultLabel("Tax Payable");
    // No call to action on this screen.

    // ---------------------------------------- 13. Taxation International Equity

    public static final String TAX_INTL_TITLE = title("Taxation International Equity");
    public static final String TAX_INTL_INVESTMENT_AMOUNT = sliderRow("Investment Amount");
    public static final String TAX_INTL_SALE_AMOUNT = sliderRow("Sale amount");
    public static final String TAX_INTL_HOLDING_PERIOD = sliderRow("Holding Period");
    public static final String TAX_INTL_INCOME_TAX_SLAB = sliderRow("Income tax slab");
    public static final String TAX_INTL_RESULT_CAPITAL_GAIN = resultLabel("Capital Gain");
    public static final String TAX_INTL_RESULT_TAX_PAYABLE = resultLabel("Tax Payable");
    public static final String TAX_INTL_CTA_INVEST_NOW = cta("Invest Now");

    // ------------------------------------------------------------ 14. Inflation

    public static final String INFLATION_TITLE = title("Inflation Calculator");
    public static final String INFLATION_AMOUNT_LABEL = resultLabel("Enter amount");
    public static final String INFLATION_AMOUNT_SLIDER_ROW = sliderRow("Enter amount");
    public static final String INFLATION_BASE_YEAR_LABEL = resultLabel("Base year");
    public static final String INFLATION_END_YEAR_LABEL = resultLabel("End year");
    /** Both year pickers are Buttons whose only accessible name is the selected year. */
    public static String inflationYearButton(String year) {
        return "//android.widget.Button[@content-desc='" + year + "']";
    }

    public static final String INFLATION_RESULT_CAPTION = resultLabel("You'll be able to buy it for");
    public static final String INFLATION_HISTORY_TITLE = resultLabel("Historical data for inflation");
    public static final String INFLATION_HISTORY_HEADER_YEAR = resultLabel("Year");
    public static final String INFLATION_HISTORY_HEADER_INFLATION = resultLabel("Inflation");
    /** Each history row merges year and rate into one content-desc, e.g. "2026\n3.20%". */
    public static String inflationHistoryRow(String year) {
        return "//android.view.View[starts-with(@content-desc,'" + year + "')]";
    }

    public static final String INFLATION_SHOW_MORE = cta("Show More");

    // --------------------------------------------------------------- 15. Budget

    public static final String BUDGET_TITLE = title("Budget Calculator");
    public static final String BUDGET_MONTHLY_INCOME_LABEL =
            "//android.view.View[contains(@content-desc,'Your Monthly')]";
    public static final String BUDGET_SECTION_CURRENT_ALLOCATION = resultLabel("Current Allocation");
    public static final String BUDGET_SECTION_IDEAL_ALLOCATION = resultLabel("Ideal Allocation");
    public static final String BUDGET_SECTION_DIFFERENCE = resultLabel("Difference");
    public static final String BUDGET_LABEL_NEEDS = resultLabel("Needs");
    public static final String BUDGET_LABEL_WANTS = resultLabel("Wants");
    public static final String BUDGET_LABEL_SAVINGS = resultLabel("Savings");
    /**
     * The only free-text inputs in the whole module. Three unlabelled EditTexts in Needs, Wants,
     * Savings order — index them positionally, there is no name to match on.
     */
    public static final String BUDGET_ALLOCATION_INPUTS = "//android.widget.EditText";
    public static final String BUDGET_NEEDS_INPUT = "(//android.widget.EditText)[1]";
    public static final String BUDGET_WANTS_INPUT = "(//android.widget.EditText)[2]";
    public static final String BUDGET_SAVINGS_INPUT = "(//android.widget.EditText)[3]";
    // No call to action on this screen.

    public boolean showsTitle(String screenTitle) {
        return sourceContains(screenTitle);
    }

    public String rowText(String label) {
        WebElement row = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"));
        return row == null ? "" : String.valueOf(row.getAttribute("content-desc"));
    }

    public void nudgeSlider(String label) {
        dragSlider(label, 0.35, 0.75);
    }

    public void dragSliderToEnd(String label, boolean maximum) {
        dragSlider(label, maximum ? 0.2 : 0.8, maximum ? 0.95 : 0.05);
    }

    public void editAndSave(String label, String value) {
        openEditor(label);
        replaceEditorValue(value);
        tapLabel("Save Changes");
        sleep(600);
    }

    public void clearAndSave(String label) {
        openEditor(label);
        WebElement field = editor();
        if (field != null) {
            field.click();
            try {
                field.clear();
            } catch (Exception ignored) {
                field.sendKeys("\b\b\b\b\b\b\b\b\b\b");
            }
        }
        tapLabel("Save Changes");
        sleep(500);
    }

    public String editAndClose(String label, String value) {
        String before = rowText(label);
        openEditor(label);
        replaceEditorValue(value);
        closeEditor();
        return before;
    }

    public void openEditor(String label) {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc," + xpathLiteral(label) + ")]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc," + xpathLiteral(label) + ")]"));
        sleep(500);
    }

    private void replaceEditorValue(String value) {
        WebElement field = editor();
        if (field == null) {
            return;
        }
        field.click();
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter fields often reject clear()
        }
        field.sendKeys(value);
        sleep(200);
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard already closed
        }
    }

    private WebElement editor() {
        return firstPresent(Duration.ofSeconds(4), MobileWaits.xpath(EDIT_SHEET_INPUT));
    }

    private void closeEditor() {
        WebElement close = null;
        for (WebElement button : driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[@clickable='true' and (@content-desc='' or not(@content-desc))]"))) {
            try {
                if (button.isDisplayed() && button.getRect().getY() > 400) {
                    close = button;
                    break;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        if (close != null) {
            close.click();
        } else {
            tapLabel("Scrim");
        }
        sleep(400);
    }

    private void dragSlider(String label, double from, double to) {
        WebElement bar = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label)
                        + ")]/following-sibling::android.widget.SeekBar[1]"),
                MobileWaits.xpath(ANY_SLIDER));
        if (bar == null) {
            return;
        }
        var rect = bar.getRect();
        int y = rect.getY() + Math.max(1, rect.getHeight() / 2);
        int x1 = rect.getX() + (int) (rect.getWidth() * from);
        int x2 = rect.getX() + (int) (rect.getWidth() * to);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence drag = new Sequence(finger, 1);
        drag.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x1, y));
        drag.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        drag.addAction(finger.createPointerMove(Duration.ofMillis(400), PointerInput.Origin.viewport(), x2, y));
        drag.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(java.util.List.of(drag));
        sleep(500);
    }
}
