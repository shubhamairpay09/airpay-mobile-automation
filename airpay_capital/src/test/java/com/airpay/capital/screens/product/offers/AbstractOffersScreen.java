package com.airpay.capital.screens.product.offers;

import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;

import java.time.Duration;

/** Common assertions and lender navigation for product offer lists. */
public abstract class AbstractOffersScreen extends BaseScreen {

    protected AbstractOffersScreen(TestContext context) {
        super(context);
    }

    public boolean offersAreVisible() {
        return sourceContains(ScreenMessages.OFFERS_AVAILABLE, ScreenMessages.OFFER_AVAILABLE,
                ScreenMessages.APPLY_NOW_FOR);
    }

    public boolean noOffersAreVisible() {
        return sourceContains(ScreenMessages.NO_OFFERS_AVAILABLE, ScreenMessages.NO_LOAN_OFFERS,
                ScreenMessages.NO_OFFERS_REASON);
    }

    public abstract void tapFirstLenderOffer();

    protected boolean tapFirstLenderApply(String... xpaths) {
        for (String xpath : xpaths) {
            if (tapFirst(Duration.ofSeconds(4), MobileWaits.xpath(xpath))) {
                sleep(2500);
                return true;
            }
        }
        return tapAny(java.util.List.of("Apply now for", "Apply now"));
    }

    public boolean lenderPageOpened(String beforeSource) {
        String after = safeSource();
        return !beforeSource.equals(after)
                || sourceContains("partner", "lender", "chrome", "WebView")
                || !sourceContains("offers available");
    }
}
