package com.airpay.capital.screens.product;

import com.airpay.mobile.utilities.context.TestContext;

public final class ProductLandingScreens {

    private ProductLandingScreens() {
    }

    public static AbstractProductLandingScreen forProduct(TestContext context, String productLabel) {
        return switch (productLabel.trim()) {
            case "Personal Loan" -> new PersonalLoanLandingScreen(context);
            case "Home Loan" -> new HomeLoanLandingScreen(context);
            case "Loan Against Property" -> new LapLandingScreen(context);
            case "Gold Loan" -> new GoldLoanLandingScreen(context);
            case "Credit Card" -> new CreditCardLandingScreen(context);
            default -> throw new IllegalArgumentException("Unknown product: " + productLabel);
        };
    }
}
