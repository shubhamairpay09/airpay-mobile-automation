package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Khata/Edit Icon. Module khata. */
public class HomeFeaturedServicesKhataEditIcon2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-khata-edit-icon-edit-icon";

    public static final String CHOOSE_FROM = "//android.widget.TextView[contains(@content-desc,'Choose from')]";

    @AndroidFindBy(xpath = CHOOSE_FROM)
    private WebElement chooseFrom;

    public static final String DOCUMENT_ICON = "//android.view.View[contains(@content-desc,'Document icon')]";

    @AndroidFindBy(xpath = DOCUMENT_ICON)
    private WebElement documentIcon;

    public static final String DOCUMENT = "//android.widget.TextView[contains(@content-desc,'Document')]";

    @AndroidFindBy(xpath = DOCUMENT)
    private WebElement document;

    public static final String GALLERY_ICON = "//android.view.View[contains(@content-desc,'Gallery icon')]";

    @AndroidFindBy(xpath = GALLERY_ICON)
    private WebElement galleryIcon;

    public static final String GALLERY = "//android.widget.TextView[contains(@content-desc,'Gallery')]";

    @AndroidFindBy(xpath = GALLERY)
    private WebElement gallery;

    public static final String CAMERA_ICON = "//android.view.View[contains(@content-desc,'Camera icon')]";

    @AndroidFindBy(xpath = CAMERA_ICON)
    private WebElement cameraIcon;

    public static final String CAMERA = "//android.widget.TextView[contains(@content-desc,'Camera')]";

    @AndroidFindBy(xpath = CAMERA)
    private WebElement camera;

    public HomeFeaturedServicesKhataEditIcon2Screen(TestContext context) {
        super(context);
    }
}
