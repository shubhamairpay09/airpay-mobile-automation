package com.airpay.money.screens.menu;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class FaqsScreen extends AbstractMenuScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'FAQs') or contains(@content-desc,'FAQ')]")
    @iOSXCUITFindBy(accessibility = "FAQs")
    private WebElement heading;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Regulatory')]")
    @iOSXCUITFindBy(accessibility = "Regulatory")
    private WebElement regulatorySection;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Grievance')]")
    @iOSXCUITFindBy(accessibility = "Grievance")
    private WebElement grievanceSection;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'?') or @clickable='true']")
    @iOSXCUITFindBy(className = "XCUIElementTypeButton")
    private WebElement accordionItem;

    public FaqsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("FAQ", "Frequently", "Question");
    }

    public void expandFirstAccordion() {
        if (!tapFirst(java.time.Duration.ofSeconds(3),
                com.airpay.mobile.utilities.waits.MobileWaits.xpath(
                        "//android.widget.Button[@clickable='true']"))) {
            swipeUp();
        }
    }
}
