package com.airpay.vyaapaar.screens.electricity;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Electricity — Submit. Module electricity. */
public class HomeCreditBalanceElectricityScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-electricity";

    public static final String ELEMENT = "//android.widget.TextView[@text='●']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String BILL_PAYMENTS_WILL_BE_PROCESSED_BETWEEN8A = "//android.widget.TextView[@text='Bill payments will be processed between 8 a.m and 8 p.m.']";

    @AndroidFindBy(xpath = BILL_PAYMENTS_WILL_BE_PROCESSED_BETWEEN8A)
    private WebElement billPaymentsWillBeProcessedBetween8A;

    public static final String TRANSACTION_SUBMITTED_AFTER_THIS_DEADLINE_WILL_BE_PROCESSED = "//android.widget.TextView[@text='Transaction submitted after this deadline will be processed the next day.']";

    @AndroidFindBy(xpath = TRANSACTION_SUBMITTED_AFTER_THIS_DEADLINE_WILL_BE_PROCESSED)
    private WebElement transactionSubmittedAfterThisDeadlineWillBeProcessed;

    public static final String PAYMENTS_MADE_ON_PUBLIC_HOLIDAY_WILL_BE_PROCESSED = "//android.widget.TextView[@text='Payments made on public holiday will be processed the next day.']";

    @AndroidFindBy(xpath = PAYMENTS_MADE_ON_PUBLIC_HOLIDAY_WILL_BE_PROCESSED)
    private WebElement paymentsMadeOnPublicHolidayWillBeProcessed;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String ELECTRICITY = "//android.widget.TextView[@text='Electricity']";

    @AndroidFindBy(xpath = ELECTRICITY)
    private WebElement electricity;

    public HomeCreditBalanceElectricityScreen(TestContext context) {
        super(context);
    }
}
