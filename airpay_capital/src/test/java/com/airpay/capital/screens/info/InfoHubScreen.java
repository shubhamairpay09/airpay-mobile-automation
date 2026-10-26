package com.airpay.capital.screens.info;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.capital.screens.info.about.InfoAboutScreen;
import com.airpay.capital.screens.info.contact.InfoContactScreen;
import com.airpay.capital.screens.info.faqs.InfoFaqsScreen;
import com.airpay.capital.screens.info.settings.InfoSettingsScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Info tab landing — hub rows only; each row opens a dedicated sub-screen class. */
public class InfoHubScreen extends BaseScreen {

    private static final Set<String> SKIP = Set.of(
            "Home", "Loan", "Info", "Share app", "Rate this app", "Logout", "Log out", "Back"
    );

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Information']")
    @iOSXCUITFindBy(accessibility = "Information")
    private WebElement heading;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'About airpay capital')]")
    @iOSXCUITFindBy(accessibility = "About airpay capital")
    private WebElement aboutRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'FAQs') or @content-desc='FAQ']")
    @iOSXCUITFindBy(accessibility = "FAQs")
    private WebElement faqsRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Contact Us')]")
    @iOSXCUITFindBy(accessibility = "Contact Us")
    private WebElement contactUsRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Settings')]")
    @iOSXCUITFindBy(accessibility = "Settings")
    private WebElement settingsRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Share app')]")
    @iOSXCUITFindBy(accessibility = "Share app")
    private WebElement shareAppRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Rate this app')]")
    @iOSXCUITFindBy(accessibility = "Rate this app")
    private WebElement rateAppRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'App version')]")
    @iOSXCUITFindBy(accessibility = "App version")
    private WebElement appVersion;

    public InfoHubScreen(TestContext context) {
        super(context);
    }

    public void open() {
        new HomeScreen(context).openInfoTab();
        sleep(700);
    }

    public boolean isDisplayed() {
        return sourceContains("Information", "About airpay capital", "FAQs", "Contact Us", "Settings");
    }

    public InfoAboutScreen openAbout() {
        navigateToAbout();
        return new InfoAboutScreen(context);
    }

    public InfoFaqsScreen openFaqs() {
        navigateToFaqs();
        return new InfoFaqsScreen(context);
    }

    public InfoContactScreen openContactUs() {
        navigateToContactUs();
        return new InfoContactScreen(context);
    }

    public InfoSettingsScreen openSettings() {
        navigateToSettings();
        return new InfoSettingsScreen(context);
    }

    public boolean navigateToAbout() {
        return tapHubRowQuiet("About airpay capital", "About");
    }

    public boolean navigateToFaqs() {
        return tapHubRowQuiet("FAQs", "FAQ");
    }

    public boolean navigateToContactUs() {
        return tapHubRowQuiet("Contact Us", "Contact us", "Contact");
    }

    public boolean navigateToSettings() {
        return tapHubRowQuiet("Settings");
    }

    private boolean tapHubRowQuiet(String... labels) {
        for (String label : labels) {
            if (tapLabel(label)) {
                sleep(800);
                return true;
            }
        }
        return false;
    }

    private void tapHubRow(String... labels) {
        if (!tapHubRowQuiet(labels)) {
            throw new IllegalStateException("Info hub row not found: " + String.join(", ", labels));
        }
    }

    public List<String> sectionLabels() {
        List<String> labels = new ArrayList<>();
        List<WebElement> nodes = driver.findElements(MobileWaits.xpath(
                "//*[@clickable='true' and string-length(@content-desc)>0]"));
        for (WebElement n : nodes) {
            try {
                String desc = n.getAttribute("contentDescription");
                if (desc == null || desc.isBlank()) {
                    continue;
                }
                String first = desc.split("\n")[0].trim();
                if (first.isBlank() || first.length() > 80 || SKIP.contains(first)) {
                    continue;
                }
                labels.add(first);
            } catch (Exception ignored) {
                // stale
            }
        }
        return labels;
    }

    public void scrollDown(int swipes) {
        for (int i = 0; i < swipes; i++) {
            swipeUp();
            sleep(350);
        }
    }
}
