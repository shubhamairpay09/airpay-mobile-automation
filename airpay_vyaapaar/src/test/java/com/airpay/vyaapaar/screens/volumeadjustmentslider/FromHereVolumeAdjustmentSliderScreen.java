package com.airpay.vyaapaar.screens.volumeadjustmentslider;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Volume adjustment slider — Volume adjustment slider. Module volume-adjustment-slider. */
public class FromHereVolumeAdjustmentSliderScreen extends BaseScreen {

    public static final String PAGE_ID = "from-here-volume-adjustment-slider";

    public static final String EL400 = "//android.widget.SeekBar[@text='40.0']";

    @AndroidFindBy(xpath = EL400)
    private WebElement el400;

    public static final String VOLUME_ADJUSTMENT_SLIDER = "//android.view.View[contains(@content-desc,'Volume adjustment slider')]";

    @AndroidFindBy(xpath = VOLUME_ADJUSTMENT_SLIDER)
    private WebElement volumeAdjustmentSlider;

    public static final String RANGE_SLIDER_HEADING_CURRENT_RANGE_FROM50TO = "//android.widget.TextView[contains(@content-desc,'Range Slider heading, current range from 50 to 100')]";

    @AndroidFindBy(xpath = RANGE_SLIDER_HEADING_CURRENT_RANGE_FROM50TO)
    private WebElement rangeSliderHeadingCurrentRangeFrom50To;

    public static final String PRICE_RANGE_SELECTION_SLIDER = "//android.view.View[contains(@content-desc,'Price range selection slider')]";

    @AndroidFindBy(xpath = PRICE_RANGE_SELECTION_SLIDER)
    private WebElement priceRangeSelectionSlider;

    public static final String EL500 = "//android.widget.SeekBar[@text='50.0']";

    @AndroidFindBy(xpath = EL500)
    private WebElement el500;

    public static final String RANGE_START = "//android.view.View[contains(@content-desc,'Range start')]";

    @AndroidFindBy(xpath = RANGE_START)
    private WebElement rangeStart;

    public static final String EL1000 = "//android.widget.SeekBar[@text='100.0']";

    @AndroidFindBy(xpath = EL1000)
    private WebElement el1000;

    public static final String RANGE_END = "//android.view.View[contains(@content-desc,'Range end')]";

    @AndroidFindBy(xpath = RANGE_END)
    private WebElement rangeEnd;

    public static final String PROGRESS_INDICATORS_SECTION = "//android.widget.TextView[contains(@content-desc,'Progress Indicators section')]";

    @AndroidFindBy(xpath = PROGRESS_INDICATORS_SECTION)
    private WebElement progressIndicatorsSection;

    public static final String LINEAR_PROGRESS_INDICATOR = "//android.widget.ProgressBar[contains(@content-desc,'Linear progress indicator')]";

    @AndroidFindBy(xpath = LINEAR_PROGRESS_INDICATOR)
    private WebElement linearProgressIndicator;

    public static final String DETERMINATE_CIRCULAR_PROGRESS_INDICATOR = "//android.widget.ProgressBar[contains(@content-desc,'Determinate circular progress indicator')]";

    @AndroidFindBy(xpath = DETERMINATE_CIRCULAR_PROGRESS_INDICATOR)
    private WebElement determinateCircularProgressIndicator;

    public static final String INDETERMINATE_LOADING_PROGRESS_INDICATOR = "//android.widget.ProgressBar[contains(@content-desc,'Indeterminate loading progress indicator')]";

    @AndroidFindBy(xpath = INDETERMINATE_LOADING_PROGRESS_INDICATOR)
    private WebElement indeterminateLoadingProgressIndicator;

    public static final String EL5CHIPS_BADGES = "//android.widget.TextView[@text='5. Chips & Badges']";

    @AndroidFindBy(xpath = EL5CHIPS_BADGES)
    private WebElement el5ChipsBadges;

    public static final String FILTER_CHIP = "//android.view.View[contains(@content-desc,'Filter Chip')]";

    @AndroidFindBy(xpath = FILTER_CHIP)
    private WebElement filterChip;

    public static final String ASSIST_CHIP_INFO = "//android.view.View[contains(@content-desc,'Assist Chip, Info')]";

    @AndroidFindBy(xpath = ASSIST_CHIP_INFO)
    private WebElement assistChipInfo;

    public static final String ASSIST_CHIP = "//android.widget.TextView[@text='Assist Chip']";

    @AndroidFindBy(xpath = ASSIST_CHIP)
    private WebElement assistChip;

    public static final String SUGGESTION_CHIP = "//android.view.View[contains(@content-desc,'Suggestion Chip')]";

    @AndroidFindBy(xpath = SUGGESTION_CHIP)
    private WebElement suggestionChip;

    public static final String DISMISSIBLE_CHIP = "//android.view.View[contains(@content-desc,'Dismissible Chip')]";

    @AndroidFindBy(xpath = DISMISSIBLE_CHIP)
    private WebElement dismissibleChip;

    public static final String REMOVE_DISMISSIBLE_CHIP = "//android.widget.Button[contains(@content-desc,'Remove Dismissible Chip')]";

    @AndroidFindBy(xpath = REMOVE_DISMISSIBLE_CHIP)
    private WebElement removeDismissibleChip;

    public static final String NOTIFICATIONS3UNREAD_MESSAGES = "//android.view.View[contains(@content-desc,'Notifications, 3 unread messages')]";

    @AndroidFindBy(xpath = NOTIFICATIONS3UNREAD_MESSAGES)
    private WebElement notifications3UnreadMessages;

    public static final String EL3UNREAD_NOTIFICATIONS = "//android.widget.TextView[contains(@content-desc,'3 unread notifications')]";

    @AndroidFindBy(xpath = EL3UNREAD_NOTIFICATIONS)
    private WebElement el3UnreadNotifications;

    public static final String SETTINGS_NEW_UPDATES_AVAILABLE = "//android.view.View[contains(@content-desc,'Settings, new updates available')]";

    @AndroidFindBy(xpath = SETTINGS_NEW_UPDATES_AVAILABLE)
    private WebElement settingsNewUpdatesAvailable;

    public static final String EL6CARDS_SURFACES = "//android.widget.TextView[@text='6. Cards & Surfaces']";

    @AndroidFindBy(xpath = EL6CARDS_SURFACES)
    private WebElement el6CardsSurfaces;

    public static final String ELEVATED_CARD_ELEVATED_CARD_TITLE_THIS_CARD_IS = "//android.view.View[contains(@content-desc,'Elevated Card: Elevated Card Title. This card is accessible and clickable.')]";

    @AndroidFindBy(xpath = ELEVATED_CARD_ELEVATED_CARD_TITLE_THIS_CARD_IS)
    private WebElement elevatedCardElevatedCardTitleThisCardIs;

    public static final String ELEVATED_CARD_TITLE = "//android.widget.TextView[@text='Elevated Card Title']";

    @AndroidFindBy(xpath = ELEVATED_CARD_TITLE)
    private WebElement elevatedCardTitle;

    public static final String THIS_CARD_IS_ACCESSIBLE_AND_CLICKABLE = "//android.widget.TextView[@text='This card is accessible and clickable.']";

    @AndroidFindBy(xpath = THIS_CARD_IS_ACCESSIBLE_AND_CLICKABLE)
    private WebElement thisCardIsAccessibleAndClickable;

    public static final String OUTLINED_CARD_CONTAINER_CARD_WITH_OUTLINED_BORDER = "//android.view.View[contains(@content-desc,'Outlined Card: Container card with outlined border.')]";

    @AndroidFindBy(xpath = OUTLINED_CARD_CONTAINER_CARD_WITH_OUTLINED_BORDER)
    private WebElement outlinedCardContainerCardWithOutlinedBorder;

    public static final String OUTLINED_CARD = "//android.widget.TextView[@text='Outlined Card']";

    @AndroidFindBy(xpath = OUTLINED_CARD)
    private WebElement outlinedCard;

    public static final String CONTAINER_CARD_WITH_OUTLINED_BORDER = "//android.widget.TextView[@text='Container card with outlined border.']";

    @AndroidFindBy(xpath = CONTAINER_CARD_WITH_OUTLINED_BORDER)
    private WebElement containerCardWithOutlinedBorder;

    public static final String FILLED_ELEVATED_CARD_CONTAINS_CONTENT_INSIDE_AN_ELEVATED = "//android.view.View[contains(@content-desc,'Filled Elevated Card: Contains content inside an elevated container.')]";

    @AndroidFindBy(xpath = FILLED_ELEVATED_CARD_CONTAINS_CONTENT_INSIDE_AN_ELEVATED)
    private WebElement filledElevatedCardContainsContentInsideAnElevated;

    public static final String MATERIAL3ACCESSIBILITY_CATALOG_SCREEN_HEADER = "//android.widget.TextView[contains(@content-desc,'Material 3 Accessibility Catalog Screen Header')]";

    @AndroidFindBy(xpath = MATERIAL3ACCESSIBILITY_CATALOG_SCREEN_HEADER)
    private WebElement material3AccessibilityCatalogScreenHeader;

    public static final String NAVIGATE_TO_WITHOUT_UICONTAINER_CATALOG = "//android.widget.Button[contains(@content-desc,'Navigate to Without UiContainer Catalog')]";

    @AndroidFindBy(xpath = NAVIGATE_TO_WITHOUT_UICONTAINER_CATALOG)
    private WebElement navigateToWithoutUicontainerCatalog;

    public FromHereVolumeAdjustmentSliderScreen(TestContext context) {
        super(context);
    }
}
