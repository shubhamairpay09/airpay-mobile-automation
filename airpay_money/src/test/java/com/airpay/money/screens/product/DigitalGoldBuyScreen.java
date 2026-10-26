package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
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
        return sourceContains("Buy Gold") && sourceContains("Buy in rupees", "Investment type");
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
        WebElement box = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.CheckBox[contains(@content-desc,'Accept terms')]"),
                MobileWaits.xpath("//android.widget.CheckBox"));
        return box != null && "true".equals(box.getAttribute("checked"));
    }

    public void acceptConsent() {
        revealBottomActions();
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.CheckBox[contains(@content-desc,'Accept terms')]"),
                MobileWaits.xpath("//android.widget.CheckBox"));
        sleep(300);
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
