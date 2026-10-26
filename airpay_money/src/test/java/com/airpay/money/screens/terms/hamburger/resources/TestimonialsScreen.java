package com.airpay.money.screens.terms.hamburger.resources;

import com.airpay.money.screens.menu.AbstractMenuScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class TestimonialsScreen extends AbstractMenuScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Testimonials\")")
    @iOSXCUITFindBy(accessibility = "Testimonials")
    private WebElement heading;

    public TestimonialsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Testimonials", "Testimonial", "reviews");
    }
}
