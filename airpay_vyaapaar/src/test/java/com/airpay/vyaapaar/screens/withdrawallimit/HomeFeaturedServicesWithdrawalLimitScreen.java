package com.airpay.vyaapaar.screens.withdrawallimit;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Withdrawal Limit. Module withdrawal-limit. */
public class HomeFeaturedServicesWithdrawalLimitScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-withdrawal-limit";

    public static final String WITHDRAWAL_LIMIT = "//android.widget.TextView[@text='Withdrawal Limit']";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT)
    private WebElement withdrawalLimit;

    public HomeFeaturedServicesWithdrawalLimitScreen(TestContext context) {
        super(context);
    }
}
