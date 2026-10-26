package com.airpay.vyaapaar.screens.panapplication;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / PAN Application / DOB / dropdown — Select date. Module pan-application. */
public class HomeCreditBalancePanApplicationDob2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-pan-application-dob-dropdown";

    public static final String SELECT_DATE = "//android.widget.TextView[@text='Select date']";

    @AndroidFindBy(xpath = SELECT_DATE)
    private WebElement selectDate;

    public static final String CURRENT_SELECTION_MONDAY26OCTOBER2026 = "//android.widget.TextView[contains(@content-desc,'Current selection: Monday, 26 October 2026')]";

    @AndroidFindBy(xpath = CURRENT_SELECTION_MONDAY26OCTOBER2026)
    private WebElement currentSelectionMonday26October2026;

    public static final String SWITCH_TO_TEXT_INPUT_MODE = "//android.view.View[contains(@content-desc,'Switch to text input mode')]";

    @AndroidFindBy(xpath = SWITCH_TO_TEXT_INPUT_MODE)
    private WebElement switchToTextInputMode;

    public static final String OCTOBER2026 = "//android.widget.TextView[contains(@content-desc,'October 2026')]";

    @AndroidFindBy(xpath = OCTOBER2026)
    private WebElement october2026;

    public static final String SWITCH_TO_SELECTING_AYEAR = "//android.view.View[contains(@content-desc,'Switch to selecting a year')]";

    @AndroidFindBy(xpath = SWITCH_TO_SELECTING_AYEAR)
    private WebElement switchToSelectingAYear;

    public static final String CHANGE_TO_PREVIOUS_MONTH = "//android.view.View[contains(@content-desc,'Change to previous month')]";

    @AndroidFindBy(xpath = CHANGE_TO_PREVIOUS_MONTH)
    private WebElement changeToPreviousMonth;

    public static final String CHANGE_TO_NEXT_MONTH = "//android.view.View[contains(@content-desc,'Change to next month')]";

    @AndroidFindBy(xpath = CHANGE_TO_NEXT_MONTH)
    private WebElement changeToNextMonth;

    public static final String SUNDAY = "//android.view.View[contains(@content-desc,'Sunday')]";

    @AndroidFindBy(xpath = SUNDAY)
    private WebElement sunday;

    public static final String MONDAY = "//android.view.View[contains(@content-desc,'Monday')]";

    @AndroidFindBy(xpath = MONDAY)
    private WebElement monday;

    public static final String TUESDAY = "//android.view.View[contains(@content-desc,'Tuesday')]";

    @AndroidFindBy(xpath = TUESDAY)
    private WebElement tuesday;

    public static final String WEDNESDAY = "//android.view.View[contains(@content-desc,'Wednesday')]";

    @AndroidFindBy(xpath = WEDNESDAY)
    private WebElement wednesday;

    public static final String THURSDAY = "//android.view.View[contains(@content-desc,'Thursday')]";

    @AndroidFindBy(xpath = THURSDAY)
    private WebElement thursday;

    public static final String FRIDAY = "//android.view.View[contains(@content-desc,'Friday')]";

    @AndroidFindBy(xpath = FRIDAY)
    private WebElement friday;

    public static final String SATURDAY = "//android.view.View[contains(@content-desc,'Saturday')]";

    @AndroidFindBy(xpath = SATURDAY)
    private WebElement saturday;

    public static final String THURSDAY1OCTOBER2026 = "//android.widget.TextView[@text='Thursday, 1 October 2026']";

    @AndroidFindBy(xpath = THURSDAY1OCTOBER2026)
    private WebElement thursday1October2026;

    public static final String FRIDAY2OCTOBER2026 = "//android.widget.TextView[@text='Friday, 2 October 2026']";

    @AndroidFindBy(xpath = FRIDAY2OCTOBER2026)
    private WebElement friday2October2026;

    public static final String SATURDAY3OCTOBER2026 = "//android.widget.TextView[@text='Saturday, 3 October 2026']";

    @AndroidFindBy(xpath = SATURDAY3OCTOBER2026)
    private WebElement saturday3October2026;

    public static final String SUNDAY4OCTOBER2026 = "//android.widget.TextView[@text='Sunday, 4 October 2026']";

    @AndroidFindBy(xpath = SUNDAY4OCTOBER2026)
    private WebElement sunday4October2026;

    public static final String MONDAY5OCTOBER2026 = "//android.widget.TextView[@text='Monday, 5 October 2026']";

    @AndroidFindBy(xpath = MONDAY5OCTOBER2026)
    private WebElement monday5October2026;

    public static final String TUESDAY6OCTOBER2026 = "//android.widget.TextView[@text='Tuesday, 6 October 2026']";

    @AndroidFindBy(xpath = TUESDAY6OCTOBER2026)
    private WebElement tuesday6October2026;

    public static final String WEDNESDAY7OCTOBER2026 = "//android.widget.TextView[@text='Wednesday, 7 October 2026']";

    @AndroidFindBy(xpath = WEDNESDAY7OCTOBER2026)
    private WebElement wednesday7October2026;

    public static final String THURSDAY8OCTOBER2026 = "//android.widget.TextView[@text='Thursday, 8 October 2026']";

    @AndroidFindBy(xpath = THURSDAY8OCTOBER2026)
    private WebElement thursday8October2026;

    public static final String FRIDAY9OCTOBER2026 = "//android.widget.TextView[@text='Friday, 9 October 2026']";

    @AndroidFindBy(xpath = FRIDAY9OCTOBER2026)
    private WebElement friday9October2026;

    public static final String SATURDAY10OCTOBER2026 = "//android.widget.TextView[@text='Saturday, 10 October 2026']";

    @AndroidFindBy(xpath = SATURDAY10OCTOBER2026)
    private WebElement saturday10October2026;

    public static final String SUNDAY11OCTOBER2026 = "//android.widget.TextView[@text='Sunday, 11 October 2026']";

    @AndroidFindBy(xpath = SUNDAY11OCTOBER2026)
    private WebElement sunday11October2026;

    public static final String MONDAY12OCTOBER2026 = "//android.widget.TextView[@text='Monday, 12 October 2026']";

    @AndroidFindBy(xpath = MONDAY12OCTOBER2026)
    private WebElement monday12October2026;

    public static final String TUESDAY13OCTOBER2026 = "//android.widget.TextView[@text='Tuesday, 13 October 2026']";

    @AndroidFindBy(xpath = TUESDAY13OCTOBER2026)
    private WebElement tuesday13October2026;

    public static final String WEDNESDAY14OCTOBER2026 = "//android.widget.TextView[@text='Wednesday, 14 October 2026']";

    @AndroidFindBy(xpath = WEDNESDAY14OCTOBER2026)
    private WebElement wednesday14October2026;

    public static final String THURSDAY15OCTOBER2026 = "//android.widget.TextView[@text='Thursday, 15 October 2026']";

    @AndroidFindBy(xpath = THURSDAY15OCTOBER2026)
    private WebElement thursday15October2026;

    public static final String FRIDAY16OCTOBER2026 = "//android.widget.TextView[@text='Friday, 16 October 2026']";

    @AndroidFindBy(xpath = FRIDAY16OCTOBER2026)
    private WebElement friday16October2026;

    public static final String SATURDAY17OCTOBER2026 = "//android.widget.TextView[@text='Saturday, 17 October 2026']";

    @AndroidFindBy(xpath = SATURDAY17OCTOBER2026)
    private WebElement saturday17October2026;

    public static final String SUNDAY18OCTOBER2026 = "//android.widget.TextView[@text='Sunday, 18 October 2026']";

    @AndroidFindBy(xpath = SUNDAY18OCTOBER2026)
    private WebElement sunday18October2026;

    public static final String MONDAY19OCTOBER2026 = "//android.widget.TextView[@text='Monday, 19 October 2026']";

    @AndroidFindBy(xpath = MONDAY19OCTOBER2026)
    private WebElement monday19October2026;

    public static final String TUESDAY20OCTOBER2026 = "//android.widget.TextView[@text='Tuesday, 20 October 2026']";

    @AndroidFindBy(xpath = TUESDAY20OCTOBER2026)
    private WebElement tuesday20October2026;

    public static final String WEDNESDAY21OCTOBER2026 = "//android.widget.TextView[@text='Wednesday, 21 October 2026']";

    @AndroidFindBy(xpath = WEDNESDAY21OCTOBER2026)
    private WebElement wednesday21October2026;

    public static final String THURSDAY22OCTOBER2026 = "//android.widget.TextView[@text='Thursday, 22 October 2026']";

    @AndroidFindBy(xpath = THURSDAY22OCTOBER2026)
    private WebElement thursday22October2026;

    public static final String FRIDAY23OCTOBER2026 = "//android.widget.TextView[@text='Friday, 23 October 2026']";

    @AndroidFindBy(xpath = FRIDAY23OCTOBER2026)
    private WebElement friday23October2026;

    public static final String SATURDAY24OCTOBER2026 = "//android.widget.TextView[@text='Saturday, 24 October 2026']";

    @AndroidFindBy(xpath = SATURDAY24OCTOBER2026)
    private WebElement saturday24October2026;

    public static final String SUNDAY25OCTOBER2026 = "//android.widget.TextView[@text='Sunday, 25 October 2026']";

    @AndroidFindBy(xpath = SUNDAY25OCTOBER2026)
    private WebElement sunday25October2026;

    public static final String TODAY_MONDAY26OCTOBER2026 = "//android.widget.TextView[@text='Today, Monday, 26 October 2026']";

    @AndroidFindBy(xpath = TODAY_MONDAY26OCTOBER2026)
    private WebElement todayMonday26October2026;

    public static final String TUESDAY27OCTOBER2026 = "//android.widget.TextView[@text='Tuesday, 27 October 2026']";

    @AndroidFindBy(xpath = TUESDAY27OCTOBER2026)
    private WebElement tuesday27October2026;

    public static final String WEDNESDAY28OCTOBER2026 = "//android.widget.TextView[@text='Wednesday, 28 October 2026']";

    @AndroidFindBy(xpath = WEDNESDAY28OCTOBER2026)
    private WebElement wednesday28October2026;

    public static final String THURSDAY29OCTOBER2026 = "//android.widget.TextView[@text='Thursday, 29 October 2026']";

    @AndroidFindBy(xpath = THURSDAY29OCTOBER2026)
    private WebElement thursday29October2026;

    public static final String FRIDAY30OCTOBER2026 = "//android.widget.TextView[@text='Friday, 30 October 2026']";

    @AndroidFindBy(xpath = FRIDAY30OCTOBER2026)
    private WebElement friday30October2026;

    public static final String SATURDAY31OCTOBER2026 = "//android.widget.TextView[@text='Saturday, 31 October 2026']";

    @AndroidFindBy(xpath = SATURDAY31OCTOBER2026)
    private WebElement saturday31October2026;

    public static final String CANCEL = "//android.widget.TextView[@text='Cancel']";

    @AndroidFindBy(xpath = CANCEL)
    private WebElement cancel;

    public static final String OK = "//android.widget.TextView[@text='OK']";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public HomeCreditBalancePanApplicationDob2Screen(TestContext context) {
        super(context);
    }
}
