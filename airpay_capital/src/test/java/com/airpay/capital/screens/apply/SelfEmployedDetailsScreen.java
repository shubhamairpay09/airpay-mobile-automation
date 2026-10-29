package com.airpay.capital.screens.apply;

import com.airpay.capital.data.CapitalExcel;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class SelfEmployedDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Enter your details as per official records')]")
    @iOSXCUITFindBy(accessibility = "Enter your details as per official records")
    private WebElement banner;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'PAN') or contains(@hint,'Enter PAN')]")
    @iOSXCUITFindBy(accessibility = "Enter PAN")
    private WebElement panField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Desired loan amount')]")
    @iOSXCUITFindBy(accessibility = "Enter amount")
    private WebElement loanAmountField;

    public SelfEmployedDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("explicit consent", "credit information report")
                || firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Desired loan amount')]")) != null;
    }

    public void fillIfDisplayed() {
        if (!isDisplayed()) {
            return;
        }
        WebElement pan = firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'PAN')]"));
        if (pan != null) {
            type(pan, CapitalExcel.text("PAN", "ABCDE1234F"));
        }
        WebElement email = firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Email')]"));
        if (email != null) {
            type(email, CapitalExcel.text("Email", "test.user@airpay.example"));
        }
        WebElement amount = firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Desired loan amount')]"));
        if (amount != null) {
            type(amount, CapitalExcel.text("LoanAmount", "500000"));
        }
        new ApplyDetailsScreen(context).acceptTermsAndTapApply();
    }
}
