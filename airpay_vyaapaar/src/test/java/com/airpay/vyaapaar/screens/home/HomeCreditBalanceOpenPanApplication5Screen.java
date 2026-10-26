package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Post Office / India Post Office / BOOK. Module home. */
public class HomeCreditBalanceOpenPanApplication5Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-post-office-india-post-office-book";

    public static final String EL1 = "//android.widget.TextView[@text='1']";

    @AndroidFindBy(xpath = EL1)
    private WebElement el1;

    public static final String TARIFF_CHECK = "//android.widget.TextView[@text='Tariff Check']";

    @AndroidFindBy(xpath = TARIFF_CHECK)
    private WebElement tariffCheck;

    public static final String EL2 = "//android.widget.TextView[@text='2']";

    @AndroidFindBy(xpath = EL2)
    private WebElement el2;

    public static final String EL3 = "//android.widget.TextView[@text='3']";

    @AndroidFindBy(xpath = EL3)
    private WebElement el3;

    public static final String EL4 = "//android.widget.TextView[@text='4']";

    @AndroidFindBy(xpath = EL4)
    private WebElement el4;

    public static final String EL5 = "//android.widget.TextView[@text='5']";

    @AndroidFindBy(xpath = EL5)
    private WebElement el5;

    public static final String EL6 = "//android.widget.TextView[@text='6']";

    @AndroidFindBy(xpath = EL6)
    private WebElement el6;

    public static final String TARIFF_SERVICE_CHECK = "//android.widget.TextView[@text='Tariff & Service Check']";

    @AndroidFindBy(xpath = TARIFF_SERVICE_CHECK)
    private WebElement tariffServiceCheck;

    public static final String SPEED_POST_ICON = "//android.view.View[contains(@content-desc,'Speed Post icon')]";

    @AndroidFindBy(xpath = SPEED_POST_ICON)
    private WebElement speedPostIcon;

    public static final String SPEED_POST = "//android.widget.TextView[@text='Speed Post']";

    @AndroidFindBy(xpath = SPEED_POST)
    private WebElement speedPost;

    public static final String QUICK_TARIFF_ESTIMATE = "//android.widget.TextView[@text='Quick Tariff Estimate']";

    @AndroidFindBy(xpath = QUICK_TARIFF_ESTIMATE)
    private WebElement quickTariffEstimate;

    public static final String SOURCE_PINCODE = "//android.widget.TextView[@text='Source Pincode']";

    @AndroidFindBy(xpath = SOURCE_PINCODE)
    private WebElement sourcePincode;

    public static final String DESTINATION_PINCODE = "//android.widget.TextView[@text='Destination Pincode']";

    @AndroidFindBy(xpath = DESTINATION_PINCODE)
    private WebElement destinationPincode;

    public static final String ARTICLE_SHAPE = "//android.widget.TextView[@text='Article Shape']";

    @AndroidFindBy(xpath = ARTICLE_SHAPE)
    private WebElement articleShape;

    public static final String DOCUMENT_FLAT_ICON = "//android.view.View[contains(@content-desc,'Document/ Flat icon')]";

    @AndroidFindBy(xpath = DOCUMENT_FLAT_ICON)
    private WebElement documentFlatIcon;

    public static final String DOCUMENT_FLAT = "//android.widget.TextView[@text='Document/ Flat']";

    @AndroidFindBy(xpath = DOCUMENT_FLAT)
    private WebElement documentFlat;

    public static final String ENVELOPES_FLAT_PACKETS = "//android.widget.TextView[@text='Envelopes, flat packets']";

    @AndroidFindBy(xpath = ENVELOPES_FLAT_PACKETS)
    private WebElement envelopesFlatPackets;

    public static final String DIMENSIONS = "//android.widget.TextView[@text='Dimensions']";

    @AndroidFindBy(xpath = DIMENSIONS)
    private WebElement dimensions;

    public static final String PHYSICAL_WEIGHT_GRAMS = "//android.widget.TextView[@text='Physical Weight (grams)']";

    @AndroidFindBy(xpath = PHYSICAL_WEIGHT_GRAMS)
    private WebElement physicalWeightGrams;

    public static final String MIN1GM_MAX500GM = "//android.widget.TextView[@text='Min: 1gm  Max: 500gm']";

    @AndroidFindBy(xpath = MIN1GM_MAX500GM)
    private WebElement min1gmMax500gm;

    public static final String BOOK_NEW_PARCEL = "//android.widget.TextView[@text='Book New Parcel']";

    @AndroidFindBy(xpath = BOOK_NEW_PARCEL)
    private WebElement bookNewParcel;

    public static final String ESTIMATE_TARIFF = "//android.widget.TextView[@text='Estimate Tariff']";

    @AndroidFindBy(xpath = ESTIMATE_TARIFF)
    private WebElement estimateTariff;

    public HomeCreditBalanceOpenPanApplication5Screen(TestContext context) {
        super(context);
    }
}
