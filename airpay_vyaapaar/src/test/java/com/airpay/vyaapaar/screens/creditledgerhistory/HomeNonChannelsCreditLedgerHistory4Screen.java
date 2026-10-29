package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Show More/Baazaar. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory4Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-show-more-baazaar";

    public static final String SEARCH_OIL_ALMONDS_EARPHONES_HERE = "//android.widget.TextView[contains(@content-desc,'Search oil, almonds, earphones here')]";

    @AndroidFindBy(xpath = SEARCH_OIL_ALMONDS_EARPHONES_HERE)
    private WebElement searchOilAlmondsEarphonesHere;

    public static final String CATEGORIES = "//android.widget.TextView[contains(@content-desc,'Categories')]";

    @AndroidFindBy(xpath = CATEGORIES)
    private WebElement categories;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String GROCERY = "//android.widget.TextView[contains(@content-desc,'Grocery')]";

    @AndroidFindBy(xpath = GROCERY)
    private WebElement grocery;

    public static final String FASHION = "//android.widget.TextView[contains(@content-desc,'Fashion')]";

    @AndroidFindBy(xpath = FASHION)
    private WebElement fashion;

    public static final String ELECTRONICS = "//android.widget.TextView[contains(@content-desc,'Electronics')]";

    @AndroidFindBy(xpath = ELECTRONICS)
    private WebElement electronics;

    public static final String HEALTH_WELLNESS = "//android.widget.TextView[contains(@content-desc,'Health & Wellness')]";

    @AndroidFindBy(xpath = HEALTH_WELLNESS)
    private WebElement healthWellness;

    public static final String BEAUTY_PERSONAL_CARE = "//android.widget.TextView[contains(@content-desc,'Beauty & Personal care')]";

    @AndroidFindBy(xpath = BEAUTY_PERSONAL_CARE)
    private WebElement beautyPersonalCare;

    public static final String TEST = "//android.widget.TextView[contains(@content-desc,'test')]";

    @AndroidFindBy(xpath = TEST)
    private WebElement test;

    public static final String PREMIUM_PICKS = "//android.widget.TextView[contains(@content-desc,'Premium Picks')]";

    @AndroidFindBy(xpath = PREMIUM_PICKS)
    private WebElement premiumPicks;

    public static final String VIEW_ALL = "//android.widget.TextView[contains(@content-desc,'View All')]";

    @AndroidFindBy(xpath = VIEW_ALL)
    private WebElement viewAll;

    public static final String FORWARD_ARROW_ICON = "//android.view.View[contains(@content-desc,'Forward Arrow Icon')]";

    @AndroidFindBy(xpath = FORWARD_ARROW_ICON)
    private WebElement forwardArrowIcon;

    public static final String BRAINGYMJR_IMAGE = "//android.view.View[contains(@content-desc,'BrainGymJr image')]";

    @AndroidFindBy(xpath = BRAINGYMJR_IMAGE)
    private WebElement braingymjrImage;

    public static final String BRAINGYMJR = "//android.widget.TextView[contains(@content-desc,'BrainGymJr')]";

    @AndroidFindBy(xpath = BRAINGYMJR)
    private WebElement braingymjr;

    public static final String PLAY_SOLVE_LEARN_WIN = "//android.widget.TextView[contains(@content-desc,'Play. Solve. Learn. Win')]";

    @AndroidFindBy(xpath = PLAY_SOLVE_LEARN_WIN)
    private WebElement playSolveLearnWin;

    public static final String BUY_NOW_ICON = "//android.view.View[contains(@content-desc,'Buy Now icon')]";

    @AndroidFindBy(xpath = BUY_NOW_ICON)
    private WebElement buyNowIcon;

    public static final String BUY_NOW = "//android.widget.TextView[contains(@content-desc,'Buy Now')]";

    @AndroidFindBy(xpath = BUY_NOW)
    private WebElement buyNow;

    public static final String MOBILE_IMAGE = "//android.view.View[contains(@content-desc,'mobile image')]";

    @AndroidFindBy(xpath = MOBILE_IMAGE)
    private WebElement mobileImage;

    public static final String MOBILE = "//android.widget.TextView[contains(@content-desc,'mobile')]";

    @AndroidFindBy(xpath = MOBILE)
    private WebElement mobile;

    public static final String MOBILE_PHONE = "//android.widget.TextView[contains(@content-desc,'Mobile phone')]";

    @AndroidFindBy(xpath = MOBILE_PHONE)
    private WebElement mobilePhone;

    public static final String GROCERY_HIGHLIGHTS = "//android.widget.TextView[contains(@content-desc,'Grocery Highlights')]";

    @AndroidFindBy(xpath = GROCERY_HIGHLIGHTS)
    private WebElement groceryHighlights;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public static final String LOCATION_ON = "//android.view.View[contains(@content-desc,'Location On')]";

    @AndroidFindBy(xpath = LOCATION_ON)
    private WebElement locationOn;

    public static final String LOCATION = "//android.widget.TextView[contains(@content-desc,'Location')]";

    @AndroidFindBy(xpath = LOCATION)
    private WebElement location;

    public static final String ORDERS = "//android.widget.TextView[contains(@content-desc,'Orders')]";

    @AndroidFindBy(xpath = ORDERS)
    private WebElement orders;

    public HomeNonChannelsCreditLedgerHistory4Screen(TestContext context) {
        super(context);
    }
}
