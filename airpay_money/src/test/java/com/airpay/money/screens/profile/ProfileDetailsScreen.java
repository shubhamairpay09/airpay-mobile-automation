package com.airpay.money.screens.profile;

/**
 * Live dump 2026-09-21 — Personal / Work / Address / Bank / Documents / KYC.
 * Native Flutter. Dummy bank IFSC {@code HDFC0000088}; no real account submitted.
 */
public class ProfileDetailsScreen extends com.airpay.money.screens.base.BaseScreen {

    public ProfileDetailsScreen(com.airpay.mobile.utilities.context.TestContext context) {
        super(context);
    }

    public static final String BACK = "//android.widget.Button[@content-desc='Back']";
    public static final String UPDATE = "//android.widget.Button[@content-desc='Update']";
    public static final String SUBMIT = "//android.widget.Button[@content-desc='Submit']";
    public static final String GOT_IT = "//android.widget.Button[@content-desc='Got it']";
    public static final String DISMISS = "//android.view.View[@content-desc='Dismiss']";

    public static final String PERSONAL_TITLE = "//android.view.View[@content-desc='Personal Details']";
    public static final String PERSONAL_EDIT = "//android.widget.Button[@content-desc='Edit personal details']";
    public static final String PERSONAL_EDIT_TITLE = "//android.view.View[@content-desc='Personal details']";
    public static final String FULL_NAME = "//android.widget.EditText[@hint='Full name']";
    public static final String FATHER_NAME = "//android.widget.EditText[@hint=\"Father's name\"]";
    public static final String MOTHER_NAME = "//android.widget.EditText[@hint=\"Mother's name\"]";
    public static final String DATE_OF_BIRTH = "//android.view.View[contains(@content-desc,'Date of birth')]";
    public static final String MOBILE_NUMBER = "//android.widget.EditText[@hint='Mobile Number']";
    public static final String GENDER_MALE = "//android.widget.ImageView[@content-desc='Male']";
    public static final String GENDER_FEMALE = "//android.widget.ImageView[@content-desc='Female']";
    public static final String GENDER_OTHER = "//android.widget.ImageView[@content-desc='Other']";
    public static final String MARITAL_STATUS = "//android.view.View[contains(@content-desc,'Marital status')]";
    public static final String EMPLOYMENT_TYPE = "//android.view.View[contains(@content-desc,'employment type')]";
    public static final String MARITAL_SINGLE = "//android.view.View[@content-desc='Single']";
    public static final String MARITAL_MARRIED = "//android.view.View[@content-desc='Married']";
    public static final String MARITAL_WIDOWED = "//android.view.View[@content-desc='Widowed']";
    public static final String MARITAL_DIVORCED = "//android.view.View[@content-desc='Divorced']";
    public static final String MARITAL_SEPARATED = "//android.view.View[@content-desc='Separated']";
    public static final String DOB_CANCEL = "//android.widget.Button[@content-desc='Cancel']";
    public static final String DOB_DONE = "//android.widget.Button[@content-desc='Done']";
    public static final String LOCKED_PROFILE_BANNER =
            "//android.view.View[contains(@content-desc,'These details cannot be edited further')]";

    public static final String WORK_PROFILE = "//android.view.View[@content-desc='Work Profile']";
    public static final String WORK_ADD_TITLE = "//android.view.View[@content-desc='Add Your Work Details']";
    public static final String SELECT_COMPANY = "//android.view.View[@content-desc='Select company']";
    public static final String SELECT_ORG_TYPE = "//android.view.View[@content-desc='Select organization type']";
    public static final String SELECT_DESIGNATION = "//android.view.View[@content-desc='Select designation']";
    public static final String MONTHLY_INCOME = "//android.widget.EditText[@hint='Monthly Income']";
    public static final String WORK_EMAIL = "//android.widget.EditText[@hint='Work Email']";
    public static final String EXP_YEAR_0 = "//android.view.View[@content-desc='0 year']";
    public static final String EXP_MONTH_0 = "//android.view.View[@content-desc='0 month']";
    public static final String MODE_VIA_BANK = "//android.view.View[@content-desc='Via bank']";
    public static final String MODE_CHEQUE = "//android.view.View[@content-desc='Cheque']";
    public static final String COMPANY_SEARCH = "//android.widget.EditText[@hint='Select company']";
    public static final String ORG_TYPE_SEARCH = "//android.widget.EditText[@hint='Select organization type']";
    public static final String DESIGNATION_SEARCH = "//android.widget.EditText[@hint='Select designation']";
    public static final String WORK_ERR_COMPANY = "//android.view.View[contains(@content-desc,'Please select company')]";
    public static final String WORK_ERR_INCOME = "//android.view.View[@content-desc='Please enter Monthly Income']";
    public static final String WORK_ERR_EMAIL = "//android.view.View[@content-desc='Please enter work email']";

    public static final String ADDRESS_TITLE = "//android.view.View[@content-desc='Address Details']";
    public static final String CURRENT_ADDRESS = "//android.view.View[@content-desc='Current Address']";
    public static final String PERMANENT_ADDRESS = "//android.view.View[@content-desc='Permanent Address']";
    public static final String RESIDENTIAL_ADDRESS = "//android.view.View[@content-desc='Residential Address']";
    public static final String FLAT_HOUSE = "//android.widget.EditText[@hint='Flat, House, Building Number']";
    public static final String AREA_LOCALITY = "//android.widget.EditText[@hint='Area, locality, street']";
    public static final String LANDMARK = "//android.widget.EditText[@hint='Landmark']";
    public static final String PINCODE = "//android.widget.EditText[contains(@hint,'Pincode')]";
    public static final String ADDR_MOBILE = "//android.widget.EditText[@hint='Mobile number']";
    public static final String SELECT_OWNERSHIP = "//android.view.View[@content-desc='Select your ownership']";
    public static final String RESIDENCE_OWNED_SELF = "//android.view.View[@content-desc='Owned-by-self spouse']";
    public static final String RESIDENCE_HOSTEL = "//android.view.View[@content-desc='Hostel']";
    public static final String ADDR_ERR_FLAT =
            "//android.view.View[@content-desc='Please enter Flat, House, Building Number']";

    public static final String SELECT_BANK_ACCOUNT = "//android.view.View[@content-desc='Select Bank Account']";
    public static final String ADD_BANK = "//android.view.View[@content-desc='Add bank']";
    public static final String ADD_BANK_BUTTON = "//android.widget.Button[@content-desc='Add bank']";
    public static final String VERIFY = "//android.widget.Button[@content-desc='Verify']";
    public static final String SELECT_YOUR_BANK = "//android.view.View[@content-desc='Select your Bank']";
    public static final String SEARCH_BANK = "//android.widget.EditText[@hint='Search Bank']";
    public static final String HDFC_BANK = "//android.view.View[@content-desc='HDFC Bank']";
    public static final String SELECT_BRANCH = "//android.view.View[@content-desc='Select your bank branch']";
    public static final String IFSC_HDFC0000088 =
            "//android.view.View[contains(@content-desc,'HDFC0000088')]";
    public static final String VERIFY_BANK_ACCOUNT = "//android.view.View[@content-desc='Verify Bank Account']";
    public static final String HOLDER_NAME_HINT = "//android.widget.EditText[contains(@hint,'holder name')]";
    public static final String ACCOUNT_NUMBER_HINT = "//android.widget.EditText[contains(@hint,'account number')]";
    public static final String ERR_HOLDER_NAME =
            "//android.view.View[@content-desc='Please enter Bank account holder name']";
    public static final String ERR_ACCOUNT = "//android.view.View[@content-desc='Please enter Bank account']";

    public static final String MY_DOCUMENTS = "//android.view.View[@content-desc=' My Documents']";
    public static final String NO_DOCUMENT = "//android.view.View[@content-desc='No Document found!']";
    public static final String KYC_TITLE = "//android.view.View[@content-desc='KYC Details']";
    public static final String KYC_VERIFIED = "//android.view.View[@content-desc='KYC VERIFIED']";
    public static final String KYC_NAME_AS_PAN = "//android.view.View[@content-desc='Name as per PAN:']";
    public static final String KYC_AADHAAR = "//android.view.View[@content-desc='Aadhaar No:']";
    public static final String KYC_PAN = "//android.view.View[@content-desc='PAN No:']";
}
