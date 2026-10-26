package com.airpay.capital.screens.info.faqs;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class InfoFaqsGrievanceScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Grievance Details')]")
    @iOSXCUITFindBy(accessibility = "Grievance Details")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,'Search') or contains(@hint,'Search')]")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchField;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Nodal Officer')]")
    @iOSXCUITFindBy(accessibility = "Nodal Officer")
    private WebElement nodalOfficerFaq;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Muthoot FinCorp')]")
    @iOSXCUITFindBy(accessibility = "Muthoot FinCorp")
    private WebElement muthootPartner;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Apollo Finvest')]")
    @iOSXCUITFindBy(accessibility = "Apollo Finvest")
    private WebElement apolloPartner;

    public InfoFaqsGrievanceScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Grievance Details", "Nodal Officer");
    }

    public void search(String query) {
        WebElement field = firstPresent(java.time.Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Search') or contains(@content-desc,'Search')]"));
        if (field != null) {
            type(field, query);
        }
    }
}
