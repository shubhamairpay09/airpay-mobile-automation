package com.airpay.vyaapaar.screens.forgotpassword;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Forgot Password — Screen. Module forgot-password. */
public class ForgotPasswordScreenScreen extends BaseScreen {

    public static final String PAGE_ID = "forgot-password-screen";

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public ForgotPasswordScreenScreen(TestContext context) {
        super(context);
    }
}
