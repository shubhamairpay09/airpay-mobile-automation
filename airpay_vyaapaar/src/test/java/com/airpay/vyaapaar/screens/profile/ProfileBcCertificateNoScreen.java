package com.airpay.vyaapaar.screens.profile;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Profile/BC Certificate/No. Module profile. */
public class ProfileBcCertificateNoScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-bc-certificate-no";

    public static final String CERTIFICATE_ICON = "//android.view.View[contains(@content-desc,'Certificate icon')]";

    @AndroidFindBy(xpath = CERTIFICATE_ICON)
    private WebElement certificateIcon;

    public static final String PLEASE_ENSURE_TO_UPLOAD_YOUR_INDIAN_INSTITUTE_OF = "//android.widget.TextView[@text='Please ensure to upload your Indian Institute of Banking & Finance (IIBF) certificate.']";

    @AndroidFindBy(xpath = PLEASE_ENSURE_TO_UPLOAD_YOUR_INDIAN_INSTITUTE_OF)
    private WebElement pleaseEnsureToUploadYourIndianInstituteOf;

    public static final String UPLOAD_BC_CERTIFICATE = "//android.widget.TextView[@text='Upload BC Certificate']";

    @AndroidFindBy(xpath = UPLOAD_BC_CERTIFICATE)
    private WebElement uploadBcCertificate;

    public static final String BC_CERTIFICATE = "//android.widget.TextView[@text='BC Certificate']";

    @AndroidFindBy(xpath = BC_CERTIFICATE)
    private WebElement bcCertificate;

    public ProfileBcCertificateNoScreen(TestContext context) {
        super(context);
    }
}
