package com.airpay.money.screens.menu;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class GrievanceScreen extends AbstractMenuScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Grievance')]")
    @iOSXCUITFindBy(accessibility = "Grievance")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    @iOSXCUITFindBy(className = "XCUIElementTypeTextField")
    private WebElement complaintField;

    public GrievanceScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Grievance", "Redressal", "complaint");
    }
}
