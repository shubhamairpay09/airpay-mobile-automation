package com.airpay.vyaapaar.screens.services;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Services/Link MIDs. Module services. */
public class ServicesLinkMidsScreen extends BaseScreen {

    public static final String PAGE_ID = "services-link-mids";

    public static final String I_HEREBY_GIVE_MY_CONSENT_TO_PROCEED_WITH = "//android.widget.TextView[@text='I hereby give my consent to proceed with linking the selected MID']";

    @AndroidFindBy(xpath = I_HEREBY_GIVE_MY_CONSENT_TO_PROCEED_WITH)
    private WebElement iHerebyGiveMyConsentToProceedWith;

    public static final String LINK = "//android.widget.TextView[@text='Link']";

    @AndroidFindBy(xpath = LINK)
    private WebElement link;

    public static final String LINKED_MIDS = "//android.widget.TextView[@text='Linked MIDs']";

    @AndroidFindBy(xpath = LINKED_MIDS)
    private WebElement linkedMids;

    public static final String EL28629 = "//android.widget.TextView[@text='28629']";

    @AndroidFindBy(xpath = EL28629)
    private WebElement el28629;

    public static final String EL30522 = "//android.widget.TextView[@text='30522']";

    @AndroidFindBy(xpath = EL30522)
    private WebElement el30522;

    public static final String EL28770 = "//android.widget.TextView[@text='28770']";

    @AndroidFindBy(xpath = EL28770)
    private WebElement el28770;

    public static final String LINK_MIDS = "//android.widget.TextView[@text='Link MIDs']";

    @AndroidFindBy(xpath = LINK_MIDS)
    private WebElement linkMids;

    public ServicesLinkMidsScreen(TestContext context) {
        super(context);
    }
}
