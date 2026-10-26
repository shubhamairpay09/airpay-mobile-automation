package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Map. Module home. */
public class OpenMapScreen extends BaseScreen {

    public static final String PAGE_ID = "open-map";

    public static final String MAP = "//android.widget.TextView[@text='Map']";

    @AndroidFindBy(xpath = MAP)
    private WebElement map;

    public static final String GOOGLE_MAP = "//android.view.TextureView[contains(@content-desc,'Google Map')]";

    @AndroidFindBy(xpath = GOOGLE_MAP)
    private WebElement googleMap;

    public static final String ZOOM_IN = "//android.widget.ImageView[contains(@content-desc,'Zoom in')]";

    @AndroidFindBy(xpath = ZOOM_IN)
    private WebElement zoomIn;

    public static final String ZOOM_OUT = "//android.widget.ImageView[contains(@content-desc,'Zoom out')]";

    @AndroidFindBy(xpath = ZOOM_OUT)
    private WebElement zoomOut;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public OpenMapScreen(TestContext context) {
        super(context);
    }
}
