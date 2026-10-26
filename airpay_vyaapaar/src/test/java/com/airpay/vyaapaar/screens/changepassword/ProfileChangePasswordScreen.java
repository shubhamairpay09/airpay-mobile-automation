package com.airpay.vyaapaar.screens.changepassword;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Change Password — Current Password. Module change-password. */
public class ProfileChangePasswordScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-change-password";

    public static final String CURRENT_PASSWORD = "//android.widget.TextView[@text='Current Password']";

    @AndroidFindBy(xpath = CURRENT_PASSWORD)
    private WebElement currentPassword;

    public static final String NEW_PASSWORD = "//android.widget.TextView[@text='New Password']";

    @AndroidFindBy(xpath = NEW_PASSWORD)
    private WebElement newPassword;

    public static final String CONFIRM_PASSWORD = "//android.widget.TextView[@text='Confirm Password']";

    @AndroidFindBy(xpath = CONFIRM_PASSWORD)
    private WebElement confirmPassword;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String PASSWORD_RULES = "//android.widget.TextView[@text='Password Rules:']";

    @AndroidFindBy(xpath = PASSWORD_RULES)
    private WebElement passwordRules;

    public static final String ELEMENT = "//android.widget.TextView[@text='•']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String NEW_PASSWORD_SHOULD_NOT_BE_THE_SAME_AS = "//android.widget.TextView[@text='New password should not be the same as your last 5 passwords.']";

    @AndroidFindBy(xpath = NEW_PASSWORD_SHOULD_NOT_BE_THE_SAME_AS)
    private WebElement newPasswordShouldNotBeTheSameAs;

    public static final String PASSWORD_MUST_NOT_EXCEED21CHARACTERS = "//android.widget.TextView[@text='Password must not exceed 21 characters.']";

    @AndroidFindBy(xpath = PASSWORD_MUST_NOT_EXCEED21CHARACTERS)
    private WebElement passwordMustNotExceed21Characters;

    public static final String PASSWORD_SHOULD_NOT_CONTAIN_ANY_SPACE_OR_ANY = "//android.widget.TextView[@text='Password should not contain any space or any special characters (eg:-!@#$%^&*(_+=./|)).']";

    @AndroidFindBy(xpath = PASSWORD_SHOULD_NOT_CONTAIN_ANY_SPACE_OR_ANY)
    private WebElement passwordShouldNotContainAnySpaceOrAny;

    public static final String PASSWORD_MUST_CONTAIN_AT_LEAST7CHARACTERS_WITH = "//android.widget.TextView[@text='Password must contain at least 7 characters, with minimum if 1 uppercase character,1 lowercase and 1 number.']";

    @AndroidFindBy(xpath = PASSWORD_MUST_CONTAIN_AT_LEAST7CHARACTERS_WITH)
    private WebElement passwordMustContainAtLeast7CharactersWith;

    public static final String CHANGE_PASSWORD = "//android.widget.TextView[@text='Change Password']";

    @AndroidFindBy(xpath = CHANGE_PASSWORD)
    private WebElement changePassword;

    public ProfileChangePasswordScreen(TestContext context) {
        super(context);
    }
}
