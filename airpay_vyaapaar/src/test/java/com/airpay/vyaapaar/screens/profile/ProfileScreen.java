package com.airpay.vyaapaar.screens.profile;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Profile. Module profile. */
public class ProfileScreen extends BaseScreen {

    public static final String PAGE_ID = "profile";

    public static final String VT = "//android.widget.TextView[contains(@content-desc,'VT')]";

    @AndroidFindBy(xpath = VT)
    private WebElement vt;

    public static final String VCARE_TEST = "//android.widget.TextView[contains(@content-desc,'Vcare test')]";

    @AndroidFindBy(xpath = VCARE_TEST)
    private WebElement vcareTest;

    public static final String LOGOUT_ICON = "//android.view.View[contains(@content-desc,'Logout icon')]";

    @AndroidFindBy(xpath = LOGOUT_ICON)
    private WebElement logoutIcon;

    public static final String LOGOUT = "//android.widget.TextView[contains(@content-desc,'Logout')]";

    @AndroidFindBy(xpath = LOGOUT)
    private WebElement logout;

    public static final String GST_NUMBER = "//android.widget.TextView[contains(@content-desc,'GST Number')]";

    @AndroidFindBy(xpath = GST_NUMBER)
    private WebElement gstNumber;

    public static final String N_A = "//android.widget.TextView[contains(@content-desc,'N/A')]";

    @AndroidFindBy(xpath = N_A)
    private WebElement nA;

    public static final String PAN_NUMBER = "//android.widget.TextView[contains(@content-desc,'PAN Number')]";

    @AndroidFindBy(xpath = PAN_NUMBER)
    private WebElement panNumber;

    public static final String ASXXXXXX1F = "//android.widget.TextView[contains(@content-desc,'ASXXXXXX1F')]";

    @AndroidFindBy(xpath = ASXXXXXX1F)
    private WebElement asxxxxxx1f;

    public static final String ADDRESS = "//android.widget.TextView[contains(@content-desc,'Address')]";

    @AndroidFindBy(xpath = ADDRESS)
    private WebElement address;

    public static final String EL6SRINIWAS_BLDG_ZAVER_ROAD_MULUND_WEST_MUMBAI = "//android.widget.TextView[contains(@content-desc,'6,sriniwas bldg,zaver road,mulund-west, Mumbai, Maharashtra, 0, 400080')]";

    @AndroidFindBy(xpath = EL6SRINIWAS_BLDG_ZAVER_ROAD_MULUND_WEST_MUMBAI)
    private WebElement el6SriniwasBldgZaverRoadMulundWestMumbai;

    public static final String QR_ACTIVATION_ICON = "//android.view.View[contains(@content-desc,'QR Activation icon')]";

    @AndroidFindBy(xpath = QR_ACTIVATION_ICON)
    private WebElement qrActivationIcon;

    public static final String QR_ACTIVATION = "//android.widget.TextView[contains(@content-desc,'QR Activation')]";

    @AndroidFindBy(xpath = QR_ACTIVATION)
    private WebElement qrActivation;

    public static final String FORWARD_ARROW_ICON = "//android.view.View[contains(@content-desc,'Forward Arrow Icon')]";

    @AndroidFindBy(xpath = FORWARD_ARROW_ICON)
    private WebElement forwardArrowIcon;

    public static final String MANAGE_PIN_ICON = "//android.view.View[contains(@content-desc,'Manage PIN icon')]";

    @AndroidFindBy(xpath = MANAGE_PIN_ICON)
    private WebElement managePinIcon;

    public static final String MANAGE_PIN = "//android.widget.TextView[contains(@content-desc,'Manage PIN')]";

    @AndroidFindBy(xpath = MANAGE_PIN)
    private WebElement managePin;

    public static final String CHANGE_PASSWORD_ICON = "//android.view.View[contains(@content-desc,'Change Password icon')]";

    @AndroidFindBy(xpath = CHANGE_PASSWORD_ICON)
    private WebElement changePasswordIcon;

    public static final String CHANGE_PASSWORD = "//android.widget.TextView[contains(@content-desc,'Change Password')]";

    @AndroidFindBy(xpath = CHANGE_PASSWORD)
    private WebElement changePassword;

    public static final String BC_CERTIFICATE_ICON = "//android.view.View[contains(@content-desc,'BC Certificate icon')]";

    @AndroidFindBy(xpath = BC_CERTIFICATE_ICON)
    private WebElement bcCertificateIcon;

    public static final String BC_CERTIFICATE = "//android.widget.TextView[contains(@content-desc,'BC Certificate')]";

    @AndroidFindBy(xpath = BC_CERTIFICATE)
    private WebElement bcCertificate;

    public static final String MY_COMMERCIALS_ICON = "//android.view.View[contains(@content-desc,'My Commercials icon')]";

    @AndroidFindBy(xpath = MY_COMMERCIALS_ICON)
    private WebElement myCommercialsIcon;

    public static final String MY_COMMERCIALS = "//android.widget.TextView[contains(@content-desc,'My Commercials')]";

    @AndroidFindBy(xpath = MY_COMMERCIALS)
    private WebElement myCommercials;

    public static final String COPYRIGHT2026AIRPAY_VYAAPAAR_VERSION3020 = "//android.widget.TextView[contains(@content-desc,'Copyright © 2026 airpay vyaapaar  • Version 3.0.20-staging-accessibility')]";

    @AndroidFindBy(xpath = COPYRIGHT2026AIRPAY_VYAAPAAR_VERSION3020)
    private WebElement copyright2026AirpayVyaapaarVersion3020;

    public static final String HOME = "//android.widget.TextView[contains(@content-desc,'Home')]";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[contains(@content-desc,'Services')]";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[contains(@content-desc,'History')]";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[contains(@content-desc,'Profile')]";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public ProfileScreen(TestContext context) {
        super(context);
    }
}
