package com.airpay.capital.screens.info.faqs;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Info → FAQs hub (Regulatory Disclosure + Grievance Details). */
public class InfoFaqsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='FAQ']")
    @iOSXCUITFindBy(accessibility = "FAQ")
    private WebElement heading;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Regulatory Disclosure')]")
    @iOSXCUITFindBy(accessibility = "Regulatory Disclosure")
    private WebElement regulatoryDisclosure;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Grievance Details')]")
    @iOSXCUITFindBy(accessibility = "Grievance Details")
    private WebElement grievanceDetails;

    public InfoFaqsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("FAQ", "Regulatory Disclosure", "Grievance Details");
    }

    public InfoFaqsRegulatoryScreen openRegulatoryDisclosure() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[contains(@content-desc,'Regulatory Disclosure')]"));
        sleep(800);
        return new InfoFaqsRegulatoryScreen(context);
    }

    public InfoFaqsGrievanceScreen openGrievanceDetails() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[contains(@content-desc,'Grievance Details')]"));
        sleep(800);
        return new InfoFaqsGrievanceScreen(context);
    }
}
