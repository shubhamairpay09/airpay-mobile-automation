package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Merchant Details. Module home. */
public class OpenMerchantDetailsScreen extends BaseScreen {

    public static final String PAGE_ID = "open-merchant-details";

    public static final String MERCHANT_DETAILS = "//android.widget.TextView[@text='Merchant Details']";

    @AndroidFindBy(xpath = MERCHANT_DETAILS)
    private WebElement merchantDetails;

    public static final String CANCEL_ICON = "//android.view.View[contains(@content-desc,'Cancel icon')]";

    @AndroidFindBy(xpath = CANCEL_ICON)
    private WebElement cancelIcon;

    public static final String PARENT_MID = "//android.widget.TextView[@text='Parent MID']";

    @AndroidFindBy(xpath = PARENT_MID)
    private WebElement parentMid;

    public static final String LINKED_MID = "//android.widget.TextView[@text='Linked MID']";

    @AndroidFindBy(xpath = LINKED_MID)
    private WebElement linkedMid;

    public static final String RELATIONSHIP = "//android.widget.TextView[@text='Relationship']";

    @AndroidFindBy(xpath = RELATIONSHIP)
    private WebElement relationship;

    public static final String SPOUSE = "//android.widget.TextView[@text='Spouse']";

    @AndroidFindBy(xpath = SPOUSE)
    private WebElement spouse;

    public static final String OK = "//android.widget.TextView[@text='OK']";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public OpenMerchantDetailsScreen(TestContext context) {
        super(context);
    }
}
