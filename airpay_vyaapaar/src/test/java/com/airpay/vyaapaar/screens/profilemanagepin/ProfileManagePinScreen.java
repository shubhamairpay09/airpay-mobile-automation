package com.airpay.vyaapaar.screens.profilemanagepin;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Profile / Manage PIN — Set MPIN. Module profile-manage-pin. */
public class ProfileManagePinScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-manage-pin";

    public static final String SET_MPIN = "//android.widget.TextView[@text='Set MPIN']";

    @AndroidFindBy(xpath = SET_MPIN)
    private WebElement setMpin;

    public static final String ENTER4DIGIT_MPIN = "//android.widget.TextView[@text='Enter 4 digit MPIN']";

    @AndroidFindBy(xpath = ENTER4DIGIT_MPIN)
    private WebElement enter4DigitMpin;

    public static final String VISIBILITY_ON_ICON = "//android.widget.ImageView[contains(@content-desc,'Visibility On Icon')]";

    @AndroidFindBy(xpath = VISIBILITY_ON_ICON)
    private WebElement visibilityOnIcon;

    public static final String CONFIRM4DIGIT_MPIN = "//android.widget.TextView[@text='Confirm 4 digit MPIN']";

    @AndroidFindBy(xpath = CONFIRM4DIGIT_MPIN)
    private WebElement confirm4DigitMpin;

    public static final String NOTE = "//android.widget.TextView[@text='Note:']";

    @AndroidFindBy(xpath = NOTE)
    private WebElement note;

    public static final String ELEMENT = "//android.widget.TextView[@text='•']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String DO_NOT_USE_REPEATED_OR_SEQUENTIAL_NUMBERS_E = "//android.widget.TextView[@text='Do not use repeated or sequential numbers e.g.1111 or 1234.']";

    @AndroidFindBy(xpath = DO_NOT_USE_REPEATED_OR_SEQUENTIAL_NUMBERS_E)
    private WebElement doNotUseRepeatedOrSequentialNumbersE;

    public static final String DO_NOT_USE_AN_MPIN_SIMILAR_TO_YOUR = "//android.widget.TextView[@text='Do not use an MPIN similar to your personal details like Mobile number, Date of Birth etc.']";

    @AndroidFindBy(xpath = DO_NOT_USE_AN_MPIN_SIMILAR_TO_YOUR)
    private WebElement doNotUseAnMpinSimilarToYour;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public ProfileManagePinScreen(TestContext context) {
        super(context);
    }
}
