package com.airpay.vyaapaar.screens.fromhere;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** UiContainer Catalog Overview Card/Show password. Module from-here. */
public class FromHereUicontainerCatalogOverviewCard4Screen extends BaseScreen {

    public static final String PAGE_ID = "from-here-uicontainer-catalog-overview-card-show-password";

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

    public static final String SHUBHAM_VERMA = "//android.widget.EditText[@text='Shubham Verma']";

    @AndroidFindBy(xpath = SHUBHAM_VERMA)
    private WebElement shubhamVerma;

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

    public FromHereUicontainerCatalogOverviewCard4Screen(TestContext context) {
        super(context);
    }
}
