package com.airpay.vyaapaar.screens.withdrawallimit;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Withdrawal Limit/RTGS/Transfer to Distributor/RTGS/Transfer to Network. Module withdrawal-limit. */
public class HomeFeaturedServicesWithdrawalLimitRtgs5Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-withdrawal-limit-rtgs-transfer-to-distributor-rtgs-rtgs-transfer-to";

    public static final String WITHDRAWABLE_BALANCE = "//android.widget.TextView[contains(@content-desc,'Withdrawable Balance')]";

    @AndroidFindBy(xpath = WITHDRAWABLE_BALANCE)
    private WebElement withdrawableBalance;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[contains(@content-desc,'Mobile Number')]";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String TRANSFER_TO_NETWORK = "//android.widget.TextView[@text='Transfer to Network']";

    @AndroidFindBy(xpath = TRANSFER_TO_NETWORK)
    private WebElement transferToNetwork;

    public HomeFeaturedServicesWithdrawalLimitRtgs5Screen(TestContext context) {
        super(context);
    }
}
