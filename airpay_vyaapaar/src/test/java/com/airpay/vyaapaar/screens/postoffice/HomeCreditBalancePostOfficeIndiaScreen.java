package com.airpay.vyaapaar.screens.postoffice;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Post Office / India Post Office. Module post-office. */
public class HomeCreditBalancePostOfficeIndiaScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-post-office-india-post-office";

    public static final String BOOK_ICON = "//android.view.View[contains(@content-desc,'BOOK icon')]";

    @AndroidFindBy(xpath = BOOK_ICON)
    private WebElement bookIcon;

    public static final String BOOK = "//android.widget.TextView[@text='BOOK']";

    @AndroidFindBy(xpath = BOOK)
    private WebElement book;

    public static final String SEND_APARCEL_VIA_SPEED_POST_OR_BUSINESS = "//android.widget.TextView[@text='Send a parcel via Speed Post or Business Parcel']";

    @AndroidFindBy(xpath = SEND_APARCEL_VIA_SPEED_POST_OR_BUSINESS)
    private WebElement sendAParcelViaSpeedPostOrBusiness;

    public static final String BOOK_NEW_PARCEL = "//android.widget.TextView[@text='Book New Parcel']";

    @AndroidFindBy(xpath = BOOK_NEW_PARCEL)
    private WebElement bookNewParcel;

    public static final String FORWARD_ARROW_ICON = "//android.view.View[contains(@content-desc,'Forward Arrow Icon')]";

    @AndroidFindBy(xpath = FORWARD_ARROW_ICON)
    private WebElement forwardArrowIcon;

    public static final String TRACK_ICON = "//android.view.View[contains(@content-desc,'TRACK icon')]";

    @AndroidFindBy(xpath = TRACK_ICON)
    private WebElement trackIcon;

    public static final String TRACK = "//android.widget.TextView[@text='TRACK']";

    @AndroidFindBy(xpath = TRACK)
    private WebElement track;

    public static final String VIEW_ALL_BOOKED_PARCELS_AND_TRACK_LIVE_STATUS = "//android.widget.TextView[@text='View all booked parcels and track live status']";

    @AndroidFindBy(xpath = VIEW_ALL_BOOKED_PARCELS_AND_TRACK_LIVE_STATUS)
    private WebElement viewAllBookedParcelsAndTrackLiveStatus;

    public static final String TRACK_MANAGE = "//android.widget.TextView[@text='Track & Manage']";

    @AndroidFindBy(xpath = TRACK_MANAGE)
    private WebElement trackManage;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String SEARCH_BY_ORDER_ID_OR_TRACKING_ID = "//android.widget.TextView[@text='Search by order id or tracking id']";

    @AndroidFindBy(xpath = SEARCH_BY_ORDER_ID_OR_TRACKING_ID)
    private WebElement searchByOrderIdOrTrackingId;

    public static final String INDIA_POST = "//android.widget.TextView[@text='India Post']";

    @AndroidFindBy(xpath = INDIA_POST)
    private WebElement indiaPost;

    public HomeCreditBalancePostOfficeIndiaScreen(TestContext context) {
        super(context);
    }
}
