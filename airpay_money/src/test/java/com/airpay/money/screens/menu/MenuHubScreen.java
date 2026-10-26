package com.airpay.money.screens.menu;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** More / Profile / Menu hub — each row opens a dedicated sub-screen class. */
public class MenuHubScreen extends BaseScreen {

    private static final Set<String> SKIP = Set.of(
            "Home", "Invest", "Portfolio", "Loans", "More", "Back", "Logout", "Log out", "Share app", "Rate this app");

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'About Us') or contains(@content-desc,'About airpay')]")
    @iOSXCUITFindBy(accessibility = "About Us")
    private WebElement aboutRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Contact Us')]")
    @iOSXCUITFindBy(accessibility = "Contact Us")
    private WebElement contactRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Settings')]")
    @iOSXCUITFindBy(accessibility = "Settings")
    private WebElement settingsRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'FAQs') or contains(@content-desc,'FAQ')]")
    @iOSXCUITFindBy(accessibility = "FAQs")
    private WebElement faqsRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Privacy Policy')]")
    @iOSXCUITFindBy(accessibility = "Privacy Policy")
    private WebElement privacyRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Terms')]")
    @iOSXCUITFindBy(accessibility = "Terms and Conditions")
    private WebElement termsRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Grievance')]")
    @iOSXCUITFindBy(accessibility = "Grievance")
    private WebElement grievanceRow;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Notifications')]")
    @iOSXCUITFindBy(accessibility = "Notifications")
    private WebElement notificationsRow;

    public MenuHubScreen(TestContext context) {
        super(context);
    }

    public void open() {
        new HomeScreen(context).openMoreTab();
        sleep(600);
    }

    public boolean isDisplayed() {
        return sourceContains("About", "Settings", "Contact", "FAQs", "Profile", "More");
    }

    public AboutScreen openAbout() {
        tapRow("About Us", "About airpay money", "About");
        return new AboutScreen(context);
    }

    public ContactScreen openContact() {
        tapRow("Contact Us", "Contact us", "Contact");
        return new ContactScreen(context);
    }

    public SettingsScreen openSettings() {
        tapRow("Settings");
        return new SettingsScreen(context);
    }

    public FaqsScreen openFaqs() {
        tapRow("FAQs", "FAQ", "Help");
        return new FaqsScreen(context);
    }

    public PrivacyScreen openPrivacy() {
        tapRow("Privacy Policy", "Privacy");
        return new PrivacyScreen(context);
    }

    public TermsScreen openTerms() {
        tapRow("Terms and Conditions", "Terms & Conditions", "Terms");
        return new TermsScreen(context);
    }

    public GrievanceScreen openGrievance() {
        tapRow("Grievance", "Grievance Redressal");
        return new GrievanceScreen(context);
    }

    public boolean navigateToAbout() {
        return tapRowQuiet("About Us", "About airpay money", "About");
    }

    public boolean navigateToContact() {
        return tapRowQuiet("Contact Us", "Contact us", "Contact");
    }

    public boolean navigateToSettings() {
        return tapRowQuiet("Settings");
    }

    public boolean navigateToFaqs() {
        return tapRowQuiet("FAQs", "FAQ", "Help");
    }

    public boolean navigateToPrivacy() {
        return tapRowQuiet("Privacy Policy", "Privacy");
    }

    public boolean navigateToTerms() {
        return tapRowQuiet("Terms and Conditions", "Terms & Conditions", "Terms");
    }

    public boolean navigateToGrievance() {
        return tapRowQuiet("Grievance", "Grievance Redressal");
    }

    private boolean tapRow(String... labels) {
        if (!tapRowQuiet(labels)) {
            throw new IllegalStateException("Menu row not found: " + String.join(", ", labels));
        }
        return true;
    }

    private boolean tapRowQuiet(String... labels) {
        for (String label : labels) {
            if (tapFirst(Duration.ofSeconds(5),
                    MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"),
                    accessibility(label))) {
                sleep(700);
                return true;
            }
        }
        return false;
    }

    public List<String> clickableLabels() {
        List<String> labels = new ArrayList<>();
        List<WebElement> nodes;
        try {
            nodes = driver.findElements(MobileWaits.xpath(
                    "//*[@clickable='true' and string-length(@content-desc)>0]"));
        } catch (org.openqa.selenium.WebDriverException e) {
            return labels;
        }
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
