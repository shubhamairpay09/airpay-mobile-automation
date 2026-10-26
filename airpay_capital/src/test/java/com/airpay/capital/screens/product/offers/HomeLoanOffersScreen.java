package com.airpay.capital.screens.product.offers;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class HomeLoanOffersScreen extends AbstractOffersScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'offer available')]")
    @iOSXCUITFindBy(accessibility = "offer available")
    private WebElement offersBanner;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'No Offers Available')]")
    @iOSXCUITFindBy(accessibility = "No Offers Available")
    private WebElement noOffersMessage;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Apply now for')]")
    @iOSXCUITFindBy(accessibility = "Apply now for")
    private WebElement applyNowForLender;

    public HomeLoanOffersScreen(TestContext context) {
        super(context);
    }

    @Override
    public void tapFirstLenderOffer() {
        String before = safeSource();
        if (!tapFirstLenderApply("//android.widget.Button[contains(@content-desc,'Apply now for')]")) {
            throw new IllegalStateException("No lender Apply now on Home Loan offers screen.");
        }
        if (!lenderPageOpened(before)) {
            throw new IllegalStateException("Tapping Home Loan offer did not open lender page.");
        }
    }
}
