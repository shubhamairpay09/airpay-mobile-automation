package com.airpay.vyaapaar.screens.licdateofbirth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** LIC / Date of Birth — Close sheet. Module lic-date-of-birth. */
public class LicDateOfBirthScreen extends BaseScreen {

    public static final String PAGE_ID = "lic-date-of-birth";

    public static final String CLOSE_SHEET = "//android.view.View[contains(@content-desc,'Close sheet')]";

    @AndroidFindBy(xpath = CLOSE_SHEET)
    private WebElement closeSheet;

    public static final String SELECT_DATE = "//android.widget.TextView[@text='Select date']";

    @AndroidFindBy(xpath = SELECT_DATE)
    private WebElement selectDate;

    public static final String CURRENT_SELECTION_TUESDAY14OCTOBER2008 = "//android.widget.TextView[contains(@content-desc,'Current selection: Tuesday, 14 October 2008')]";

    @AndroidFindBy(xpath = CURRENT_SELECTION_TUESDAY14OCTOBER2008)
    private WebElement currentSelectionTuesday14October2008;

    public static final String SWITCH_TO_TEXT_INPUT_MODE = "//android.view.View[contains(@content-desc,'Switch to text input mode')]";

    @AndroidFindBy(xpath = SWITCH_TO_TEXT_INPUT_MODE)
    private WebElement switchToTextInputMode;

    public static final String OCTOBER2008 = "//android.widget.TextView[contains(@content-desc,'October 2008')]";

    @AndroidFindBy(xpath = OCTOBER2008)
    private WebElement october2008;

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

    public static final String WEDNESDAY1OCTOBER2008 = "//android.widget.TextView[@text='Wednesday, 1 October 2008']";

    @AndroidFindBy(xpath = WEDNESDAY1OCTOBER2008)
    private WebElement wednesday1October2008;

    public static final String THURSDAY2OCTOBER2008 = "//android.widget.TextView[@text='Thursday, 2 October 2008']";

    @AndroidFindBy(xpath = THURSDAY2OCTOBER2008)
    private WebElement thursday2October2008;

    public static final String FRIDAY3OCTOBER2008 = "//android.widget.TextView[@text='Friday, 3 October 2008']";

    @AndroidFindBy(xpath = FRIDAY3OCTOBER2008)
    private WebElement friday3October2008;

    public static final String SATURDAY4OCTOBER2008 = "//android.widget.TextView[@text='Saturday, 4 October 2008']";

    @AndroidFindBy(xpath = SATURDAY4OCTOBER2008)
    private WebElement saturday4October2008;

    public static final String SUNDAY5OCTOBER2008 = "//android.widget.TextView[@text='Sunday, 5 October 2008']";

    @AndroidFindBy(xpath = SUNDAY5OCTOBER2008)
    private WebElement sunday5October2008;

    public static final String MONDAY6OCTOBER2008 = "//android.widget.TextView[@text='Monday, 6 October 2008']";

    @AndroidFindBy(xpath = MONDAY6OCTOBER2008)
    private WebElement monday6October2008;

    public static final String TUESDAY7OCTOBER2008 = "//android.widget.TextView[@text='Tuesday, 7 October 2008']";

    @AndroidFindBy(xpath = TUESDAY7OCTOBER2008)
    private WebElement tuesday7October2008;

    public static final String WEDNESDAY8OCTOBER2008 = "//android.widget.TextView[@text='Wednesday, 8 October 2008']";

    @AndroidFindBy(xpath = WEDNESDAY8OCTOBER2008)
    private WebElement wednesday8October2008;

    public static final String THURSDAY9OCTOBER2008 = "//android.widget.TextView[@text='Thursday, 9 October 2008']";

    @AndroidFindBy(xpath = THURSDAY9OCTOBER2008)
    private WebElement thursday9October2008;

    public static final String FRIDAY10OCTOBER2008 = "//android.widget.TextView[@text='Friday, 10 October 2008']";

    @AndroidFindBy(xpath = FRIDAY10OCTOBER2008)
    private WebElement friday10October2008;

    public static final String SATURDAY11OCTOBER2008 = "//android.widget.TextView[@text='Saturday, 11 October 2008']";

    @AndroidFindBy(xpath = SATURDAY11OCTOBER2008)
    private WebElement saturday11October2008;

    public static final String SUNDAY12OCTOBER2008 = "//android.widget.TextView[@text='Sunday, 12 October 2008']";

    @AndroidFindBy(xpath = SUNDAY12OCTOBER2008)
    private WebElement sunday12October2008;

    public static final String MONDAY13OCTOBER2008 = "//android.widget.TextView[@text='Monday, 13 October 2008']";

    @AndroidFindBy(xpath = MONDAY13OCTOBER2008)
    private WebElement monday13October2008;

    public static final String TUESDAY14OCTOBER2008 = "//android.widget.TextView[@text='Tuesday, 14 October 2008']";

    @AndroidFindBy(xpath = TUESDAY14OCTOBER2008)
    private WebElement tuesday14October2008;

    public static final String WEDNESDAY15OCTOBER2008 = "//android.widget.TextView[@text='Wednesday, 15 October 2008']";

    @AndroidFindBy(xpath = WEDNESDAY15OCTOBER2008)
    private WebElement wednesday15October2008;

    public static final String THURSDAY16OCTOBER2008 = "//android.widget.TextView[@text='Thursday, 16 October 2008']";

    @AndroidFindBy(xpath = THURSDAY16OCTOBER2008)
    private WebElement thursday16October2008;

    public static final String FRIDAY17OCTOBER2008 = "//android.widget.TextView[@text='Friday, 17 October 2008']";

    @AndroidFindBy(xpath = FRIDAY17OCTOBER2008)
    private WebElement friday17October2008;

    public static final String SATURDAY18OCTOBER2008 = "//android.widget.TextView[@text='Saturday, 18 October 2008']";

    @AndroidFindBy(xpath = SATURDAY18OCTOBER2008)
    private WebElement saturday18October2008;

    public static final String SUNDAY19OCTOBER2008 = "//android.widget.TextView[@text='Sunday, 19 October 2008']";

    @AndroidFindBy(xpath = SUNDAY19OCTOBER2008)
    private WebElement sunday19October2008;

    public static final String MONDAY20OCTOBER2008 = "//android.widget.TextView[@text='Monday, 20 October 2008']";

    @AndroidFindBy(xpath = MONDAY20OCTOBER2008)
    private WebElement monday20October2008;

    public static final String TUESDAY21OCTOBER2008 = "//android.widget.TextView[@text='Tuesday, 21 October 2008']";

    @AndroidFindBy(xpath = TUESDAY21OCTOBER2008)
    private WebElement tuesday21October2008;

    public static final String WEDNESDAY22OCTOBER2008 = "//android.widget.TextView[@text='Wednesday, 22 October 2008']";

    @AndroidFindBy(xpath = WEDNESDAY22OCTOBER2008)
    private WebElement wednesday22October2008;

    public static final String THURSDAY23OCTOBER2008 = "//android.widget.TextView[@text='Thursday, 23 October 2008']";

    @AndroidFindBy(xpath = THURSDAY23OCTOBER2008)
    private WebElement thursday23October2008;

    public static final String FRIDAY24OCTOBER2008 = "//android.widget.TextView[@text='Friday, 24 October 2008']";

    @AndroidFindBy(xpath = FRIDAY24OCTOBER2008)
    private WebElement friday24October2008;

    public static final String SATURDAY25OCTOBER2008 = "//android.widget.TextView[@text='Saturday, 25 October 2008']";

    @AndroidFindBy(xpath = SATURDAY25OCTOBER2008)
    private WebElement saturday25October2008;

    public static final String SUNDAY26OCTOBER2008 = "//android.widget.TextView[@text='Sunday, 26 October 2008']";

    @AndroidFindBy(xpath = SUNDAY26OCTOBER2008)
    private WebElement sunday26October2008;

    public static final String MONDAY27OCTOBER2008 = "//android.widget.TextView[@text='Monday, 27 October 2008']";

    @AndroidFindBy(xpath = MONDAY27OCTOBER2008)
    private WebElement monday27October2008;

    public static final String TUESDAY28OCTOBER2008 = "//android.widget.TextView[@text='Tuesday, 28 October 2008']";

    @AndroidFindBy(xpath = TUESDAY28OCTOBER2008)
    private WebElement tuesday28October2008;

    public static final String WEDNESDAY29OCTOBER2008 = "//android.widget.TextView[@text='Wednesday, 29 October 2008']";

    @AndroidFindBy(xpath = WEDNESDAY29OCTOBER2008)
    private WebElement wednesday29October2008;

    public static final String THURSDAY30OCTOBER2008 = "//android.widget.TextView[@text='Thursday, 30 October 2008']";

    @AndroidFindBy(xpath = THURSDAY30OCTOBER2008)
    private WebElement thursday30October2008;

    public static final String FRIDAY31OCTOBER2008 = "//android.widget.TextView[@text='Friday, 31 October 2008']";

    @AndroidFindBy(xpath = FRIDAY31OCTOBER2008)
    private WebElement friday31October2008;

    public static final String CANCEL = "//android.widget.TextView[@text='Cancel']";

    @AndroidFindBy(xpath = CANCEL)
    private WebElement cancel;

    public static final String OK = "//android.widget.TextView[@text='OK']";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public LicDateOfBirthScreen(TestContext context) {
        super(context);
    }
}
