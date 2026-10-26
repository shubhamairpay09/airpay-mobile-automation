package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Diagnose end — YOU'RE DONE (share / download). Live dump 2026-09-16. */
public class DiagnosisDoneScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Share on WhatsApp']")
    private WebElement shareWhatsapp;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Download the PDF']")
    private WebElement downloadPdf;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Share on Email']")
    private WebElement shareEmail;

    public DiagnosisDoneScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Save. Share. Act.") || sourceContains("Share on WhatsApp");
    }
}
