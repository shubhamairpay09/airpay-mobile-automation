package com.airpay.vyaapaar.screens.volumeadjustmentslidernavigatetowithoutuicontainercatalog;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Volume adjustment slider / Navigate to Without UiContainer Catalog — UiContainer Catalog Overview Card. Module volume-adjustment-slider-navigate-to-without-uicontainer-catalog. */
public class FromHereVolumeAdjustmentSliderNavigateScreen extends BaseScreen {

    public static final String PAGE_ID = "from-here-volume-adjustment-slider-navigate-to-without-uicontainer-catalog";

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

    public static final String FULL_NAME_TEXT_FIELD = "//android.view.View[contains(@content-desc,'Full Name text field')]";

    @AndroidFindBy(xpath = FULL_NAME_TEXT_FIELD)
    private WebElement fullNameTextField;

    public static final String FULL_NAME = "//android.widget.TextView[@text='Full Name']";

    @AndroidFindBy(xpath = FULL_NAME)
    private WebElement fullName;

    public static final String ABC = "//android.widget.EditText[@text='abc']";

    @AndroidFindBy(xpath = ABC)
    private WebElement abc;

    public static final String USERNAME_TEXT_FIELD = "//android.view.View[contains(@content-desc,'Username text field')]";

    @AndroidFindBy(xpath = USERNAME_TEXT_FIELD)
    private WebElement usernameTextField;

    public static final String USERNAME_ERROR_STATE = "//android.widget.TextView[@text='Username (Error State)']";

    @AndroidFindBy(xpath = USERNAME_ERROR_STATE)
    private WebElement usernameErrorState;

    public static final String ERROR_MESSAGE_NAME_MUST_BE_AT_LEAST5 = "//android.widget.TextView[contains(@content-desc,'Error message: Name must be at least 5 characters')]";

    @AndroidFindBy(xpath = ERROR_MESSAGE_NAME_MUST_BE_AT_LEAST5)
    private WebElement errorMessageNameMustBeAtLeast5;

    public static final String NAVIGATE_BACK = "//android.view.View[contains(@content-desc,'Navigate back')]";

    @AndroidFindBy(xpath = NAVIGATE_BACK)
    private WebElement navigateBack;

    public static final String NATIVE_MATERIAL3CATALOG_NO_UICONTAINER = "//android.widget.TextView[@text='Native Material 3 Catalog (No UiContainer)']";

    @AndroidFindBy(xpath = NATIVE_MATERIAL3CATALOG_NO_UICONTAINER)
    private WebElement nativeMaterial3CatalogNoUicontainer;

    public FromHereVolumeAdjustmentSliderNavigateScreen(TestContext context) {
        super(context);
    }
}
