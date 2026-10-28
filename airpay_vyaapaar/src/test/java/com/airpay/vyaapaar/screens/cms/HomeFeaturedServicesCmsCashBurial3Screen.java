package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Burial/CE. Module cms. */
public class HomeFeaturedServicesCmsCashBurial3Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-burial-ce";

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public HomeFeaturedServicesCmsCashBurial3Screen(TestContext context) {
        super(context);
    }
}
