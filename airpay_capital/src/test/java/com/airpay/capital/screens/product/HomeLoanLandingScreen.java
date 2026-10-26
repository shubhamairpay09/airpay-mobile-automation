package com.airpay.capital.screens.product;

import com.airpay.mobile.utilities.context.TestContext;

public class HomeLoanLandingScreen extends AbstractProductLandingScreen {

    public static final String LABEL = "Home Loan";

    public HomeLoanLandingScreen(TestContext context) {
        super(context, LABEL);
    }

    public boolean tapApplyNow() {
        return openFromHome();
    }
}
