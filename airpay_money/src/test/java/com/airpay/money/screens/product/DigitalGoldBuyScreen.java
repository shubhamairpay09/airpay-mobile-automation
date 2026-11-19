package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Buy Gold &amp; Silver. Dump {@code uidump-gold-11-buy} / {@code uidump-gold-26-buy-gold} 2.1.3.
 * Proceed opens the order summary. Make Payment is not confirmed with a card or UPI.
 */
public class DigitalGoldBuyScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Buy Gold')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Gold') and contains(@content-desc,'Tab 1')]")
    private WebElement goldTab;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Silver') and contains(@content-desc,'Tab 2')]")
    private WebElement silverTab;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'AUGMONT')]")
    private WebElement augmontCard;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Investment type')]")
    private WebElement investmentType;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy in rupees']")
    private WebElement buyInRupees;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy in grams']")
    private WebElement buyInGrams;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='₹ 100']")
    private WebElement chip100;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='₹ 200']")
    private WebElement chip200;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='₹ 500']")
    private WebElement chip500;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='₹ 1000']")
    private WebElement chip1000;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='₹ 2000']")
    private WebElement chip2000;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement amount;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='0.25']")
    private WebElement chip025g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='0.50']")
    private WebElement chip050g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='1.0']")
    private WebElement chip10g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='1.50']")
    private WebElement chip150g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='3.0']")
    private WebElement chip30g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='5.0']")
    private WebElement chip50g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='One Time']")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Monthly ( SIP )']")
    private WebElement monthlySip;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Dismiss']")
    private WebElement dismissSheet;

    @AndroidFindBy(xpath = "//android.widget.CheckBox[contains(@content-desc,'Accept terms')]")
    private WebElement acceptTerms;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Privacy Policy')]")
    private WebElement privacyPolicy;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Terms and Conditions')]")
    private WebElement terms;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Proceed']")
    private WebElement proceed;

    public DigitalGoldBuyScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        boolean form = sourceContains("Buy in rupees", "Investment type", "Buy in grams");
        if (!form) {
            return false;
        }
        return sourceContains("Buy Gold", "Buy Gold &", "Buy Gold and", "Silver");
    }

    public void openSilverTab() {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Silver') and contains(@content-desc,'Tab')]"));
        sleep(500);
    }

    public void openInvestmentType() {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Investment type')]"));
        sleep(400);
    }

    public void chooseBuyInGrams() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Buy in grams']"));
    }

    public void chooseBuyInRupees() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Buy in rupees']"));
    }

    public void tapAmountChip(String rupees) {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc=" + xpathLiteral("₹ " + rupees) + "]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'" + rupees + "')]"));
    }

    public void tapGramChip(String grams) {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc=" + xpathLiteral(grams) + "]"));
    }

    public void chooseOneTime() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='One Time']"));
    }

    public void chooseMonthlySip() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Monthly ( SIP )']"));
    }

    public void openPrivacyPolicy() {
        revealBottomActions();
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Privacy Policy')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Privacy Policy')]"));
        sleep(700);
    }

    public void openTerms() {
        revealBottomActions();
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Terms and Conditions')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Terms and Conditions')]"));
        sleep(700);
    }

    public void enterAmount(String digits) {
        WebElement field = firstPresent(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.widget.EditText"));
        if (field == null) {
            return;
        }
        field.click();
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter fields often reject clear()
        }
        field.sendKeys(digits);
        sleep(300);
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard already closed
        }
        sleep(300);
    }

    public void revealBottomActions() {
        for (int i = 0; i < 3; i++) {
            if (!driver.findElements(MobileWaits.xpath("//android.widget.Button[@content-desc='Proceed']")).isEmpty()
                    && !driver.findElements(MobileWaits.xpath("//android.widget.CheckBox")).isEmpty()) {
                return;
            }
            swipeUp();
        }
    }

    public boolean consentChecked() {
        WebElement box = consentBox(Duration.ofSeconds(3));
        return isChecked(box);
    }

    /** Toggles the consent box once and waits until its checked state flips. */
    public void acceptConsent() {
        hideSoftKeyboard();
        revealBottomActions();
        WebElement box = consentBox(Duration.ofSeconds(4));
        if (box == null) {
            return;
        }
        boolean before = isChecked(box);
        WebElement label = firstPresent(Duration.ofSeconds(1),
                MobileWaits.xpath("//android.view.View[starts-with(@content-desc,'By clicking proceed')]"));
        if (label != null) {
            var text = label.getRect();
            // Privacy Policy / Terms links sit mid-text; press the opening words only.
            shellPress(text.getX() + 24, text.getY() + Math.min(20, text.getHeight() / 4));
            if (waits.until(() -> consentChecked() != before, Duration.ofMillis(1_500))) {
                return;
            }
        }
        var rect = box.getRect();
        shellPress(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
        if (waits.until(() -> consentChecked() != before, Duration.ofMillis(1_500))) {
            return;
        }
        WebElement again = consentBox(Duration.ofSeconds(1));
        if (again != null) {
            again.click();
            waits.until(() -> consentChecked() != before, Duration.ofMillis(1_500));
        }
    }

    /** Amount is auto-focused on open; its number pad sits in its own window over the consent row. */
    private void hideSoftKeyboard() {
        if (!keyboardShown()) {
            return;
        }
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // Appium skips hiding when it believes no keyboard is up; ESC below closes it.
        }
        if (waits.until(() -> !keyboardShown(), Duration.ofSeconds(1))) {
            return;
        }
        AdbCommands.shell(udid(), "input", "keyevent", "111");
        waits.until(() -> !keyboardShown(), Duration.ofSeconds(2));
    }

    /** Flutter fields leave {@code mInputShown=false} while the pad is up, so Appium's check misses it. */
    private boolean keyboardShown() {
        String ime = AdbCommands.shellOut(udid(), 5, "dumpsys", "input_method");
        return ime != null && ime.contains("mIsInputViewShown=true");
    }

    private String udid() {
        return context.config().get("android.udid", "");
    }

    private WebElement consentBox(Duration wait) {
        return firstPresent(wait,
                MobileWaits.xpath("//android.widget.CheckBox[contains(@content-desc,'Accept terms')]"),
                MobileWaits.xpath("//android.widget.CheckBox"));
    }

    private static boolean isChecked(WebElement box) {
        try {
            return box != null && "true".equals(box.getAttribute("checked"));
        } catch (Exception stale) {
            return false;
        }
    }

    public boolean proceedEnabled() {
        WebElement button = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Proceed']"));
        return button != null && "true".equals(button.getAttribute("enabled"));
    }

    public void tapProceed() {
        revealBottomActions();
        tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Proceed']"),
                accessibility("Proceed"));
        sleep(1200);
    }
}
