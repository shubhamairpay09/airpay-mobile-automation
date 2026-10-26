package com.airpay.money.screens.calculators;

import com.airpay.mobile.utilities.waits.MobileWaits;
import org.openqa.selenium.By;

import java.time.Duration;

/** Live dump 2026-09-21 — Calculators tab (Financial Calculators hub), 15 tiles. */
public class CalculatorsHubScreen extends com.airpay.money.screens.base.BaseScreen {

    public static final String[] TILES = {
            "EMI Calculator",
            "SIP Calculator",
            "Lumpsum Calculator",
            "PPF Calculator",
            "EPF Calculator",
            "FD Calculator",
            "NPS Calculator",
            "Retirement Calculator",
            "Crorepati Calculator",
            "Term Insurance Calculator",
            "Buy vs Rent Calculator",
            "Taxation Indian Equity",
            "Taxation International Equity",
            "Inflation Calculator",
            "Budget Calculator"
    };

    public CalculatorsHubScreen(com.airpay.mobile.utilities.context.TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Financial Calculators");
    }

    public boolean listsAllTiles() {
        String all = catalogSource();
        for (String tile : TILES) {
            if (!all.contains(tile)) {
                return false;
            }
        }
        return true;
    }

    public String catalogSource() {
        StringBuilder seen = new StringBuilder(pageSource());
        for (int i = 0; i < 5; i++) {
            swipeUp();
            sleep(300);
            seen.append('\n').append(pageSource());
        }
        return seen.toString();
    }

    /** Hello, the Financial Calculators header, and the EMI Calculator tile occupy separate areas. */
    public boolean headerGreetingAndOptionSeparated() {
        org.openqa.selenium.WebElement hello = firstPresent(java.time.Duration.ofSeconds(3),
                com.airpay.mobile.utilities.waits.MobileWaits.xpath("//*[@content-desc='Hello']"));
        org.openqa.selenium.WebElement header = firstPresent(java.time.Duration.ofSeconds(3),
                com.airpay.mobile.utilities.waits.MobileWaits.xpath("//*[@content-desc='Financial Calculators']"));
        org.openqa.selenium.WebElement option = firstPresent(java.time.Duration.ofSeconds(3),
                com.airpay.mobile.utilities.waits.MobileWaits.xpath("//*[@content-desc='EMI Calculator']"));
        if (hello == null || header == null || option == null) {
            return false;
        }
        return separated(hello, header) && separated(header, option) && separated(hello, option);
    }

    private static boolean separated(org.openqa.selenium.WebElement first, org.openqa.selenium.WebElement second) {
        org.openqa.selenium.Rectangle a = first.getRect();
        org.openqa.selenium.Rectangle b = second.getRect();
        boolean overlapsX = a.getX() < b.getX() + b.getWidth() && b.getX() < a.getX() + a.getWidth();
        boolean overlapsY = a.getY() < b.getY() + b.getHeight() && b.getY() < a.getY() + a.getHeight();
        return !(overlapsX && overlapsY);
    }

    public boolean openTile(String label) {
        By tile = MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]");
        for (int i = 0; i < 6; i++) {
            if (tapFirst(Duration.ofSeconds(2), tile, accessibility(label))) {
                sleep(700);
                return true;
            }
            swipeUp();
            sleep(300);
        }
        return false;
    }

    public static final String TAB_CALCULATORS = "//android.widget.Button[@content-desc='Calculators']";
    public static final String TAB_HOME = "//android.widget.Button[@content-desc='Home']";
    public static final String TAB_FINSIGHT = "//android.widget.Button[@content-desc='FinSight']";

    public static final String OPEN_SETTINGS = "//android.widget.Button[@content-desc='Open settings']";
    public static final String NOTIFICATIONS = "//android.widget.ImageView[@content-desc='Notifications']";
    public static final String GREETING = "//android.view.View[@content-desc='Hello']";
    public static final String USER_NAME = "//android.view.View[@content-desc='Shubham Verma']";

    public static final String HERO_TITLE = "//android.view.View[@content-desc='Financial Calculators']";
    public static final String HERO_SUBTITLE =
            "//android.view.View[@content-desc='Empower your money decisions with our suite of calculators']";
    /** Hero illustration above the grid — unlabelled, so only reachable positionally. */
    public static final String HERO_IMAGE = "//android.widget.ImageView[@content-desc='' and @clickable='true']";

    public static final String TILE_EMI = "//android.view.View[@content-desc='EMI Calculator']";
    public static final String TILE_SIP = "//android.view.View[@content-desc='SIP Calculator']";
    public static final String TILE_LUMPSUM = "//android.view.View[@content-desc='Lumpsum Calculator']";
    public static final String TILE_PPF = "//android.view.View[@content-desc='PPF Calculator']";
    public static final String TILE_EPF = "//android.view.View[@content-desc='EPF Calculator']";
    public static final String TILE_FD = "//android.view.View[@content-desc='FD Calculator']";
    public static final String TILE_NPS = "//android.view.View[@content-desc='NPS Calculator']";
    public static final String TILE_RETIREMENT = "//android.view.View[@content-desc='Retirement Calculator']";
    public static final String TILE_CROREPATI = "//android.view.View[@content-desc='Crorepati Calculator']";
    public static final String TILE_TERM_INSURANCE = "//android.view.View[@content-desc='Term Insurance Calculator']";
    public static final String TILE_BUY_VS_RENT = "//android.view.View[@content-desc='Buy vs Rent Calculator']";
    public static final String TILE_TAXATION_INDIAN_EQUITY = "//android.view.View[@content-desc='Taxation Indian Equity']";
    public static final String TILE_TAXATION_INTERNATIONAL_EQUITY =
            "//android.view.View[@content-desc='Taxation International Equity']";
    public static final String TILE_INFLATION = "//android.view.View[@content-desc='Inflation Calculator']";
    public static final String TILE_BUDGET = "//android.view.View[@content-desc='Budget Calculator']";

    public static String tile(String label) {
        return "//android.view.View[@content-desc='" + label + "']";
    }
}
