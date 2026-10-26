package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Closing time / 50 minutes. Module home. */
public class OpenClosingTime50MinutesScreen extends BaseScreen {

    public static final String PAGE_ID = "open-closing-time-50-minutes";

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

    public static final String EL50MINUTES = "//android.widget.TextView[contains(@content-desc,'50 minutes')]";

    @AndroidFindBy(xpath = EL50MINUTES)
    private WebElement el50Minutes;

    public static final String SELECT_AMOR_PM = "//android.view.View[contains(@content-desc,'Select a.m. or p.m.')]";

    @AndroidFindBy(xpath = SELECT_AMOR_PM)
    private WebElement selectAMOrPM;

    public static final String A_M = "//android.widget.TextView[@text='a.m.']";

    @AndroidFindBy(xpath = A_M)
    private WebElement aM;

    public static final String P_M = "//android.widget.TextView[@text='p.m.']";

    @AndroidFindBy(xpath = P_M)
    private WebElement pM;

    public static final String EL0MINUTES = "//android.view.View[contains(@content-desc,'0 minutes')]";

    @AndroidFindBy(xpath = EL0MINUTES)
    private WebElement el0Minutes;

    public static final String EL5MINUTES = "//android.view.View[contains(@content-desc,'5 minutes')]";

    @AndroidFindBy(xpath = EL5MINUTES)
    private WebElement el5Minutes;

    public static final String EL10MINUTES = "//android.view.View[contains(@content-desc,'10 minutes')]";

    @AndroidFindBy(xpath = EL10MINUTES)
    private WebElement el10Minutes;

    public static final String EL15MINUTES = "//android.view.View[contains(@content-desc,'15 minutes')]";

    @AndroidFindBy(xpath = EL15MINUTES)
    private WebElement el15Minutes;

    public static final String EL20MINUTES = "//android.view.View[contains(@content-desc,'20 minutes')]";

    @AndroidFindBy(xpath = EL20MINUTES)
    private WebElement el20Minutes;

    public static final String EL25MINUTES = "//android.view.View[contains(@content-desc,'25 minutes')]";

    @AndroidFindBy(xpath = EL25MINUTES)
    private WebElement el25Minutes;

    public static final String EL30MINUTES = "//android.view.View[contains(@content-desc,'30 minutes')]";

    @AndroidFindBy(xpath = EL30MINUTES)
    private WebElement el30Minutes;

    public static final String EL35MINUTES = "//android.view.View[contains(@content-desc,'35 minutes')]";

    @AndroidFindBy(xpath = EL35MINUTES)
    private WebElement el35Minutes;

    public static final String EL40MINUTES = "//android.view.View[contains(@content-desc,'40 minutes')]";

    @AndroidFindBy(xpath = EL40MINUTES)
    private WebElement el40Minutes;

    public static final String EL45MINUTES = "//android.view.View[contains(@content-desc,'45 minutes')]";

    @AndroidFindBy(xpath = EL45MINUTES)
    private WebElement el45Minutes;

    public static final String EL55MINUTES = "//android.view.View[contains(@content-desc,'55 minutes')]";

    @AndroidFindBy(xpath = EL55MINUTES)
    private WebElement el55Minutes;

    public static final String CANCEL = "//android.widget.TextView[@text='Cancel']";

    @AndroidFindBy(xpath = CANCEL)
    private WebElement cancel;

    public static final String OK = "//android.widget.TextView[@text='Ok']";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public OpenClosingTime50MinutesScreen(TestContext context) {
        super(context);
    }
}
