package com.airpay.money.steps;

import org.testng.SkipException;

/** Skips the current scenario when this account does not have the detail the sheet case needs. */
public final class ScenarioSkip {

    private ScenarioSkip() {
    }

    public static void unlessPresent(boolean present, String detail) {
        if (!present) {
            throw new SkipException("Skipped: " + detail + " is not on this account");
        }
    }
}
