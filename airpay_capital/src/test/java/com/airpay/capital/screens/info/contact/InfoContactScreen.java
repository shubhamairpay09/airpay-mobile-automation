package com.airpay.capital.screens.info.contact;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/** Info → Contact Us. */
public class InfoContactScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Contact Us']")
    @iOSXCUITFindBy(accessibility = "Contact Us")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'here to help you anytime')]")
    @iOSXCUITFindBy(accessibility = "here to help you anytime")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Contact number')]")
    @iOSXCUITFindBy(accessibility = "Contact number")
    private WebElement contactNumber;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'020 69110333')]")
    @iOSXCUITFindBy(accessibility = "020 69110333")
    private WebElement phone;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Mon-Sat')]")
    @iOSXCUITFindBy(accessibility = "Mon-Sat")
    private WebElement hours;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Email us')]")
    @iOSXCUITFindBy(accessibility = "Email us")
    private WebElement emailUs;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'capitalsupport@airpay.co.in')]")
    @iOSXCUITFindBy(accessibility = "capitalsupport@airpay.co.in")
    private WebElement supportEmail;

    public InfoContactScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Contact Us", "020 69110333", "capitalsupport@airpay.co.in");
    }

    public String phoneNumber() {
        return "020 69110333";
    }

    public String supportEmailAddress() {
        return "capitalsupport@airpay.co.in";
    }
}
