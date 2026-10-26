package com.airpay.vyaapaar.screens.certificatedownloadicon;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Certificate / Download Icon — Image Content. Module certificate-download-icon. */
public class CertificateDownloadIconScreen extends BaseScreen {

    public static final String PAGE_ID = "certificate-download-icon";

    public static final String IMAGE_CONTENT = "//android.view.View[contains(@content-desc,'Image Content')]";

    @AndroidFindBy(xpath = IMAGE_CONTENT)
    private WebElement imageContent;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public static final String CERTIFICATE = "//android.widget.TextView[@text='Certificate']";

    @AndroidFindBy(xpath = CERTIFICATE)
    private WebElement certificate;

    public static final String DOWNLOAD_ICON = "//android.view.View[contains(@content-desc,'Download Icon')]";

    @AndroidFindBy(xpath = DOWNLOAD_ICON)
    private WebElement downloadIcon;

    public static final String DOWNLOAD_COMPLETED = "//android.widget.Toast[@text='Download Completed']";

    @AndroidFindBy(xpath = DOWNLOAD_COMPLETED)
    private WebElement downloadCompleted;

    public static final String AIRPAY_VYAAPAAR = "//android.widget.Toast[@text='airpay vyaapaar']";

    @AndroidFindBy(xpath = AIRPAY_VYAAPAAR)
    private WebElement airpayVyaapaar;

    public CertificateDownloadIconScreen(TestContext context) {
        super(context);
    }
}
