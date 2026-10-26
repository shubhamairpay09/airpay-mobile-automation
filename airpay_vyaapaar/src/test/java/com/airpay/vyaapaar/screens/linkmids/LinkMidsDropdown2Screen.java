package com.airpay.vyaapaar.screens.linkmids;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Link MIDs dropdown 2 — Owner. Module link-mids. */
public class LinkMidsDropdown2Screen extends BaseScreen {

    public static final String PAGE_ID = "link-mids-dropdown-2";

    public static final String OWNER = "//android.widget.TextView[@text='Owner']";

    @AndroidFindBy(xpath = OWNER)
    private WebElement owner;

    public static final String FATHER = "//android.widget.TextView[@text='Father']";

    @AndroidFindBy(xpath = FATHER)
    private WebElement father;

    public static final String MOTHER = "//android.widget.TextView[@text='Mother']";

    @AndroidFindBy(xpath = MOTHER)
    private WebElement mother;

    public static final String EMPLOYEE = "//android.widget.TextView[@text='Employee']";

    @AndroidFindBy(xpath = EMPLOYEE)
    private WebElement employee;

    public static final String SISTER = "//android.widget.TextView[@text='Sister']";

    @AndroidFindBy(xpath = SISTER)
    private WebElement sister;

    public static final String BROTHER = "//android.widget.TextView[@text='Brother']";

    @AndroidFindBy(xpath = BROTHER)
    private WebElement brother;

    public static final String COUSIN = "//android.widget.TextView[@text='Cousin']";

    @AndroidFindBy(xpath = COUSIN)
    private WebElement cousin;

    public static final String SPOUSE = "//android.widget.TextView[@text='Spouse']";

    @AndroidFindBy(xpath = SPOUSE)
    private WebElement spouse;

    public LinkMidsDropdown2Screen(TestContext context) {
        super(context);
    }
}
