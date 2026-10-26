package com.airpay.vyaapaar.screens.profilebccertificate;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Profile / BC Certificate — BC Certificate. Module profile-bc-certificate. */
public class ProfileBcCertificateScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-bc-certificate";

    public static final String BC_CERTIFICATE = "//android.widget.TextView[@text='BC Certificate']";

    @AndroidFindBy(xpath = BC_CERTIFICATE)
    private WebElement bcCertificate;

    public static final String DO_YOU_HAVE_INDIAN_INSTITUTE_OF_BANKING_FINANCE = "//android.widget.TextView[@text='Do you have Indian Institute of Banking & Finance Certificate?']";

    @AndroidFindBy(xpath = DO_YOU_HAVE_INDIAN_INSTITUTE_OF_BANKING_FINANCE)
    private WebElement doYouHaveIndianInstituteOfBankingFinance;

    public static final String NO = "//android.widget.TextView[@text='No']";

    @AndroidFindBy(xpath = NO)
    private WebElement no;

    public static final String YES = "//android.widget.TextView[@text='Yes']";

    @AndroidFindBy(xpath = YES)
    private WebElement yes;

    public ProfileBcCertificateScreen(TestContext context) {
        super(context);
    }
}
