package com.airpay.vyaapaar.screens.devotionalexperiences;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Devotional Experiences/Ayodhya Darshan Booking. Module devotional-experiences. */
public class HomeNonChannelsDevotionalExperiencesAyodhyaScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-devotional-experiences-ayodhya-darshan-booking";

    public static final String FULL_NAME = "//android.view.View[contains(@content-desc,'Full Name')]";

    @AndroidFindBy(xpath = FULL_NAME)
    private WebElement fullName;

    public static final String MOBILE_NUMBER = "//android.view.View[contains(@content-desc,'Mobile Number')]";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String SAVE_REDIRECT = "//android.widget.TextView[contains(@content-desc,'Save & Redirect')]";

    @AndroidFindBy(xpath = SAVE_REDIRECT)
    private WebElement saveRedirect;

    public static final String AYODHYA_DARSHAN_BOOKING_ICON = "//android.view.View[contains(@content-desc,'Ayodhya Darshan Booking icon')]";

    @AndroidFindBy(xpath = AYODHYA_DARSHAN_BOOKING_ICON)
    private WebElement ayodhyaDarshanBookingIcon;

    public static final String FILL_IN_YOUR_DETAILS_TO_PROCEED_YOU_WILL = "//android.widget.TextView[contains(@content-desc,'Fill in your details to proceed. You will be redirected to the partner website after saving.')]";

    @AndroidFindBy(xpath = FILL_IN_YOUR_DETAILS_TO_PROCEED_YOU_WILL)
    private WebElement fillInYourDetailsToProceedYouWill;

    public static final String AYODHYA_DARSHAN_BOOKING = "//android.widget.TextView[@text='Ayodhya Darshan Booking']";

    @AndroidFindBy(xpath = AYODHYA_DARSHAN_BOOKING)
    private WebElement ayodhyaDarshanBooking;

    public HomeNonChannelsDevotionalExperiencesAyodhyaScreen(TestContext context) {
        super(context);
    }
}
