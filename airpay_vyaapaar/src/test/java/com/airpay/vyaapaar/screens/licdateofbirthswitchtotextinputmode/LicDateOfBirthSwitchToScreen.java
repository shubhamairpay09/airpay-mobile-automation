package com.airpay.vyaapaar.screens.licdateofbirthswitchtotextinputmode;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** LIC / Date of Birth / Switch to text input mode — Close sheet. Module lic-date-of-birth-switch-to-text-input-mode. */
public class LicDateOfBirthSwitchToScreen extends BaseScreen {

    public static final String PAGE_ID = "lic-date-of-birth-switch-to-text-input-mode";

    public static final String CLOSE_SHEET = "//android.view.View[contains(@content-desc,'Close sheet')]";

    @AndroidFindBy(xpath = CLOSE_SHEET)
    private WebElement closeSheet;

    public static final String SELECT_DATE = "//android.widget.TextView[@text='Select date']";

    @AndroidFindBy(xpath = SELECT_DATE)
    private WebElement selectDate;

    public static final String ENTERED_DATE_TUESDAY14OCTOBER2008 = "//android.widget.TextView[contains(@content-desc,'Entered date: Tuesday, 14 October 2008')]";

    @AndroidFindBy(xpath = ENTERED_DATE_TUESDAY14OCTOBER2008)
    private WebElement enteredDateTuesday14October2008;

    public static final String SWITCH_TO_CALENDAR_INPUT_MODE = "//android.view.View[contains(@content-desc,'Switch to calendar input mode')]";

    @AndroidFindBy(xpath = SWITCH_TO_CALENDAR_INPUT_MODE)
    private WebElement switchToCalendarInputMode;

    public static final String DATE_DD_MM_YYYY = "//android.widget.TextView[contains(@content-desc,'Date, DD/MM/YYYY')]";

    @AndroidFindBy(xpath = DATE_DD_MM_YYYY)
    private WebElement dateDdMmYyyy;

    public static final String CANCEL = "//android.widget.TextView[@text='Cancel']";

    @AndroidFindBy(xpath = CANCEL)
    private WebElement cancel;

    public static final String OK = "//android.widget.TextView[@text='OK']";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public LicDateOfBirthSwitchToScreen(TestContext context) {
        super(context);
    }
}
