package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Recommendation View all — Funds basis on your risk profile.
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsRecommendationScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Funds basis on your risk')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='']")
    private WebElement filter;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='']")
    private WebElement search;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Clear All']")
    private WebElement chipClearAll;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Aditya Birla Sun Life Medium Term Plan')]")
    private WebElement firstFund;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'HDFC Money Market Fund')]")
    private WebElement hdfcMoneyMarket;

    public MutualFundsRecommendationScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        String src = pageSource().replace('\n', ' ').replace('\r', ' ')
                .replace("&#10;", " ").replaceAll("\\s+", " ");
        return src.contains("Funds basis on your")
                || src.contains("Funds based on your");
    }

    public boolean openFundCard() {
        String[] names = {
                "DSP Liquidity Fund",
                "Aditya Birla Sun Life Liquid",
                "Aditya Birla Sun Life Pharma",
                "PGIM India Liquid",
                "WhiteOak Capital",
                "Nippon India Multi",
                "UTI Liquid",
                "UTI Healthcare"
        };
        for (String name : names) {
            if (tapNamedFund(name) && waitFundDetails(4000)) {
                return true;
            }
        }
        if (tapFirstAny(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc,'Fund')"
                        + " and (contains(@content-desc,'Growth') or contains(@content-desc,'Plan'))"
                        + " and not(contains(@content-desc,'Funds basis'))"
                        + " and not(contains(@content-desc,'Funds based'))"
                        + " and not(contains(@content-desc,'Mutual Funds'))]"))) {
            return waitFundDetails(4000);
        }
        return waitFundDetails(500);
    }

    private boolean tapNamedFund(String name) {
        if (tapFirstAny(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(name) + ")]"))) {
            return true;
        }
        WebElement card = firstPresent(Duration.ofMillis(700),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(name) + ")]"));
        if (card == null) {
            return false;
        }
        pressElement(card);
        return true;
    }

    private boolean waitFundDetails(int timeoutMs) {
        long end = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < end) {
            String src = pageSource();
            if (src.contains("One Time") || src.contains("Monthly") || src.contains("Fund Details")) {
                return true;
            }
            sleep(250);
        }
        return false;
    }
}
