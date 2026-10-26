package com.airpay.money.screens.profile;

/**
 * Live dump 2026-09-21 — in-app website hamburger (WebView) opened from Profile
 * settings FAQs / About / T&C / Privacy. Tap Menu then each module; do not
 * open footer URLs or the web Login button.
 *
 * WebView dumps mix on-screen nodes with off-screen DOM (login overlay, closed
 * drawer). Prefer content-desc / text locators over bounds.
 */
public class ProfileWebMenuScreen extends com.airpay.money.screens.base.BaseScreen {

    public ProfileWebMenuScreen(com.airpay.mobile.utilities.context.TestContext context) {
        super(context);
    }

    public static final String WEBVIEW_BACK = "//android.widget.Button[@bounds='[56,123][169,219]']";
    public static final String LOGO = "//android.view.View[@content-desc='airpay money']";
    public static final String LOGIN = "//android.widget.Button[@content-desc='Login']";
    public static final String MENU = "//android.widget.Button[@text='Menu']";
    public static final String MENU_CLOSE = "//android.widget.Button[@text='Close']";
    public static final String QR_FAB = "//android.widget.Button[@text='Download airpay money App QR Code']";

    public static final String START_FHC_CTA = "//android.view.View[@content-desc='Start Financial Health Check']";
    public static final String TOGGLE_FHC = "//android.widget.TextView[@text='Financial Health Check']";
    public static final String TOGGLE_LEARN = "//android.widget.TextView[@text='Learn']";
    public static final String TOGGLE_RESOURCES = "//android.widget.TextView[@text='Resources']";
    public static final String WHAT_IS_FHC = "//android.view.View[@content-desc='What is FHC']";
    public static final String HOW_IT_WORKS = "//android.view.View[@content-desc='How it Works']";
    public static final String START_FHC = "//android.widget.Button[@text='Start FHC']";
    public static final String BLOGS = "//android.view.View[@content-desc='Blogs']";
    public static final String ABOUT_US = "//android.view.View[@content-desc='About Us']";
    public static final String CONTACT_US = "//android.view.View[@content-desc='Contact Us']";
    public static final String TESTIMONIALS = "//android.view.View[@content-desc='Testimonials']";

    public static final String WHAT_IS_FHC_HEADING =
            "//android.widget.TextView[@text='Your Finances, clearly assessed']";
    public static final String HOW_IT_WORKS_HEADING =
            "//android.widget.TextView[contains(@text,'Financial Wellness Score')]";
    public static final String ABOUT_HEADING =
            "//android.widget.TextView[@text='Financial wellness, simplified for India']";
    public static final String CONTACT_HEADING = "//android.widget.TextView[@text='Contact Us']";
    public static final String TESTIMONIALS_HEADING = "//android.widget.TextView[@text='What Our Customers Say']";
    public static final String BLOGS_HEADING = "//android.widget.TextView[@text='airpay money Blog']";
    public static final String BLOGS_NEXT = "//android.widget.Button[@content-desc='Next page']";
    public static final String BLOGS_PREV = "//android.widget.Button[@content-desc='Previous page']";
}
