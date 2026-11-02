package com.airpay.vyaapaar.screens.fromhere;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** UiContainer Catalog Overview Card/Show password (filled). Module from-here. */
public class FromHereUicontainerCatalogOverviewCard5Screen extends BaseScreen {

    public static final String PAGE_ID = "from-here-uicontainer-catalog-overview-card-show-password-filled";

    public static final String COM_GOOGLE_ANDROID_INPUTMETHOD_LATIN_ID_DECOR_CONTENT = "//android.view.ViewGroup[@resource-id='com.google.android.inputmethod.latin:id/decor_content_parent']";

    @AndroidFindBy(xpath = COM_GOOGLE_ANDROID_INPUTMETHOD_LATIN_ID_DECOR_CONTENT)
    private WebElement comGoogleAndroidInputmethodLatinIdDecorContent;

    public static final String NAVIGATE_UP = "//android.widget.ImageButton[contains(@content-desc,'Navigate up')]";

    @AndroidFindBy(xpath = NAVIGATE_UP)
    private WebElement navigateUp;

    public static final String THEME = "//android.widget.TextView[@text='Theme']";

    @AndroidFindBy(xpath = THEME)
    private WebElement theme;

    public static final String COM_GOOGLE_ANDROID_INPUTMETHOD_LATIN_ID_THEME_LISTING = "//android.widget.FrameLayout[@resource-id='com.google.android.inputmethod.latin:id/theme_listing_details_fragment_container']";

    @AndroidFindBy(xpath = COM_GOOGLE_ANDROID_INPUTMETHOD_LATIN_ID_THEME_LISTING)
    private WebElement comGoogleAndroidInputmethodLatinIdThemeListing;

    public static final String MY_THEMES = "//android.widget.TextView[@text='My themes']";

    @AndroidFindBy(xpath = MY_THEMES)
    private WebElement myThemes;

    public static final String CREATE_KEYBOARD_THEME_WITH_MY_IMAGE = "//android.widget.FrameLayout[contains(@content-desc,'Create keyboard theme with my image')]";

    @AndroidFindBy(xpath = CREATE_KEYBOARD_THEME_WITH_MY_IMAGE)
    private WebElement createKeyboardThemeWithMyImage;

    public static final String EL_DEFAULT = "//android.widget.TextView[@text='Default']";

    @AndroidFindBy(xpath = EL_DEFAULT)
    private WebElement elDefault;

    public static final String DEFAULT_AUTO_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Default auto theme')]";

    @AndroidFindBy(xpath = DEFAULT_AUTO_THEME)
    private WebElement defaultAutoTheme;

    public static final String SYSTEM_AUTO = "//android.widget.TextView[@text='System Auto']";

    @AndroidFindBy(xpath = SYSTEM_AUTO)
    private WebElement systemAuto;

    public static final String DEFAULT_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Default theme')]";

    @AndroidFindBy(xpath = DEFAULT_THEME)
    private WebElement defaultTheme;

    public static final String DEFAULT_DARK_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Default dark theme')]";

    @AndroidFindBy(xpath = DEFAULT_DARK_THEME)
    private WebElement defaultDarkTheme;

    public static final String DEFAULT_DARK = "//android.widget.TextView[@text='Default Dark']";

    @AndroidFindBy(xpath = DEFAULT_DARK)
    private WebElement defaultDark;

    public static final String COLORS = "//android.widget.TextView[@text='Colors']";

    @AndroidFindBy(xpath = COLORS)
    private WebElement colors;

    public static final String MATERIAL_LIGHT_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Material Light Theme')]";

    @AndroidFindBy(xpath = MATERIAL_LIGHT_THEME)
    private WebElement materialLightTheme;

    public static final String MATERIAL_DARK_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Material Dark Theme')]";

    @AndroidFindBy(xpath = MATERIAL_DARK_THEME)
    private WebElement materialDarkTheme;

    public static final String GOOGLE_LIGHT_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Google Light Theme')]";

    @AndroidFindBy(xpath = GOOGLE_LIGHT_THEME)
    private WebElement googleLightTheme;

    public static final String GOOGLE_DARK_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Google Dark Theme')]";

    @AndroidFindBy(xpath = GOOGLE_DARK_THEME)
    private WebElement googleDarkTheme;

    public static final String RED_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Red Theme')]";

    @AndroidFindBy(xpath = RED_THEME)
    private WebElement redTheme;

    public static final String GREEN_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Green Theme')]";

    @AndroidFindBy(xpath = GREEN_THEME)
    private WebElement greenTheme;

    public static final String TEAL_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Teal Theme')]";

    @AndroidFindBy(xpath = TEAL_THEME)
    private WebElement tealTheme;

    public static final String BLUE_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Blue Theme')]";

    @AndroidFindBy(xpath = BLUE_THEME)
    private WebElement blueTheme;

    public static final String CYAN_THEME = "//android.widget.FrameLayout[contains(@content-desc,'Cyan Theme')]";

    @AndroidFindBy(xpath = CYAN_THEME)
    private WebElement cyanTheme;

    public static final String SHOW_MORE_COLORS = "//android.widget.TextView[contains(@content-desc,'Show more Colors')]";

    @AndroidFindBy(xpath = SHOW_MORE_COLORS)
    private WebElement showMoreColors;

    public FromHereUicontainerCatalogOverviewCard5Screen(TestContext context) {
        super(context);
    }
}
