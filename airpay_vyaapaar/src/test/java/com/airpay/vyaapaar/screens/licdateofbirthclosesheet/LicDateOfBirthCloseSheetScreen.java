package com.airpay.vyaapaar.screens.licdateofbirthclosesheet;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** LIC / Date of Birth / Close sheet — LIC. Module lic-date-of-birth-close-sheet. */
public class LicDateOfBirthCloseSheetScreen extends BaseScreen {

    public static final String PAGE_ID = "lic-date-of-birth-close-sheet";

    public static final String ELEMENT = "//android.widget.TextView[@text='●']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String PREMIUM_PAYMENTS_WILL_BE_PROCESSED_BETWEEN8A = "//android.widget.TextView[@text='Premium payments will be processed between 8 a.m and 8 p.m.']";

    @AndroidFindBy(xpath = PREMIUM_PAYMENTS_WILL_BE_PROCESSED_BETWEEN8A)
    private WebElement premiumPaymentsWillBeProcessedBetween8A;

    public static final String TRANSACTION_SUBMITTED_AFTER_THIS_DEADLINE_WILL_BE_PROCESSED = "//android.widget.TextView[@text='Transaction submitted after this deadline will be processed the next day.']";

    @AndroidFindBy(xpath = TRANSACTION_SUBMITTED_AFTER_THIS_DEADLINE_WILL_BE_PROCESSED)
    private WebElement transactionSubmittedAfterThisDeadlineWillBeProcessed;

    public static final String PAYMENTS_MADE_ON_PUBLIC_HOLIDAY_WILL_BE_PROCESSED = "//android.widget.TextView[@text='Payments made on public holiday will be processed the next day.']";

    @AndroidFindBy(xpath = PAYMENTS_MADE_ON_PUBLIC_HOLIDAY_WILL_BE_PROCESSED)
    private WebElement paymentsMadeOnPublicHolidayWillBeProcessed;

    public static final String LIC = "//android.widget.EditText[@text='LIC']";

    @AndroidFindBy(xpath = LIC)
    private WebElement lic;

    public static final String SELECT_PROVIDER = "//android.widget.TextView[@text='Select Provider']";

    @AndroidFindBy(xpath = SELECT_PROVIDER)
    private WebElement selectProvider;

    public static final String POLICY_NUMBER = "//android.widget.TextView[@text='Policy Number']";

    @AndroidFindBy(xpath = POLICY_NUMBER)
    private WebElement policyNumber;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String CUSTOMER_EMAIL = "//android.widget.TextView[@text='Customer Email']";

    @AndroidFindBy(xpath = CUSTOMER_EMAIL)
    private WebElement customerEmail;

    public static final String DATE_OF_BIRTH = "//android.widget.TextView[@text='Date of Birth']";

    @AndroidFindBy(xpath = DATE_OF_BIRTH)
    private WebElement dateOfBirth;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String LIC_PREMIUM = "//android.widget.TextView[@text='LIC Premium']";

    @AndroidFindBy(xpath = LIC_PREMIUM)
    private WebElement licPremium;

    public LicDateOfBirthCloseSheetScreen(TestContext context) {
        super(context);
    }
}
