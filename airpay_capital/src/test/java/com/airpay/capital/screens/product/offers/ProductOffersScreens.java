package com.airpay.capital.screens.product.offers;

import com.airpay.mobile.utilities.context.TestContext;

public final class ProductOffersScreens {

    private ProductOffersScreens() {
    }

    public static AbstractOffersScreen forProduct(TestContext context, String productLabel) {
        return switch (productLabel.trim()) {
            case "Personal Loan" -> new PersonalLoanOffersScreen(context);
            case "Home Loan" -> new HomeLoanOffersScreen(context);
            case "Loan Against Property" -> new LapOffersScreen(context);
            case "Gold Loan" -> new GoldLoanOffersScreen(context);
            case "Credit Card" -> new CreditCardOffersScreen(context);
            default -> throw new IllegalArgumentException("Unknown product: " + productLabel);
        };
    }
}
