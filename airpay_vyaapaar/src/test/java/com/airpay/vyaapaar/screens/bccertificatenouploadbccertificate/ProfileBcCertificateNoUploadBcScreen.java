package com.airpay.vyaapaar.screens.bccertificatenouploadbccertificate;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** BC Certificate / No / Upload BC Certificate — Choose from. Module bc-certificate-no-upload-bc-certificate. */
public class ProfileBcCertificateNoUploadBcScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-bc-certificate-no-upload-bc-certificate";

    public static final String CHOOSE_FROM = "//android.widget.TextView[@text='Choose from']";

    @AndroidFindBy(xpath = CHOOSE_FROM)
    private WebElement chooseFrom;

    public static final String GALLERY_ICON = "//android.view.View[contains(@content-desc,'Gallery icon')]";

    @AndroidFindBy(xpath = GALLERY_ICON)
    private WebElement galleryIcon;

    public static final String GALLERY = "//android.widget.TextView[@text='Gallery']";

    @AndroidFindBy(xpath = GALLERY)
    private WebElement gallery;

    public static final String CAMERA_ICON = "//android.view.View[contains(@content-desc,'Camera icon')]";

    @AndroidFindBy(xpath = CAMERA_ICON)
    private WebElement cameraIcon;

    public static final String CAMERA = "//android.widget.TextView[@text='Camera']";

    @AndroidFindBy(xpath = CAMERA)
    private WebElement camera;

    public ProfileBcCertificateNoUploadBcScreen(TestContext context) {
        super(context);
    }
}
