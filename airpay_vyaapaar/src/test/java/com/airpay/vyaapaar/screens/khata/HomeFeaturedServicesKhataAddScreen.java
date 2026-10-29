package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Khata/Add. Module khata. */
public class HomeFeaturedServicesKhataAddScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-khata-add";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String FAIL_MINIMUM3AND_MAXIMUM50CHARACTERS_ALLOWED = "//android.widget.TextView[@text='Fail: Minimum 3 and maximum 50 characters allowed for business name']";

    @AndroidFindBy(xpath = FAIL_MINIMUM3AND_MAXIMUM50CHARACTERS_ALLOWED)
    private WebElement failMinimum3AndMaximum50CharactersAllowed;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeFeaturedServicesKhataAddScreen(TestContext context) {
        super(context);
    }
}
