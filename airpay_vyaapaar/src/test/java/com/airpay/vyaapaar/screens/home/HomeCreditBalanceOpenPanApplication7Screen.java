package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Post Office / India Post Office / TRACK. Module home. */
public class HomeCreditBalanceOpenPanApplication7Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-post-office-india-post-office-track";

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String TRACK_MANAGE = "//android.widget.TextView[@text='Track & Manage']";

    @AndroidFindBy(xpath = TRACK_MANAGE)
    private WebElement trackManage;

    public HomeCreditBalanceOpenPanApplication7Screen(TestContext context) {
        super(context);
    }
}
