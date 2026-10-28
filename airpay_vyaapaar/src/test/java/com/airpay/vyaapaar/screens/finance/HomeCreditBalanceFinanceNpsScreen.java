package com.airpay.vyaapaar.screens.finance;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Finance / NPS — Alert. Module finance. */
public class HomeCreditBalanceFinanceNpsScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-finance-nps";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String SOMETHING_WENT_WRONG = "//android.widget.TextView[@text='Something went wrong']";

    @AndroidFindBy(xpath = SOMETHING_WENT_WRONG)
    private WebElement somethingWentWrong;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeCreditBalanceFinanceNpsScreen(TestContext context) {
        super(context);
    }
}
