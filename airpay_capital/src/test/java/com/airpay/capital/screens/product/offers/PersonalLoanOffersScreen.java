package com.airpay.capital.screens.product.offers;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class PersonalLoanOffersScreen extends AbstractOffersScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'offers available')]")
    @iOSXCUITFindBy(accessibility = "offers available")
    private WebElement offersBanner;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Apply now for')]")
    @iOSXCUITFindBy(accessibility = "Apply now for")
    private WebElement applyNowForLender;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Muthoot FinCorp Ltd')]")
    @iOSXCUITFindBy(accessibility = "Muthoot FinCorp Ltd")
    private WebElement offerMuthoot;

    public PersonalLoanOffersScreen(TestContext context) {
        super(context);
    }

    @Override
    public void tapFirstLenderOffer() {
        String before = safeSource();
        if (!tapFirstLenderApply(
                "//android.widget.Button[contains(@content-desc,'Apply now for')]",
                "//android.widget.Button[contains(@content-desc,'Muthoot FinCorp Ltd')]")) {
            throw new IllegalStateException("No lender Apply now on Personal Loan offers screen.");
        }
        if (!lenderPageOpened(before)) {
            throw new IllegalStateException("Tapping Personal Loan offer did not open lender page.");
        }
    }
}
