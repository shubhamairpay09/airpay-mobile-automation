package com.airpay.vyaapaar.screens.postofficeindiapostofficetrack;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Post Office / India Post Office / TRACK — Search. Module post-office-india-post-office-track. */
public class PostOfficeIndiaPostOfficeTrackScreen extends BaseScreen {

    public static final String PAGE_ID = "post-office-india-post-office-track";

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String TRACK_MANAGE = "//android.widget.TextView[@text='Track & Manage']";

    @AndroidFindBy(xpath = TRACK_MANAGE)
    private WebElement trackManage;

    public PostOfficeIndiaPostOfficeTrackScreen(TestContext context) {
        super(context);
    }
}
