package com.airpay.vyaapaar.screens.withdrawallimit;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Withdrawal Limit — Withdrawal Limit. Module withdrawal-limit. */
public class WithdrawalLimitScreen extends BaseScreen {

    public static final String PAGE_ID = "withdrawal-limit";

    public static final String WITHDRAWAL_LIMIT = "//android.widget.TextView[@text='Withdrawal Limit']";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT)
    private WebElement withdrawalLimit;

    public WithdrawalLimitScreen(TestContext context) {
        super(context);
    }
}
