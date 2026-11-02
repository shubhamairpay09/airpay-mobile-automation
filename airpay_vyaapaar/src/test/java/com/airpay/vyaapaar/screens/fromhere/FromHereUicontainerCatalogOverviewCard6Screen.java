package com.airpay.vyaapaar.screens.fromhere;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** UiContainer Catalog Overview Card/Show password/Create keyboard theme with my image/Navigate to Without UiContainer Catalog/Show password/Filter Chip. Module from-here. */
public class FromHereUicontainerCatalogOverviewCard6Screen extends BaseScreen {

    public static final String PAGE_ID = "from-here-uicontainer-catalog-overview-card-show-password-create-keyboard-theme-with-my-im";

    public static final String ELEVATED_BUTTON = "//android.view.View[contains(@content-desc,'Elevated Button')]";

    @AndroidFindBy(xpath = ELEVATED_BUTTON)
    private WebElement elevatedButton;

    public static final String FILLED_TONAL_BUTTON = "//android.view.View[contains(@content-desc,'Filled Tonal Button')]";

    @AndroidFindBy(xpath = FILLED_TONAL_BUTTON)
    private WebElement filledTonalButton;

    public static final String TONAL_BUTTON = "//android.widget.TextView[@text='Tonal Button']";

    @AndroidFindBy(xpath = TONAL_BUTTON)
    private WebElement tonalButton;

    public static final String TEXT_BUTTON = "//android.view.View[contains(@content-desc,'Text Button')]";

    @AndroidFindBy(xpath = TEXT_BUTTON)
    private WebElement textButton;

    public static final String DISABLED_BUTTON = "//android.view.View[contains(@content-desc,'Disabled Button')]";

    @AndroidFindBy(xpath = DISABLED_BUTTON)
    private WebElement disabledButton;

    public static final String ADD_NEW_ITEM_FLOATING_ACTION_BUTTON = "//android.widget.Button[contains(@content-desc,'Add new item Floating Action Button')]";

    @AndroidFindBy(xpath = ADD_NEW_ITEM_FLOATING_ACTION_BUTTON)
    private WebElement addNewItemFloatingActionButton;

    public static final String ADD_TO_FAVORITES_ICON_BUTTON = "//android.widget.Button[contains(@content-desc,'Add to favorites Icon Button')]";

    @AndroidFindBy(xpath = ADD_TO_FAVORITES_ICON_BUTTON)
    private WebElement addToFavoritesIconButton;

    public static final String EL2TEXT_FIELDS_INPUT_CONTROLS = "//android.widget.TextView[@text='2. Text Fields & Input Controls']";

    @AndroidFindBy(xpath = EL2TEXT_FIELDS_INPUT_CONTROLS)
    private WebElement el2TextFieldsInputControls;

    public static final String EL1 = "//android.widget.EditText[@text='1']";

    @AndroidFindBy(xpath = EL1)
    private WebElement el1;

    public static final String FULL_NAME_TEXT_FIELD = "//android.view.View[contains(@content-desc,'Full Name text field')]";

    @AndroidFindBy(xpath = FULL_NAME_TEXT_FIELD)
    private WebElement fullNameTextField;

    public static final String FULL_NAME = "//android.widget.TextView[@text='Full Name']";

    @AndroidFindBy(xpath = FULL_NAME)
    private WebElement fullName;

    public static final String USERNAME_TEXT_FIELD = "//android.view.View[contains(@content-desc,'Username text field')]";

    @AndroidFindBy(xpath = USERNAME_TEXT_FIELD)
    private WebElement usernameTextField;

    public static final String USERNAME_ERROR_STATE = "//android.widget.TextView[@text='Username (Error State)']";

    @AndroidFindBy(xpath = USERNAME_ERROR_STATE)
    private WebElement usernameErrorState;

    public static final String ERROR_MESSAGE_NAME_MUST_BE_AT_LEAST5 = "//android.widget.TextView[contains(@content-desc,'Error message: Name must be at least 5 characters')]";

    @AndroidFindBy(xpath = ERROR_MESSAGE_NAME_MUST_BE_AT_LEAST5)
    private WebElement errorMessageNameMustBeAtLeast5;

    public static final String PASSWORD_TEXT_FIELD = "//android.view.View[contains(@content-desc,'Password text field')]";

    @AndroidFindBy(xpath = PASSWORD_TEXT_FIELD)
    private WebElement passwordTextField;

    public static final String PASSWORD = "//android.widget.TextView[@text='Password']";

    @AndroidFindBy(xpath = PASSWORD)
    private WebElement password;

    public static final String HIDE_PASSWORD = "//android.widget.Button[contains(@content-desc,'Hide password')]";

    @AndroidFindBy(xpath = HIDE_PASSWORD)
    private WebElement hidePassword;

    public static final String FILLED_TEXT_FIELD = "//android.view.View[contains(@content-desc,'Filled text field')]";

    @AndroidFindBy(xpath = FILLED_TEXT_FIELD)
    private WebElement filledTextField;

    public static final String EL3CHECKBOXES_RADIO_BUTTONS_SWITCHES = "//android.widget.TextView[@text='3. Checkboxes, Radio Buttons & Switches']";

    @AndroidFindBy(xpath = EL3CHECKBOXES_RADIO_BUTTONS_SWITCHES)
    private WebElement el3CheckboxesRadioButtonsSwitches;

    public static final String I_AGREE_TO_TERMS_CONDITIONS_CHECKBOX = "//android.view.View[contains(@content-desc,'I agree to Terms & Conditions checkbox')]";

    @AndroidFindBy(xpath = I_AGREE_TO_TERMS_CONDITIONS_CHECKBOX)
    private WebElement iAgreeToTermsConditionsCheckbox;

    public static final String I_AGREE_TO_TERMS_CONDITIONS = "//android.widget.TextView[@text='I agree to Terms & Conditions']";

    @AndroidFindBy(xpath = I_AGREE_TO_TERMS_CONDITIONS)
    private WebElement iAgreeToTermsConditions;

    public static final String MATERIAL3ACCESSIBILITY_CATALOG_SCREEN_HEADER = "//android.widget.TextView[contains(@content-desc,'Material 3 Accessibility Catalog Screen Header')]";

    @AndroidFindBy(xpath = MATERIAL3ACCESSIBILITY_CATALOG_SCREEN_HEADER)
    private WebElement material3AccessibilityCatalogScreenHeader;

    public static final String NAVIGATE_TO_WITHOUT_UICONTAINER_CATALOG = "//android.widget.Button[contains(@content-desc,'Navigate to Without UiContainer Catalog')]";

    @AndroidFindBy(xpath = NAVIGATE_TO_WITHOUT_UICONTAINER_CATALOG)
    private WebElement navigateToWithoutUicontainerCatalog;

    public static final String UICONTAINER_CATALOG_OVERVIEW_CARD = "//android.view.View[contains(@content-desc,'UiContainer Catalog Overview Card')]";

    @AndroidFindBy(xpath = UICONTAINER_CATALOG_OVERVIEW_CARD)
    private WebElement uicontainerCatalogOverviewCard;

    public static final String UICONTAINER_CATALOG_OVERVIEW_SECTION_HEADING = "//android.widget.TextView[contains(@content-desc,'UiContainer Catalog Overview Section Heading')]";

    @AndroidFindBy(xpath = UICONTAINER_CATALOG_OVERVIEW_SECTION_HEADING)
    private WebElement uicontainerCatalogOverviewSectionHeading;

    public static final String DESCRIPTION_THIS_SCREEN_RUNS_INSIDE_UICONTAINER_TAP_BELOW = "//android.widget.TextView[contains(@content-desc,'Description: This screen runs inside UiContainer. Tap below to navigate to the screen built without UiContainer.')]";

    @AndroidFindBy(xpath = DESCRIPTION_THIS_SCREEN_RUNS_INSIDE_UICONTAINER_TAP_BELOW)
    private WebElement descriptionThisScreenRunsInsideUicontainerTapBelow;

    public static final String GO_TO_WITHOUT_UICONTAINER_SCREEN_BUTTON = "//android.view.View[contains(@content-desc,'Go to Without UiContainer Screen Button')]";

    @AndroidFindBy(xpath = GO_TO_WITHOUT_UICONTAINER_SCREEN_BUTTON)
    private WebElement goToWithoutUicontainerScreenButton;

    public static final String GO_TO_WITHOUT_UICONTAINER_SCREEN = "//android.widget.TextView[@text='Go to Without UiContainer Screen']";

    @AndroidFindBy(xpath = GO_TO_WITHOUT_UICONTAINER_SCREEN)
    private WebElement goToWithoutUicontainerScreen;

    public static final String EL1MATERIAL3BUTTONS = "//android.widget.TextView[@text='1. Material 3 Buttons']";

    @AndroidFindBy(xpath = EL1MATERIAL3BUTTONS)
    private WebElement el1Material3Buttons;

    public static final String FILLED_BUTTON_TAP_TO_TOGGLE_LOADING_STATE = "//android.view.View[contains(@content-desc,'Filled Button, tap to toggle loading state')]";

    @AndroidFindBy(xpath = FILLED_BUTTON_TAP_TO_TOGGLE_LOADING_STATE)
    private WebElement filledButtonTapToToggleLoadingState;

    public static final String FILLED_BUTTON = "//android.widget.TextView[@text='Filled Button']";

    @AndroidFindBy(xpath = FILLED_BUTTON)
    private WebElement filledButton;

    public static final String OUTLINED_BUTTON = "//android.view.View[contains(@content-desc,'Outlined Button')]";

    @AndroidFindBy(xpath = OUTLINED_BUTTON)
    private WebElement outlinedButton;

    public static final String ABC = "//android.widget.EditText[@text='abc']";

    @AndroidFindBy(xpath = ABC)
    private WebElement abc;

    public static final String NAVIGATE_BACK = "//android.view.View[contains(@content-desc,'Navigate back')]";

    @AndroidFindBy(xpath = NAVIGATE_BACK)
    private WebElement navigateBack;

    public static final String NATIVE_MATERIAL3CATALOG_NO_UICONTAINER = "//android.widget.TextView[@text='Native Material 3 Catalog (No UiContainer)']";

    @AndroidFindBy(xpath = NATIVE_MATERIAL3CATALOG_NO_UICONTAINER)
    private WebElement nativeMaterial3CatalogNoUicontainer;

    public static final String SHUBHAM_VERMA = "//android.widget.EditText[@text='Shubham Verma']";

    @AndroidFindBy(xpath = SHUBHAM_VERMA)
    private WebElement shubhamVerma;

    public static final String TRI_STATE_SELECTION_CHECKBOX = "//android.view.View[contains(@content-desc,'Tri-State Selection checkbox')]";

    @AndroidFindBy(xpath = TRI_STATE_SELECTION_CHECKBOX)
    private WebElement triStateSelectionCheckbox;

    public static final String TRI_STATE_SELECTION_INDETERMINATE = "//android.widget.TextView[@text='Tri-State Selection (Indeterminate)']";

    @AndroidFindBy(xpath = TRI_STATE_SELECTION_INDETERMINATE)
    private WebElement triStateSelectionIndeterminate;

    public static final String SELECT_PAYMENT_METHOD_RADIO_GROUP_HEADING = "//android.widget.TextView[contains(@content-desc,'Select Payment Method radio group heading')]";

    @AndroidFindBy(xpath = SELECT_PAYMENT_METHOD_RADIO_GROUP_HEADING)
    private WebElement selectPaymentMethodRadioGroupHeading;

    public static final String OPTION1RADIO_BUTTON = "//android.view.View[contains(@content-desc,'Option 1 radio button')]";

    @AndroidFindBy(xpath = OPTION1RADIO_BUTTON)
    private WebElement option1RadioButton;

    public static final String OPTION1 = "//android.widget.TextView[@text='Option 1']";

    @AndroidFindBy(xpath = OPTION1)
    private WebElement option1;

    public static final String OPTION2RADIO_BUTTON = "//android.view.View[contains(@content-desc,'Option 2 radio button')]";

    @AndroidFindBy(xpath = OPTION2RADIO_BUTTON)
    private WebElement option2RadioButton;

    public static final String OPTION2 = "//android.widget.TextView[@text='Option 2']";

    @AndroidFindBy(xpath = OPTION2)
    private WebElement option2;

    public static final String OPTION3RADIO_BUTTON = "//android.view.View[contains(@content-desc,'Option 3 radio button')]";

    @AndroidFindBy(xpath = OPTION3RADIO_BUTTON)
    private WebElement option3RadioButton;

    public static final String OPTION3 = "//android.widget.TextView[@text='Option 3']";

    @AndroidFindBy(xpath = OPTION3)
    private WebElement option3;

    public static final String ENABLE_PUSH_NOTIFICATIONS_SWITCH = "//android.view.View[contains(@content-desc,'Enable Push Notifications switch')]";

    @AndroidFindBy(xpath = ENABLE_PUSH_NOTIFICATIONS_SWITCH)
    private WebElement enablePushNotificationsSwitch;

    public static final String ENABLE_PUSH_NOTIFICATIONS = "//android.widget.TextView[@text='Enable Push Notifications']";

    @AndroidFindBy(xpath = ENABLE_PUSH_NOTIFICATIONS)
    private WebElement enablePushNotifications;

    public static final String EL4SLIDERS_PROGRESS_INDICATORS = "//android.widget.TextView[@text='4. Sliders & Progress Indicators']";

    @AndroidFindBy(xpath = EL4SLIDERS_PROGRESS_INDICATORS)
    private WebElement el4SlidersProgressIndicators;

    public static final String CONTINUOUS_SLIDER_HEADING_CURRENT_VALUE40PERCENT = "//android.widget.TextView[contains(@content-desc,'Continuous Slider heading, current value 40 percent')]";

    @AndroidFindBy(xpath = CONTINUOUS_SLIDER_HEADING_CURRENT_VALUE40PERCENT)
    private WebElement continuousSliderHeadingCurrentValue40Percent;

    public static final String EL400 = "//android.widget.SeekBar[@text='40.0']";

    @AndroidFindBy(xpath = EL400)
    private WebElement el400;

    public static final String VOLUME_ADJUSTMENT_SLIDER = "//android.view.View[contains(@content-desc,'Volume adjustment slider')]";

    @AndroidFindBy(xpath = VOLUME_ADJUSTMENT_SLIDER)
    private WebElement volumeAdjustmentSlider;

    public static final String RANGE_SLIDER_HEADING_CURRENT_RANGE_FROM20TO = "//android.widget.TextView[contains(@content-desc,'Range Slider heading, current range from 20 to 80')]";

    @AndroidFindBy(xpath = RANGE_SLIDER_HEADING_CURRENT_RANGE_FROM20TO)
    private WebElement rangeSliderHeadingCurrentRangeFrom20To;

    public static final String PRICE_RANGE_SELECTION_SLIDER = "//android.view.View[contains(@content-desc,'Price range selection slider')]";

    @AndroidFindBy(xpath = PRICE_RANGE_SELECTION_SLIDER)
    private WebElement priceRangeSelectionSlider;

    public static final String EL200 = "//android.widget.SeekBar[@text='20.0']";

    @AndroidFindBy(xpath = EL200)
    private WebElement el200;

    public static final String RANGE_START = "//android.view.View[contains(@content-desc,'Range start')]";

    @AndroidFindBy(xpath = RANGE_START)
    private WebElement rangeStart;

    public static final String EL800 = "//android.widget.SeekBar[@text='80.0']";

    @AndroidFindBy(xpath = EL800)
    private WebElement el800;

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

    public FromHereUicontainerCatalogOverviewCard6Screen(TestContext context) {
        super(context);
    }
}
