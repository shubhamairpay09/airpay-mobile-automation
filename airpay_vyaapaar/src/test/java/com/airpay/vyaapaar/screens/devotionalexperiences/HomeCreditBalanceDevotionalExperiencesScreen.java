package com.airpay.vyaapaar.screens.devotionalexperiences;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Devotional Experiences — Ayodhya Darshan Booking. Module devotional-experiences. */
public class HomeCreditBalanceDevotionalExperiencesScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-devotional-experiences";

    public static final String AYODHYA_DARSHAN_BOOKING = "//android.view.View[contains(@content-desc,'Ayodhya Darshan Booking')]";

    @AndroidFindBy(xpath = AYODHYA_DARSHAN_BOOKING)
    private WebElement ayodhyaDarshanBooking;

    public static final String DEVOTIONAL_EXPERIENCES_ICON = "//android.view.View[contains(@content-desc,'Devotional Experiences icon')]";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES_ICON)
    private WebElement devotionalExperiencesIcon;

    public static final String CLICK_ON_ASERVICE_PROVIDER_TO_PROCEED_WITH = "//android.widget.TextView[@text='Click on a service provider to proceed with booking']";

    @AndroidFindBy(xpath = CLICK_ON_ASERVICE_PROVIDER_TO_PROCEED_WITH)
    private WebElement clickOnAServiceProviderToProceedWith;

    public static final String DEVOTIONAL_EXPERIENCES = "//android.widget.TextView[@text='Devotional Experiences']";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES)
    private WebElement devotionalExperiences;

    public HomeCreditBalanceDevotionalExperiencesScreen(TestContext context) {
        super(context);
    }
}
