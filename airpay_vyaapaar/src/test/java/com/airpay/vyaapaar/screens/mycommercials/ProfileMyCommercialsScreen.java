package com.airpay.vyaapaar.screens.mycommercials;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** My Commercials — Image Content. Module my-commercials. */
public class ProfileMyCommercialsScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-my-commercials";

    public static final String IMAGE_CONTENT = "//android.view.View[contains(@content-desc,'Image Content')]";

    @AndroidFindBy(xpath = IMAGE_CONTENT)
    private WebElement imageContent;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public static final String MY_COMMERCIALS = "//android.widget.TextView[@text='My Commercials']";

    @AndroidFindBy(xpath = MY_COMMERCIALS)
    private WebElement myCommercials;

    public static final String DOWNLOAD_ICON = "//android.view.View[contains(@content-desc,'Download Icon')]";

    @AndroidFindBy(xpath = DOWNLOAD_ICON)
    private WebElement downloadIcon;

    public ProfileMyCommercialsScreen(TestContext context) {
        super(context);
    }
}
