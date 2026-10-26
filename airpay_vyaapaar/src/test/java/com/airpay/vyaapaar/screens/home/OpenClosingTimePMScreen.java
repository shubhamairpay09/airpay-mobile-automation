package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Closing time / p.m.. Module home. */
public class OpenClosingTimePMScreen extends BaseScreen {

    public static final String PAGE_ID = "open-closing-time-p-m";

    public static final String SELECT_TIME = "//android.widget.TextView[@text='Select Time']";

    @AndroidFindBy(xpath = SELECT_TIME)
    private WebElement selectTime;

    public static final String SELECT_HOUR = "//android.view.View[contains(@content-desc,'Select hour')]";

    @AndroidFindBy(xpath = SELECT_HOUR)
    private WebElement selectHour;

    public static final String EL5OCLOCK = "//android.widget.TextView[contains(@content-desc,concat('5 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL5OCLOCK)
    private WebElement el5OClock;

    public static final String SELECT_MINUTES = "//android.view.View[contains(@content-desc,'Select minutes')]";

    @AndroidFindBy(xpath = SELECT_MINUTES)
    private WebElement selectMinutes;

    public static final String EL55MINUTES = "//android.widget.TextView[contains(@content-desc,'55 minutes')]";

    @AndroidFindBy(xpath = EL55MINUTES)
    private WebElement el55Minutes;

    public static final String SELECT_AMOR_PM = "//android.view.View[contains(@content-desc,'Select a.m. or p.m.')]";

    @AndroidFindBy(xpath = SELECT_AMOR_PM)
    private WebElement selectAMOrPM;

    public static final String P_M = "//android.widget.TextView[@text='p.m.']";

    @AndroidFindBy(xpath = P_M)
    private WebElement pM;

    public static final String A_M = "//android.widget.TextView[@text='a.m.']";

    @AndroidFindBy(xpath = A_M)
    private WebElement aM;

    public static final String EL12OCLOCK = "//android.view.View[contains(@content-desc,concat('12 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL12OCLOCK)
    private WebElement el12OClock;

    public static final String EL1OCLOCK = "//android.view.View[contains(@content-desc,concat('1 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL1OCLOCK)
    private WebElement el1OClock;

    public static final String EL2OCLOCK = "//android.view.View[contains(@content-desc,concat('2 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL2OCLOCK)
    private WebElement el2OClock;

    public static final String EL3OCLOCK = "//android.view.View[contains(@content-desc,concat('3 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL3OCLOCK)
    private WebElement el3OClock;

    public static final String EL4OCLOCK = "//android.view.View[contains(@content-desc,concat('4 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL4OCLOCK)
    private WebElement el4OClock;

    public static final String EL6OCLOCK = "//android.view.View[contains(@content-desc,concat('6 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL6OCLOCK)
    private WebElement el6OClock;

    public static final String EL7OCLOCK = "//android.view.View[contains(@content-desc,concat('7 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL7OCLOCK)
    private WebElement el7OClock;

    public static final String EL8OCLOCK = "//android.view.View[contains(@content-desc,concat('8 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL8OCLOCK)
    private WebElement el8OClock;

    public static final String EL9OCLOCK = "//android.view.View[contains(@content-desc,concat('9 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL9OCLOCK)
    private WebElement el9OClock;

    public static final String EL10OCLOCK = "//android.view.View[contains(@content-desc,concat('10 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL10OCLOCK)
    private WebElement el10OClock;

    public static final String EL11OCLOCK = "//android.view.View[contains(@content-desc,concat('11 o',\"'\",'clock'))]";

    @AndroidFindBy(xpath = EL11OCLOCK)
    private WebElement el11OClock;

    public static final String CANCEL = "//android.widget.TextView[@text='Cancel']";

    @AndroidFindBy(xpath = CANCEL)
    private WebElement cancel;

    public static final String OK = "//android.widget.TextView[@text='Ok']";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public OpenClosingTimePMScreen(TestContext context) {
        super(context);
    }
}
