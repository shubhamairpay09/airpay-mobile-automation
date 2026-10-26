package com.airpay.capital.screens.info.faqs;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class InfoFaqsRegulatoryScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Regulatory Disclosure')]")
    @iOSXCUITFindBy(accessibility = "Regulatory Disclosure")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'Search') or contains(@hint,'Search')]")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchField;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'RBI Sachet portal')]")
    @iOSXCUITFindBy(accessibility = "RBI Sachet portal")
    private WebElement sachetFaq;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'sachet.rbi.org.in')]")
    @iOSXCUITFindBy(accessibility = "sachet.rbi.org.in")
    private WebElement sachetUrl;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'RBI Digital Lending Guidelines')]")
    @iOSXCUITFindBy(accessibility = "RBI Digital Lending Guidelines")
    private WebElement digitalLendingFaq;

    public InfoFaqsRegulatoryScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Regulatory Disclosure", "RBI Sachet", "Digital Lending");
    }

    public void search(String query) {
        WebElement field = firstPresent(java.time.Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Search') or contains(@content-desc,'Search')]"));
        if (field != null) {
            type(field, query);
        }
    }
}
