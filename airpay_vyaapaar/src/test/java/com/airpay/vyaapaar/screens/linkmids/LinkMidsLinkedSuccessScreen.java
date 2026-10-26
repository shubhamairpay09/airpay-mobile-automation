package com.airpay.vyaapaar.screens.linkmids;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Merchant linked successfully. Module link-mids. */
public class LinkMidsLinkedSuccessScreen extends BaseScreen {

    public static final String PAGE_ID = "link-mids-linked-success";

    public static final String SUCCESS_ICON = "//android.widget.ImageView[contains(@content-desc,'Success Icon')]";

    @AndroidFindBy(xpath = SUCCESS_ICON)
    private WebElement successIcon;

    public static final String MERCHANT_LINKED_SUCCESSFULLY = "//android.widget.TextView[@text='Merchant linked successfully']";

    @AndroidFindBy(xpath = MERCHANT_LINKED_SUCCESSFULLY)
    private WebElement merchantLinkedSuccessfully;

    public static final String OK = "//android.widget.TextView[@text='OK']";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public LinkMidsLinkedSuccessScreen(TestContext context) {
        super(context);
    }
}
