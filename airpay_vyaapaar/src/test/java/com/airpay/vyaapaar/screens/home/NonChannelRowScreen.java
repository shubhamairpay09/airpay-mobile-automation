package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non-Channel — PAN Application. Module home. */
public class NonChannelRowScreen extends BaseScreen {

    public static final String PAGE_ID = "non-channel-row";

    public static final String PAN_APPLICATION = "//android.widget.TextView[@text='PAN Application']";

    @AndroidFindBy(xpath = PAN_APPLICATION)
    private WebElement panApplication;

    public static final String AADHAAR_PAY_ICON = "//android.view.View[contains(@content-desc,'Aadhaar Pay icon')]";

    @AndroidFindBy(xpath = AADHAAR_PAY_ICON)
    private WebElement aadhaarPayIcon;

    public static final String AADHAAR_PAY = "//android.widget.TextView[@text='Aadhaar Pay']";

    @AndroidFindBy(xpath = AADHAAR_PAY)
    private WebElement aadhaarPay;

    public static final String CMS_ICON = "//android.view.View[contains(@content-desc,'CMS icon')]";

    @AndroidFindBy(xpath = CMS_ICON)
    private WebElement cmsIcon;

    public static final String CMS = "//android.widget.TextView[@text='CMS']";

    @AndroidFindBy(xpath = CMS)
    private WebElement cms;

    public static final String KHATA_ICON = "//android.view.View[contains(@content-desc,'Khata icon')]";

    @AndroidFindBy(xpath = KHATA_ICON)
    private WebElement khataIcon;

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public static final String DASHBOARD_ICON = "//android.view.View[contains(@content-desc,'Dashboard icon')]";

    @AndroidFindBy(xpath = DASHBOARD_ICON)
    private WebElement dashboardIcon;

    public static final String DASHBOARD = "//android.widget.TextView[@text='Dashboard']";

    @AndroidFindBy(xpath = DASHBOARD)
    private WebElement dashboard;

    public static final String WITHDRAWAL_LIMIT_ICON = "//android.view.View[contains(@content-desc,'Withdrawal Limit icon')]";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT_ICON)
    private WebElement withdrawalLimitIcon;

    public static final String WITHDRAWAL_LIMIT = "//android.widget.TextView[@text='Withdrawal Limit']";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT)
    private WebElement withdrawalLimit;

    public static final String PREPAID_CARDS_ICON = "//android.view.View[contains(@content-desc,'Prepaid Cards icon')]";

    @AndroidFindBy(xpath = PREPAID_CARDS_ICON)
    private WebElement prepaidCardsIcon;

    public static final String PREPAID_CARDS = "//android.widget.TextView[@text='Prepaid Cards']";

    @AndroidFindBy(xpath = PREPAID_CARDS)
    private WebElement prepaidCards;

    public static final String MONEY_TRANSFER = "//android.widget.TextView[@text='Money Transfer']";

    @AndroidFindBy(xpath = MONEY_TRANSFER)
    private WebElement moneyTransfer;

    public static final String MONEY_TRANSFER_ICON = "//android.view.View[contains(@content-desc,'Money Transfer icon')]";

    @AndroidFindBy(xpath = MONEY_TRANSFER_ICON)
    private WebElement moneyTransferIcon;

    public static final String FINO_SUVIDHA_DMT_ICON = "//android.view.View[contains(@content-desc,'Fino Suvidha DMT icon')]";

    @AndroidFindBy(xpath = FINO_SUVIDHA_DMT_ICON)
    private WebElement finoSuvidhaDmtIcon;

    public static final String FINO_SUVIDHA_DMT = "//android.widget.TextView[@text='Fino Suvidha DMT']";

    @AndroidFindBy(xpath = FINO_SUVIDHA_DMT)
    private WebElement finoSuvidhaDmt;

    public static final String NON_CHANNELS = "//android.widget.TextView[@text='Non Channels']";

    @AndroidFindBy(xpath = NON_CHANNELS)
    private WebElement nonChannels;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String COLLECT_MONEY_ICON = "//android.view.View[contains(@content-desc,'Collect Money icon')]";

    @AndroidFindBy(xpath = COLLECT_MONEY_ICON)
    private WebElement collectMoneyIcon;

    public static final String COLLECT_MONEY = "//android.widget.TextView[@text='Collect Money']";

    @AndroidFindBy(xpath = COLLECT_MONEY)
    private WebElement collectMoney;

    public static final String BAAZAAR_ICON = "//android.view.View[contains(@content-desc,'Baazaar icon')]";

    @AndroidFindBy(xpath = BAAZAAR_ICON)
    private WebElement baazaarIcon;

    public static final String BAAZAAR = "//android.widget.TextView[@text='Baazaar']";

    @AndroidFindBy(xpath = BAAZAAR)
    private WebElement baazaar;

    public static final String NOTIFICATIONS = "//android.view.View[contains(@content-desc,'Notifications')]";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

    public static final String HOME = "//android.widget.TextView[@text='Home']";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[@text='Services']";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[@text='History']";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[@text='Profile']";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public NonChannelRowScreen(TestContext context) {
        super(context);
    }
}
