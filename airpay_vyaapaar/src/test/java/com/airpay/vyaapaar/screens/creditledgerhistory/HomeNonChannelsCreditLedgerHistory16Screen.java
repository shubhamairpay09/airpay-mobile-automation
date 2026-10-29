package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/ONDC. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory16Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-ondc";

    public static final String BUSINESS_LOAN = "//android.widget.TextView[@text='Business Loan']";

    @AndroidFindBy(xpath = BUSINESS_LOAN)
    private WebElement businessLoan;

    public static final String CAR_LOAN = "//android.widget.TextView[@text='Car Loan']";

    @AndroidFindBy(xpath = CAR_LOAN)
    private WebElement carLoan;

    public static final String GOLD_LOAN = "//android.widget.TextView[@text='Gold Loan']";

    @AndroidFindBy(xpath = GOLD_LOAN)
    private WebElement goldLoan;

    public static final String HOUSING_LOAN = "//android.widget.TextView[@text='Housing Loan']";

    @AndroidFindBy(xpath = HOUSING_LOAN)
    private WebElement housingLoan;

    public static final String PERSONAL_LOAN = "//android.widget.TextView[@text='Personal Loan']";

    @AndroidFindBy(xpath = PERSONAL_LOAN)
    private WebElement personalLoan;

    public static final String VEHICLE_LOAN = "//android.widget.TextView[@text='Vehicle Loan']";

    @AndroidFindBy(xpath = VEHICLE_LOAN)
    private WebElement vehicleLoan;

    public static final String OTHER_LOANS = "//android.widget.TextView[@text='Other Loans']";

    @AndroidFindBy(xpath = OTHER_LOANS)
    private WebElement otherLoans;

    public HomeNonChannelsCreditLedgerHistory16Screen(TestContext context) {
        super(context);
    }
}
