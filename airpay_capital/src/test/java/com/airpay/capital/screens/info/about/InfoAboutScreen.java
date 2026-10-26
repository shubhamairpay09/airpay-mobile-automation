package com.airpay.capital.screens.info.about;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Info → About airpay capital (native About Us). */
public class InfoAboutScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='About Us']")
    @iOSXCUITFindBy(accessibility = "About Us")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='app logo']")
    @iOSXCUITFindBy(accessibility = "app logo")
    private WebElement appLogo;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Credit made simple')]")
    @iOSXCUITFindBy(accessibility = "Credit made simple")
    private WebElement tagline;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Why customers choose us']")
    @iOSXCUITFindBy(accessibility = "Why customers choose us")
    private WebElement whyCustomersChooseUs;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Airpay Capital website')]")
    @iOSXCUITFindBy(accessibility = "Airpay Capital website")
    private WebElement websiteLink;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Terms & conditions']")
    @iOSXCUITFindBy(accessibility = "Terms & conditions")
    private WebElement termsConditions;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Privacy policy']")
    @iOSXCUITFindBy(accessibility = "Privacy policy")
    private WebElement privacyPolicy;

    public InfoAboutScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("About Us", "Why customers choose us", "Airpay Capital website");
    }

    public InfoAboutWebsiteScreen openWebsite() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Airpay Capital website')]"));
        sleep(1500);
        return new InfoAboutWebsiteScreen(context);
    }

    public InfoAboutTermsScreen openTermsAndConditions() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.ImageView[@content-desc='Terms & conditions']"));
        sleep(1000);
        return new InfoAboutTermsScreen(context);
    }

    public InfoAboutPrivacyScreen openPrivacyPolicy() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.ImageView[@content-desc='Privacy policy']"));
        sleep(1000);
        return new InfoAboutPrivacyScreen(context);
    }

    public void scrollToFooter() {
        for (int i = 0; i < 4; i++) {
            if (sourceContains("Terms & conditions", "Privacy policy", "Airpay Capital website")) {
                return;
            }
            swipeUp();
            sleep(400);
        }
    }
}
