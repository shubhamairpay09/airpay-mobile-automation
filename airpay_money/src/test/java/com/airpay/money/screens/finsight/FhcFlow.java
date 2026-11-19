package com.airpay.money.screens.finsight;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.data.MoneyTestData;
import com.airpay.money.hooks.FhcErrorLog;
import com.airpay.money.steps.MoneyFunctionalFast;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.money.hooks.FunctionalModuleReset;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.android.AdbInput;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Financial Health Check journey from the sheet.
 * Dummy amounts only. KYC is not started. Diagnosis share actions are not tapped.
 */
public class FhcFlow extends BaseScreen {

    private static final Logger LOG = LogManager.getLogger(FhcFlow.class);

    public static final String PINCODE = "247001";
    public static final String CITY = "Saharanpur";
    public static final String STATE = "Uttar Pradesh";
    public static final String INCOME = "100000";
    public static final String EXPENSE = "40000";
    public static final String LIFESTYLE = "10000";
    public static final String PRINCIPAL = "500000";
    public static final String EMI = "10000";
    public static final String RATE = "12";
    public static final String CURRENT_VALUE = "100000";
    public static final String LOAN_EDUCATION = "Education Loan";
    public static final String INVESTMENT = "Public Provident Fund (PPF)";

    public static String pincode() {
        return MoneyExcel.text("Pincode", PINCODE);
    }

    public static String city() {
        return MoneyExcel.text("City", CITY);
    }

    public static String income() {
        return MoneyExcel.text("Income", INCOME);
    }

    public static String expense() {
        return MoneyExcel.text("Expenses", EXPENSE);
    }

    public static String lifestyleExpense() {
        return MoneyExcel.text("Lifestyle", LIFESTYLE);
    }

    public static String principal() {
        return MoneyExcel.text("Principal", PRINCIPAL);
    }

    public static String emi() {
        return MoneyExcel.text("EMI", EMI);
    }

    public static String rate() {
        return MoneyExcel.text("InterestRate", RATE);
    }

    public static String currentValue() {
        return MoneyExcel.text("CurrentValue", CURRENT_VALUE);
    }

    public static String bankBalance() {
        return MoneyExcel.text("BankBalance", CURRENT_VALUE);
    }

    public static String loanType() {
        return MoneyExcel.text("LoanType", LOAN_EDUCATION);
    }

    public static String investment() {
        return MoneyExcel.text("Investment", INVESTMENT);
    }

    private static final Pattern DECIMAL_SCORE = Pattern.compile("(\\d{1,3}\\.\\d)");

    private final HomeScreen home;
    private final FinSightHomeScreen finsight;
    private String lastDump = "";
    private long lastDumpAt;

    public FhcFlow(TestContext context) {
        super(context);
        this.home = new HomeScreen(context);
        this.finsight = new FinSightHomeScreen(context);
    }

    public String text() {
        long now = System.currentTimeMillis();
        long cacheMs = MoneyFunctionalFast.pageSourceCacheMs(context);
        if (!lastDump.isBlank() && now - lastDumpAt < cacheMs) {
            return lastDump;
        }
        String src = pageSource().replace("&amp;", "&").replace("&#10;", " ").replace('\n', ' ');
        lastDump = src;
        lastDumpAt = now;
        return src;
    }

    private void invalidateDump() {
        lastDump = "";
        lastDumpAt = 0;
    }

    public boolean shows(String label) {
        return text().contains(label);
    }

    /** Flutter page source on short phones often omits off-screen fields. */
    public boolean reveal(String label) {
        if (labelVisible(label)) {
            return true;
        }
        scrollIntoDescription(label);
        if (labelVisible(label)) {
            return true;
        }
        if (onIncome()) {
            return labelVisible(label) || shows(label);
        }
        return scrollUntilShows(label, scrollBudget(label));
    }

    private boolean labelVisible(String label) {
        if (shows(label)) {
            return true;
        }
        if ("Full Name".equals(label) && (shows("Full name") || (onPersonal() && fieldByHint("Name") != null))) {
            return true;
        }
        return false;
    }

    public boolean errorShown() {
        return systemErrorMessage() != null;
    }

    /** App/backend failure, not a field validation such as "is required". */
    public String systemErrorMessage() {
        String src = text();
        String lower = src.toLowerCase();
        String[] needles = {
                "something went wrong",
                "unexpected error",
                "unable to",
                "could not",
                "couldn't",
                "try again later",
                "server error",
                "internal error",
                "failed to load",
                "network error",
                "please try again"
        };
        for (String needle : needles) {
            if (lower.contains(needle) && !lower.contains("please enter") && !lower.contains("is required")) {
                return needle;
            }
        }
        return null;
    }

    public boolean datePickerOpen() {
        String src = lastDump.isBlank() ? text() : lastDump;
        return src.contains("SELECT DATE")
                || src.contains("Switch to input")
                || src.contains("Select year")
                || src.contains("Previous month");
    }

    public void dismissOverlays() {
        if (shows("Please Wait")) {
            waitUntil(() -> {
                invalidateDump();
                return !shows("Please Wait");
            }, Duration.ofSeconds(8));
        }
        for (int i = 0; i < 4 && datePickerOpen(); i++) {
            confirmDatePicker();
        }
        String src = text();
        if (src.contains("Date of Birth is required") || src.contains("Date of birth is required")) {
            fillDateOfBirth();
            dismissOverlays();
        }
    }

    public boolean onPersonal() {
        String src = text();
        if (FunctionalModuleReset.onProfileTree(src)) {
            return false;
        }
        return src.contains("Personal Details")
                && (src.contains("Full Name") || src.contains("Male") || src.contains("Pincode"));
    }

    public boolean onIncome() {
        if (onAddLoan()) {
            return false;
        }
        if (datePickerOpen()) {
            dismissOverlays();
        }
        if (datePickerOpen()) {
            return false;
        }
        boolean title = shows("Income & Expense") || shows("Income and Expense")
                || shows("Income & Expense details");
        boolean body = shows("Net Monthly Income") || shows("My Loans") || shows("running Loan")
                || shows("LifeStyle") || shows("Lifestyle") || shows(loanType())
                || shows("Education Loan");
        if (!title && shows("Income") && body) {
            title = true;
        }
        if (title && !body && shows(loanType())) {
            scrollTo(loanType());
            body = shows(loanType());
        }
        return title && body;
    }

    public boolean runningLoanQuestionDisplayed() {
        String src = text();
        return src.contains("running Loan") || src.contains("running loan")
                || src.contains("Do you have any running");
    }

    /** Yes-path Add Loan CTA on Income — not on the Add Loan Details sub-flow. */
    public boolean addLoanActionVisible() {
        if (onAddLoan()) {
            return false;
        }
        if (!onIncome()) {
            return findExact("Add Loan") != null;
        }
        if (runningLoanAnswerIs("No")) {
            return false;
        }
        if (!shows("Add Loan")) {
            return false;
        }
        if (runningLoanAnswerIs("Yes")) {
            return true;
        }
        // Running-loan row is often off-screen (no scroll); Yes exposes My Loans + Add Loan together.
        if (shows("My Loans")) {
            return true;
        }
        return addLoanNearRunningLoanQuestion();
    }

    private boolean runningLoanAnswerIs(String choice) {
        return choiceChecked("running Loan", choice)
                || choiceChecked("Do you have any running", choice);
    }

    /** Flutter often omits radio {@code checked}; infer No when the Yes-path CTA is off. */
    public boolean runningLoanNoSelected() {
        if (!onIncome() && !runningLoanQuestionDisplayed()) {
            return false;
        }
        if (addLoanActionVisible()) {
            return false;
        }
        if (runningLoanAnswerIs("No")) {
            return true;
        }
        return !runningLoanAnswerIs("Yes");
    }

    private boolean runningLoanCtaActive() {
        return addLoanActionVisible();
    }

    /** Older layout: Add Loan sits between the running-loan row and My Loans. */
    private boolean addLoanNearRunningLoanQuestion() {
        String src = pageSource();
        if (!src.contains("Add Loan")) {
            return false;
        }
        int qTop = runningLoanAnchorY();
        if (qTop < 0) {
            return false;
        }
        Pattern addLoan = Pattern.compile(
                "content-desc=\"Add Loan\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"",
                Pattern.CASE_INSENSITIVE);
        Matcher m = addLoan.matcher(src);
        while (m.find()) {
            int btnY = (Integer.parseInt(m.group(2)) + Integer.parseInt(m.group(4))) / 2;
            if (btnY >= qTop - 24 && btnY <= qTop + 360) {
                return true;
            }
        }
        return false;
    }

    public boolean onAddLoan() {
        boolean title = shows("Add Goal Details") || shows("Add Loan Details") || shows("Add Loan");
        return title && shows("Loan Type") && shows("Principal");
    }

    public boolean onInsurance() {
        return shows("Insurance Details") && shows("Do you have any Insurance");
    }

    public boolean onSavings() {
        return shows("Savings") && shows("Investment");
    }

    public boolean onAddInvestment() {
        String src = text();
        return src.contains("Investment Type")
                || src.contains("Add Investment Details")
                || (src.contains("Current Value") && src.contains("associate a goal"));
    }

    public boolean investmentTypeSelected() {
        String src = text();
        if (investmentDropdownOpen(src)) {
            return false;
        }
        return src.contains("Public Provident Fund") || src.contains("(PPF)");
    }

    /** Field validation on the current page. Logged, does not stop the suite. */
    public String fieldErrorMessage() {
        String src = text();
        String lower = src.toLowerCase();
        String[] needles = {
                "is required", "please enter", "please select",
                "cannot be empty", "mandatory", "enter a valid"
        };
        for (String needle : needles) {
            if (lower.contains(needle)) {
                return needle + " | " + visibleLabels();
            }
        }
        return null;
    }

    public void storeFieldErrors(String step) {
        String error = fieldErrorMessage();
        if (error != null) {
            FhcErrorLog.record(step, "VALIDATION", error);
        }
    }

    /**
     * When the app shows inline validation (Please enter… / is required), fill from the FHC Excel
     * defaults for the running test case. Safe to call before Next and in page assertions.
     */
    public void resolveVisibleValidationErrors() {
        for (int round = 0; round < MoneyFunctionalFast.rounds(context, 5); round++) {
            String src = text();
            boolean pending = hasFieldValidation(src)
                    || (onIncome() && lifestyleNeedsFill(lifestyleExpense()));
            if (!pending) {
                return;
            }
            boolean acted = applyValidationFixes(src);
            storeFieldErrors("resolveValidation:" + round);
            if (!acted) {
                return;
            }
            invalidateDump();
            fSleep(350);
        }
    }

    private boolean hasFieldValidation(String src) {
        if (src == null || src.isBlank()) {
            return false;
        }
        String lower = src.toLowerCase();
        return lower.contains("please enter") || lower.contains("please select")
                || lower.contains("is required") || lower.contains("cannot be empty")
                || lower.contains("mandatory") || lower.contains("enter a valid");
    }

    /** Map visible validation text to sheet-backed field fillers on the current FHC page. */
    private boolean applyValidationFixes(String src) {
        boolean any = false;
        String lower = src.toLowerCase();

        if (lower.contains("date of birth") && lower.contains("required")) {
            fillDateOfBirth();
            any = true;
        }
        if (onPersonal() || lower.contains("personal details")) {
            if (lower.contains("full name") || lower.contains("name is required")) {
                sanitizeFullName();
                any = true;
            }
            if (lower.contains("pincode") || (!shows(city()) && shows("Pincode"))) {
                fillPincode();
                any = true;
            }
            if (lower.contains("dependant") || lower.contains("dependent")) {
                setDependantCountOne();
                fillEmptyDependantDobs();
                any = true;
            }
        }
        if (onIncome() || lower.contains("income & expense") || lower.contains("income and expense")) {
            if (!income().equals(fieldDigits("Income"))
                    || lower.contains("net monthly income") || lower.contains("monthly income")) {
                replaceHint("Net Monthly Income", income());
                any = true;
            }
            if (!expense().equals(fieldDigits("Household"))
                    || lower.contains("household") || lower.contains("monthly expense")) {
                replaceHint("Household", expense());
                any = true;
            }
            if (lifestyleNeedsFill(lifestyleExpense()) || lower.contains("lifestyle")) {
                ensureLifestyleExpense();
                any = true;
            }
            if (addLoanActionVisible() && lower.contains("running loan")
                    && (lower.contains("please select") || lower.contains("is required"))) {
                selectLoanChoice("No");
                any = true;
            }
        }
        if (onAddLoan() || lower.contains("add loan")) {
            if (!loanTypeSelected(loanType()) || lower.contains("loan type")) {
                selectLoanType(loanType());
                any = true;
            }
            if (lower.contains("principal") || !principal().equals(fieldDigits("Principal"))) {
                enterPrincipal();
                any = true;
            }
            if (lower.contains("emi") || !emi().equals(fieldDigits("EMI"))) {
                enterEmi();
                any = true;
            }
            if (lower.contains("interest") || !rate().equals(fieldDigits("Interest"))) {
                enterRate();
                any = true;
            }
        }
        if (onAddInvestment() || onSavings() || lower.contains("investment")) {
            if (!investmentTypeSelected() || lower.contains("investment type")) {
                chooseInvestmentType();
                any = true;
            }
            if (lower.contains("current value")) {
                enterCurrentValue();
                any = true;
            }
            if (lower.contains("bank balance")) {
                enterBankBalance();
                any = true;
            }
        }
        return any;
    }

    public boolean onRiskIntro() {
        return shows("Lets do risk profile") && shows("I already know");
    }

    public boolean onQuestion(String number) {
        return shows("Question " + number + "/5");
    }

    public boolean onRiskResult() {
        invalidateDump();
        String src = text();
        return src.contains("Your Risk Profile") || src.contains("YOUR RISK PROFILE")
                || (src.contains("Risk Profile") && src.contains("Next") && !src.contains("Question "));
    }

    public boolean onCapability() {
        invalidateDump();
        String src = text();
        return src.contains("Let us know your risk") && src.contains("capability");
    }

    public boolean onSummary() {
        invalidateDump();
        return shows("Summary Details");
    }

    public boolean onScore() {
        invalidateDump();
        return shows("OUT OF 100") && shows("YOUR FINANCIAL HEALTH SCORE");
    }

    public boolean onDiagnosis() {
        invalidateDump();
        return shows("Your Money Diagnosed");
    }

    public void openFinsight() {
        if (finsight.isDashboardHome()) {
            return;
        }
        String src = text();
        if (FunctionalModuleReset.onProfileTree(src)) {
            FunctionalModuleReset.escapeAccidentalProfile(context);
        }
        for (int i = 0; i < 3 && nestedFhcForm(text()) && !finsight.isDashboardHome(); i++) {
            if (!goBack()) {
                break;
            }
        }
        if (finsight.isDashboardHome() || finsight.isDisplayed()) {
            return;
        }
        finsight.openFinSightTab();
        home.dismissHomeCoachIfShown();
        waitUntil(() -> finsight.isDashboardHome() || finsight.isDisplayed(),
                Duration.ofSeconds(3));
    }

    private boolean nestedFhcForm(String src) {
        return src.contains("Personal Details")
                || src.contains("Income & Expense")
                || src.contains("Income and Expense")
                || src.contains("Insurance Details")
                || src.contains("Add Loan Details")
                || src.contains("Add Investment")
                || src.contains("Lets do risk profile")
                || src.contains("Your Risk Profile")
                || src.contains("Let us know your risk")
                || src.contains("Summary Details")
                || src.contains("YOUR FINANCIAL HEALTH SCORE")
                || src.contains("Your Money Diagnosed")
                || (src.contains("Question ") && src.contains("/5"));
    }

    public boolean entryButtonDisplayed() {
        openFinsight();
        scrollTo("Financial Health");
        return labelled("Continue") != null
                || labelled("Start Now") != null
                || labelled("Let's start") != null
                || labelled("Let's Start") != null
                || labelled("Reassess") != null
                || labelled("View Details") != null;
    }

    public void openPersonalDetails() {
        if (onPersonal()) {
            return;
        }
        if (FunctionalModuleReset.onProfileTree(text())) {
            FunctionalModuleReset.escapeAccidentalProfile(context);
        }
        if (onPersonal()) {
            return;
        }
        if (nestedFhcForm(text()) && !onPersonal()) {
            for (int i = 0; i < 3 && !onPersonal() && nestedFhcForm(text()); i++) {
                if (!goBack()) {
                    break;
                }
            }
            if (onPersonal()) {
                return;
            }
        }
        openFinsight();
        scrollTo("Financial Health");
        pressFirst("Continue", "Start Now", "Let's start", "Let's Start", "Reassess");
        waitUntil(this::onPersonal, Duration.ofSeconds(3));
        if (!onPersonal() && (onScore() || shows("See the diagnosis") || shows("View Details"))) {
            pressFirst("Reassess", "Let's start", "Start Now", "Continue");
            waitUntil(this::onPersonal, Duration.ofSeconds(3));
        }
    }

    public void fillPersonalDetails() {
        openPersonalDetails();
        if (!onPersonal()) {
            return;
        }
        dismissOverlays();
        sanitizeFullName();
        pressFirst("Male");
        fillDateOfBirth();
        dismissOverlays();
        setDependantCountOne();
        fillEmptyDependantDobs();
        fillDependantDobFromDump();
        dismissOverlays();
        fillPincode();
        dismissKeyboard();
        dismissOverlays();
        resolveVisibleValidationErrors();
    }

    /** KYC sometimes prefills N.A or initials with a dot; the app rejects "." in Full Name. */
    private void sanitizeFullName() {
        reveal("Full Name");
        String current = currentFullName();
        String cleaned = MoneyTestData.lettersOnlyName(current);
        if (cleaned.isBlank()) {
            cleaned = MoneyTestData.name(context.config());
        }
        if (cleaned.isBlank()) {
            return;
        }
        if (cleaned.equals(current) && !current.contains(".") && !current.toUpperCase().contains("N.A")
                && !current.toUpperCase().matches(".*\\bNA\\b.*")) {
            return;
        }
        LOG.info("FHC Full Name cleaned | {} -> {}", current, cleaned);
        WebElement field = fieldByHint("Name");
        if (field != null) {
            replaceField(field, cleaned.replace(" ", "%s"));
        } else {
            pressFirst("Full Name");
            AdbCommands.shell(context.config().get("android.udid", ""), "input", "text",
                    cleaned.replace(" ", "%s"));
            dismissKeyboard();
        }
    }

    private String currentFullName() {
        WebElement field = fieldByHint("Name");
        if (field != null) {
            String value = String.valueOf(field.getAttribute("text")).trim();
            if (!value.isBlank() && !"null".equalsIgnoreCase(value) && !"Full Name".equalsIgnoreCase(value)
                    && !"Name".equalsIgnoreCase(value)) {
                return value;
            }
        }
        return "";
    }

    /** Live form now blocks Next with "Date of birth is required". */
    private void fillDateOfBirth() {
        if (userDobAlreadyFilled()) {
            return;
        }
        reveal("Date of Birth");
        pressFirst("Date of Birth", "Date of birth");
        fSleep(700);
        String dob = context.config().get("money.test.dob", "16-MAY-1994");
        if (datePickerOpen() && dob.contains("1994")) {
            if (shows("Select year") || shows("January 2000") || text().contains("January 2000")) {
                pressFirst("Select year");
                fSleep(400);
            }
            pressFirst("1994", "May", "MAY", "16");
        }
        confirmDatePicker();
        if (datePickerOpen()) {
            confirmDatePicker();
        }
    }

    private boolean userDobAlreadyFilled() {
        if (shows("Date of birth is required") || shows("Date of Birth is required")) {
            return false;
        }
        String src = text();
        return src.contains("1990") || src.contains("1994") || src.contains("198")
                || Pattern.compile("\\d{1,2} [A-Z][a-z]{2} 19\\d{2}").matcher(src).find();
    }

    /** 3 dependants open extra DOB fields; 0 is invalid and blocks Next. Keep 1. */
    private void setDependantCountOne() {
        String src = text();
        if (!src.contains("Dependant")) {
            return;
        }
        boolean extras = src.contains("Dependant 2") || src.contains("Dependant 3");
        boolean zero = src.contains("Parents) 0") || src.contains("Parents) 3")
                || src.contains("Dependant (Spouse/ Kids/ Parents) 0")
                || src.contains("Dependant (Spouse/ Kids/ Parents) 3");
        if (!extras && src.contains("Parents) 1")) {
            return;
        }
        if (!extras && !zero) {
            return;
        }
        reveal("Dependant");
        WebElement dropdown = findContains("Dependant (Spouse");
        if (dropdown != null) {
            pressElement(dropdown);
        } else {
            pressNear("Dependant", "3");
            pressNear("Dependant", "0");
        }
        fSleep(500);
        pressFirst("1");
        fSleep(800);
    }

    /** Flutter Pincode is often an unlabelled EditText below the fold, not a hint field. */
    private void fillPincode() {
        reveal("Pincode");
        if (shows(city()) && !fieldDigits("Pincode").isEmpty()) {
            return;
        }
        WebElement field = fieldByHint("Pincode");
        if (field == null) {
            java.util.List<WebElement> edits = findElementsTimed(
                    MobileWaits.xpath("//android.widget.EditText"), 400);
            if (edits.size() >= 2) {
                field = edits.get(edits.size() - 1);
            } else if (!edits.isEmpty()) {
                field = edits.get(0);
            }
        }
        if (field != null) {
            replaceField(field, pincode());
        } else {
            pressFirst("Pincode");
            tapDescriptionBounds("Pincode");
            AdbCommands.shell(context.config().get("android.udid", ""), "input", "text", pincode());
            dismissKeyboard();
        }
        pause(800);
        for (int i = 0; i < 6 && !shows(city()) && !shows("Saharanpur"); i++) {
            pause(250);
            invalidateDump();
        }
    }

    /** Empty Dependant Date of Birth (red placeholder) keeps the user on Personal Details. */
    private void fillEmptyDependantDobs() {
        if (!shows("Dependant 1") && !shows("Dependant 2") && !shows("Dependant 3")) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            WebElement empty = emptyDobInput();
            if (empty == null) {
                return;
            }
            pressElement(empty);
            fSleep(700);
            confirmDatePicker();
        }
    }

    private WebElement emptyDobInput() {
        try {
            WebElement best = null;
            int bestY = 400;
            for (WebElement el : findElementsTimed(MobileWaits.xpath(
                    "//*[@content-desc='Date of Birth' or @text='Date of Birth']"), 400)) {
                try {
                    var rect = el.getRect();
                    if (rect.getY() > bestY && rect.getHeight() >= 48 && rect.getWidth() > 200) {
                        best = el;
                        bestY = rect.getY();
                    }
                } catch (Exception ignored) {
                    // stale
                }
            }
            return best;
        } catch (Exception e) {
            return null;
        }
    }

    private void fillDependantDobFromDump() {
        if (!text().contains("Dependant 1") && !lastDump.contains("Dependant 1")) {
            return;
        }
        Matcher matcher = Pattern.compile(
                "content-desc=\"Date of Birth\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"")
                .matcher(lastDump.isBlank() ? text() : lastDump);
        int bestY = 400;
        int x = 0;
        int y = 0;
        while (matcher.find()) {
            int cy = (Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4))) / 2;
            if (cy > bestY) {
                bestY = cy;
                x = (Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(3))) / 2;
                y = cy;
            }
        }
        if (y > 400) {
            LOG.info("FHC tap dependant DOB at {},{}", x, y);
            shellPress(x, y);
            pause(400);
            confirmDatePicker();
        }
    }

    private void leavePersonalWithNext() {
        for (int i = 0; i < 2 && onPersonal(); i++) {
            if (!shows(city()) && !shows("Saharanpur")) {
                fillPincode();
            }
            tapNext();
            tapBottomNext();
            pause(300);
            dismissOverlays();
            invalidateDump();
        }
    }

    private void tapBottomNext() {
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        shellPress(size[0] / 2, (int) (size[1] * 0.92));
    }

    private void confirmDatePicker() {
        dismissKeyboard();
        pressFirst("OK", "Ok", "DONE", "Done", "SET", "Set", "Confirm", "Save");
        pause(250);
        if (!datePickerOpen()) {
            invalidateDump();
            return;
        }
        tapFirst(Duration.ofMillis(600),
                MobileWaits.xpath("//*[@content-desc='OK' or @text='OK']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='OK']"),
                MobileWaits.xpath("//android.widget.Button[@text='OK']"));
        pause(300);
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        shellPress((int) (size[0] * 0.88), (int) (size[1] * 0.92));
        pause(200);
        shellPress((int) (size[0] * 0.81), (int) (size[1] * 0.62));
        pause(200);
        shellPress((int) (size[0] * 0.81), (int) (size[1] * 0.72));
        pause(250);
        if (datePickerOpen()) {
            pressSystemBack();
            pause(350);
        }
        invalidateDump();
        fSleep(400);
    }

    public void openIncome() {
        dismissOverlays();
        if (onIncome()) {
            return;
        }
        if (pastIncome()) {
            rewindToIncome();
            if (onIncome()) {
                return;
            }
        }
        if (!onPersonal()) {
            openPersonalDetails();
        }
        if (onIncome()) {
            return;
        }
        if (!onPersonal()) {
            return;
        }
        fillPersonalDetails();
        if (onIncome()) {
            return;
        }
        for (int i = 0; i < 3 && !onIncome() && onPersonal(); i++) {
            tapNext();
            invalidateDump();
        }
    }

    /** Sheet cases assume a target page; session may already be on Insurance or later. */
    private void rewindToIncome() {
        for (int i = 0; i < 12 && !onIncome(); i++) {
            if (onPersonal()) {
                fillPersonalDetails();
                tapNext();
                invalidateDump();
                if (onIncome()) {
                    return;
                }
                continue;
            }
            if (goBack()) {
                invalidateDump();
                dismissOverlays();
                continue;
            }
            home.pressSystemBack();
            invalidateDump();
            dismissOverlays();
        }
    }

    /** Later FHC pages — do not refill Personal Details for minutes trying to reach Income. */
    private boolean pastIncome() {
        return onInsurance()
                || onSavings()
                || onDiagnosis()
                || onScore()
                || onRiskResult()
                || onCapability()
                || onSummary()
                || shows("Lets do risk profile")
                || shows("Let us know your risk")
                || (shows("Question ") && shows("/5"));
    }

    public void fillIncome() {
        openIncome();
        replaceHint("Net Monthly Income", income());
        replaceHint("Household", expense());
        ensureLifestyleExpense();
    }

    public void ensureLifestyleExpense() {
        if (!onIncome() && !shows("LifeStyle") && !shows("Lifestyle") && !shows("lifeStyle")
                && !shows("Monthly Lifestyle")) {
            return;
        }
        String target = lifestyleExpense();
        if (!lifestyleNeedsFill(target)) {
            return;
        }
        for (String alias : lifestyleAliases()) {
            replaceHint(alias, target);
            if (!lifestyleNeedsFill(target)) {
                invalidateDump();
                return;
            }
        }
        tapDescriptionBounds("Monthly Lifestyle");
        pressFirst("Monthly Lifestyle", "LifeStyle", "Lifestyle expenses");
        fSleep(300);
        WebElement field = lifestyleEditField();
        if (field != null) {
            replaceField(field, target);
        }
        fillLifestyleViaRowBounds(target);
        if (lifestyleNeedsFill(target)) {
            shellTypeIntoFocusedField(target);
        }
        invalidateDump();
        dismissKeyboard();
    }

    /** Income & Expense: fields are filled via hints/bounds — no scroll. */
    private void scrollIncomeFormToTop() {
        if (onIncome()) {
            return;
        }
    }

    private static String[] lifestyleAliases() {
        return new String[]{
                "Monthly Lifestyle expenses",
                "Monthly Lifestyle",
                "LifeStyle",
                "Lifestyle",
                "lifeStyle",
                "Lifestyle expenses"
        };
    }

    private void fillLifestyleViaRowBounds(String target) {
        if (!lifestyleNeedsFill(target)) {
            return;
        }
        String src = pageSource();
        Pattern[] patterns = {
                Pattern.compile("content-desc=\"([^\"]*Monthly Lifestyle[^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"",
                        Pattern.CASE_INSENSITIVE),
                Pattern.compile("content-desc=\"([^\"]*lifeStyle[^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"",
                        Pattern.CASE_INSENSITIVE)
        };
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(src);
            while (matcher.find() && lifestyleNeedsFill(target)) {
                int x1 = Integer.parseInt(matcher.group(2));
                int y1 = Integer.parseInt(matcher.group(3));
                int x2 = Integer.parseInt(matcher.group(4));
                int y2 = Integer.parseInt(matcher.group(5));
                int tapX = x1 + Math.max(24, (x2 - x1) * 7 / 10);
                int tapY = (y1 + y2) / 2;
                shellPress(tapX, tapY);
                fSleep(350);
                shellTypeIntoFocusedField(target);
                invalidateDump();
            }
        }
    }

    private void shellTypeIntoFocusedField(String value) {
        String udid = context.config().get("android.udid", "");
        AdbCommands.shell(udid, "input", "keyevent", "123");
        for (int i = 0; i < 14; i++) {
            AdbCommands.shell(udid, "input", "keyevent", "67");
        }
        AdbCommands.shell(udid, "input", "text", value);
        fSleep(250);
        dismissKeyboard();
    }

    private boolean lifestyleNeedsFill(String target) {
        String src = text();
        if (src.contains("Please enter your lifeStyle") || src.contains("Please enter your lifestyle")) {
            return true;
        }
        if (src.contains(target) || src.contains(withCommas(target))) {
            return false;
        }
        return !target.equals(lifestyleFieldDigits());
    }

    private String lifestyleFieldDigits() {
        String digits = fieldDigits("LifeStyle");
        if (!digits.isBlank()) {
            return digits;
        }
        digits = fieldDigits("Lifestyle");
        if (!digits.isBlank()) {
            return digits;
        }
        WebElement field = lifestyleEditField();
        if (field == null) {
            return "";
        }
        String value = String.valueOf(field.getAttribute("text"));
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    private WebElement lifestyleEditField() {
        for (WebElement el : findElementsTimed(MobileWaits.xpath("//android.widget.EditText"), 600)) {
            try {
                String combined = (String.valueOf(el.getAttribute("hint")) + " "
                        + String.valueOf(el.getAttribute("content-desc"))).toLowerCase();
                if (combined.contains("lifestyle") || combined.contains("lifestyle expense")) {
                    return el;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        java.util.List<WebElement> edits = findElementsTimed(MobileWaits.xpath("//android.widget.EditText"), 600);
        if (edits.size() >= 3) {
            return edits.get(2);
        }
        return null;
    }

    /** Cucumber step: one focused Next tap (assertions use {@link #advanceFromIncomeToInsurance()} if needed). */
    public void tapNextOnIncomeExpense() {
        if (!onIncome()) {
            openIncome();
        }
        dismissKeyboard();
        // A saved loan keeps Add Loan on screen. TC_003 covers that No does not clear it.
        // Later cases only need Next, so do not spend the step retrying No.
        if (fieldErrorMessage() != null || lifestyleNeedsFill(lifestyleExpense())) {
            resolveVisibleValidationErrors();
            ensureLifestyleExpense();
        }
        tapNextOnIncome();
    }

    /** Fill lifestyle and tap Next until Insurance opens (validation blocks navigation). */
    public boolean advanceFromIncomeToInsurance() {
        if (!onIncome()) {
            openIncome();
            ensureIncomeAmounts();
        }
        tapNextOnIncomeExpense();
        if (onInsurance()) {
            return true;
        }
        int retries = Math.max(2, MoneyFunctionalFast.rounds(context, 4));
        for (int i = 0; i < retries; i++) {
            if (onInsurance()) {
                return true;
            }
            if (fieldErrorMessage() != null || lifestyleNeedsFill(lifestyleExpense())) {
                resolveVisibleValidationErrors();
                ensureLifestyleExpense();
            }
            tapNextOnIncome();
            if (onInsurance()) {
                return true;
            }
            fSleep(200);
        }
        return onInsurance();
    }

    private void tapNextOnIncome() {
        if (datePickerOpen()) {
            dismissOverlays();
        }
        dismissKeyboard();
        String before = screenFingerprint();
        Duration navWait = incomeUiWait();
        if (findExact("Next") != null || shows("Next")) {
            if (!tapDescriptionBounds("Next")) {
                pressFirst("Next");
            }
        } else {
            tapBottomNext();
        }
        invalidateDump();
        if (!waitUntilChanged(before, navWait) && onIncome()) {
            tapBottomNext();
            invalidateDump();
            waitUntilChanged(before, navWait);
        }
    }

    public void selectLoanChoice(String choice) {
        if (!onIncome()) {
            openIncome();
        } else {
            dismissKeyboard();
        }
        for (int attempt = 0; attempt < 2; attempt++) {
            tapRunningLoanChoice(choice);
            invalidateDump();
            if ("No".equalsIgnoreCase(choice)
                    && waitUntilTrue(this::runningLoanNoSelected, 1_000)) {
                return;
            }
            if ("Yes".equalsIgnoreCase(choice)
                    && waitUntil(() -> addLoanActionVisible() || choiceChecked("running Loan", "Yes"),
                    incomeUiWait())) {
                return;
            }
        }
        if ("No".equalsIgnoreCase(choice) && runningLoanCtaActive()) {
            LOG.warn("FHC running loan No may not be set; visible: {}", visibleLabels());
        }
    }

    /** Income & Expense: use dump bounds for Yes/No — no scroll. */
    private void nudgeIncomeToRunningLoan() {
        invalidateDump();
    }

    private int runningLoanAnchorY() {
        int qTop = boundsTopFromDump("running Loan");
        if (qTop < 0) {
            qTop = boundsTopFromDump("running loan");
        }
        if (qTop < 0) {
            qTop = boundsTopFromDump("Do you have any running");
        }
        if (qTop >= 0) {
            return qTop;
        }
        int lifestyle = boundsTopFromDump("Monthly Lifestyle");
        if (lifestyle < 0) {
            lifestyle = boundsTopFromDump("Lifestyle");
        }
        if (lifestyle >= 0) {
            return lifestyle + 100;
        }
        return -1;
    }

    private void tapRunningLoanChoice(String choice) {
        if (tapLoanChoiceOnRow(choice)) {
            return;
        }
        pressNear("running Loan", choice);
        if (!choiceChecked("running Loan", choice) && !choiceChecked("Do you have any running", choice)) {
            pressNear("Do you have any running", choice);
        }
        if (!choiceChecked("running Loan", choice) && !choiceChecked("Do you have any running", choice)) {
            tapDescriptionBounds(choice);
            WebElement anchor = findContains("running Loan");
            if (anchor == null) {
                anchor = findContains("running loan");
            }
            if (anchor != null) {
                WebElement checkable = nearestCheckable(nearest("running Loan", choice) != null
                        ? nearest("running Loan", choice)
                        : anchor);
                if (checkable != null) {
                    pressElement(checkable);
                }
            }
        }
    }

    private int boundsTopFromDump(String labelPart) {
        Matcher m = Pattern.compile(
                "content-desc=\"[^\"]*" + Pattern.quote(labelPart) + "[^\"]*\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"",
                Pattern.CASE_INSENSITIVE).matcher(pageSource());
        if (!m.find()) {
            return -1;
        }
        return Integer.parseInt(m.group(2));
    }

    private boolean tapLoanChoiceOnRow(String choice) {
        String src = pageSource();
        int qTop = runningLoanAnchorY();
        Pattern node = Pattern.compile(
                "(?:content-desc|text)=\"(" + Pattern.quote(choice) + ")\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"");
        Matcher m = node.matcher(src);
        int bestX = -1;
        int[] bestTap = null;
        while (m.find()) {
            int yMid = (Integer.parseInt(m.group(3)) + Integer.parseInt(m.group(5))) / 2;
            if (qTop >= 0 && (yMid < qTop - 48 || yMid > qTop + 260)) {
                continue;
            }
            int x2 = Integer.parseInt(m.group(4));
            if (x2 > bestX) {
                bestX = x2;
                bestTap = new int[]{
                        (Integer.parseInt(m.group(2)) + x2) / 2,
                        yMid
                };
            }
        }
        if (bestTap != null) {
            LOG.info("FHC ADB tap running loan {} at {},{}", choice, bestTap[0], bestTap[1]);
            shellPress(bestTap[0], bestTap[1]);
            invalidateDump();
            return true;
        }
        return false;
    }

    public void ensureIncomeAmounts() {
        if (!onIncome()) {
            openIncome();
        }
        boolean incomeOk = income().equals(fieldDigits("Income"));
        boolean expenseOk = expense().equals(fieldDigits("Household"));
        boolean lifestyleOk = !lifestyleNeedsFill(lifestyleExpense());
        if (incomeOk && expenseOk && lifestyleOk) {
            return;
        }
        if (!incomeOk) {
            replaceHint("Net Monthly Income", income());
        }
        if (!expenseOk) {
            replaceHint("Household", expense());
        }
        if (!lifestyleOk) {
            ensureLifestyleExpense();
        }
    }

    public void openAddLoan() {
        if (onAddLoan()) {
            return;
        }
        if (!onIncome()) {
            openIncome();
        }
        dismissKeyboard();
        if (!runningLoanCtaActive()) {
            ensureRunningLoanYesSelected();
        }
        if (!tapRunningLoanAddButton()) {
            tapAddLoanCtaWithNudge();
        }
        waitUntil(this::onAddLoan, Duration.ofSeconds(MoneyFunctionalFast.enabled(context) ? 6 : 10));
    }

    private void ensureRunningLoanYesSelected() {
        nudgeIncomeToRunningLoan();
        tapLoanChoiceOnRow("Yes");
        fSleep(300);
        invalidateDump();
        if (!runningLoanCtaActive()) {
            pressNear("running Loan", "Yes");
            pressNear("Do you have any running", "Yes");
            fSleep(300);
            invalidateDump();
        }
    }

    private boolean tapRunningLoanAddButton() {
        if (tapDescriptionBounds("Add Loan")) {
            fSleep(400);
            invalidateDump();
            return true;
        }
        if (pressLoanOption("Add Loan")) {
            fSleep(400);
            invalidateDump();
            return true;
        }
        return false;
    }

    private void tapAddLoanCtaWithNudge() {
        if (tapRunningLoanAddButton()) {
            return;
        }
        pressFirst("Add Loan");
        waitAfterTap();
    }

    /** Income page with Yes already selected — open Add Loan Details only. */
    public void tapAddLoanOnIncome() {
        if (onAddLoan()) {
            return;
        }
        if (!onIncome()) {
            openAddLoan();
            return;
        }
        dismissKeyboard();
        if (!tapRunningLoanAddButton()) {
            tapAddLoanCtaWithNudge();
        }
        waitUntil(this::onAddLoan, Duration.ofSeconds(MoneyFunctionalFast.enabled(context) ? 6 : 10));
    }

    public void openLoanTypePicker() {
        openAddLoan();
        openLoanTypeDropdown();
    }

    /** Opens Loan Type picker only — options are tapped from the list, not typed into a search field. */
    public void searchLoanType(String word) {
        openLoanTypePicker();
    }

    public void selectLoanType(String loanType) {
        if (!onAddLoan()) {
            openAddLoan();
        }
        if (!onAddLoan()) {
            return;
        }
        if (loanTypeSelected(loanType)) {
            return;
        }
        dismissKeyboard();
        for (int round = 0; round < 2 && !loanTypeSelected(loanType); round++) {
            openLoanTypeDropdown();
            if (!loanTypePickerOpen(text())) {
                continue;
            }
            tapLoanTypeOption(loanType);
            dismissKeyboard();
            invalidateDump();
            waitUntilTrue(() -> loanTypeSelected(loanType) || !loanTypePickerOpen(text()), 2_000);
        }
        if (!loanTypeSelected(loanType)) {
            FhcErrorLog.record("select loan type", "ASSERT",
                    loanType + " was not selected. Visible: " + visibleLabels());
        }
        storeFieldErrors("select loan type");
    }

    private boolean tapLoanTypeOption(String loanType) {
        if (loanType == null || loanType.isBlank()) {
            return false;
        }
        LOG.info("FHC tap loan type {}", loanType);
        if (pickLoanTypeOptionFast(loanType)) {
            return true;
        }
        if (loanTypePickerOpen(text())) {
            swipeUp();
            invalidateDump();
            return pickLoanTypeOptionFast(loanType);
        }
        return false;
    }

    public boolean showsLoanCard(String loanType) {
        dismissOverlays();
        if (!onIncome()) {
            return false;
        }
        invalidateDump();
        return revealLoanCardInCarousel(loanType);
    }

    private void openLoanTypeDropdown() {
        if (loanTypePickerOpen(text())) {
            return;
        }
        dismissKeyboard();
        LOG.info("FHC open Loan Type dropdown");
        tapLoanTypeField();
        waitUntilTrue(() -> loanTypePickerOpen(text()), 2_500);
        if (loanTypePickerOpen(text())) {
            return;
        }
        tapDescriptionBounds("Loan Type");
        waitUntilTrue(() -> loanTypePickerOpen(text()), 2_500);
    }

    /** Tap the Loan Type row (not the list item) so the picker opens — same pattern as Investment Type. */
    private boolean tapLoanTypeField() {
        if (tapDescriptionBounds("Select Loan Type")) {
            return true;
        }
        String src = pageSource();
        Matcher row = Pattern.compile(
                "content-desc=\"([^\"]*Loan Type[^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"",
                Pattern.CASE_INSENSITIVE).matcher(src);
        int bestY = -1;
        int[] tap = null;
        while (row.find()) {
            int y1 = Integer.parseInt(row.group(3));
            if (y1 > bestY) {
                bestY = y1;
                tap = new int[]{
                        (Integer.parseInt(row.group(2)) + Integer.parseInt(row.group(4))) / 2,
                        (y1 + Integer.parseInt(row.group(5))) / 2
                };
            }
        }
        if (tap != null) {
            LOG.info("FHC ADB tap Loan Type field at {},{}", tap[0], tap[1]);
            shellPress(tap[0], tap[1]);
            invalidateDump();
            return true;
        }
        return tapDescriptionBounds("Loan Type");
    }

    private boolean pickLoanTypeOptionFast(String label) {
        return pressLoanOption(label);
    }

    private boolean loanTypeSelected(String expected) {
        if (expected == null || expected.isBlank()) {
            return false;
        }
        String src = text();
        if (loanTypePickerOpen(src)) {
            return false;
        }
        if (!src.contains(expected)) {
            return false;
        }
        if (onAddLoan()) {
            return src.contains("Principal");
        }
        return onIncome();
    }

    private static final String[] LOAN_TYPE_OPTIONS = {
            "Education Loan", "Home Loan", "Personal Loan", "Gold Loan", "Car Loan", "Two Wheeler Loan"
    };

    private boolean loanTypePickerOpen(String src) {
        int visible = 0;
        for (String option : LOAN_TYPE_OPTIONS) {
            if (src.contains(option)) {
                visible++;
            }
        }
        return visible >= 3;
    }

    private boolean pressLoanOption(String label) {
        return pressLabeledOption(label);
    }

    public void enterPrincipal() {
        if (onAddLoan() && !loanTypeSelected(loanType())) {
            selectLoanType(loanType());
        }
        replaceHint("Principal", principal());
    }

    public void enterEmi() {
        replaceHint("EMI", emi());
    }

    public void enterRate() {
        replaceHint("Interest", rate());
    }

    public void tapAdd() {
        if (onAddLoan() && !loanTypeSelected(loanType())) {
            selectLoanType(loanType());
        }
        pressFirst("Add");
        fSleep(700);
        invalidateDump();
        if (onAddInvestment()) {
            for (int i = 0; i < 4 && onAddInvestment(); i++) {
                String before = screenFingerprint();
                pressFirst("Add");
                invalidateDump();
                waitUntilChanged(before, Duration.ofSeconds(2));
            }
            return;
        }
        waitForIncomeAfterLoan();
    }

    /** After Add on loan form, return to Income with the new loan card visible. */
    public boolean waitForIncomeAfterLoan() {
        if (waitUntil(() -> {
            dismissOverlays();
            invalidateDump();
            return onIncome();
        }, Duration.ofSeconds(8))) {
            revealLoanCardInCarousel(loanType());
            return true;
        }
        for (int i = 0; i < 2 && onAddLoan(); i++) {
            pressFirst("Add");
            fSleep(600);
            invalidateDump();
            if (onIncome()) {
                revealLoanCardInCarousel(loanType());
                return true;
            }
        }
        if (onAddLoan()) {
            if (!goBack()) {
                tapUnlabeledTopLeftBackBounds();
            }
            fSleep(500);
            invalidateDump();
        }
        ensureLifestyleExpense();
        return waitUntil(() -> {
            dismissOverlays();
            invalidateDump();
            return onIncome();
        }, Duration.ofSeconds(8));
    }

    public void chooseInvestmentTypePublic() {
        chooseInvestmentType();
    }

    public void enterCurrentValue() {
        if (investmentDropdownOpen() || !investmentTypeSelected()) {
            chooseInvestmentType();
        }
        reveal("Current Value");
        replaceHint("Current Value", currentValue());
        fillEmptyInvestmentAmounts();
        if (!shows(currentValue()) && !shows(withCommas(currentValue()))) {
            pressFirst("Current Value");
            AdbCommands.shell(context.config().get("android.udid", ""), "input", "text", currentValue());
            dismissKeyboard();
        }
        storeFieldErrors("enter current value");
    }

    public void enterBankBalance() {
        replaceHint("Bank Balance", bankBalance());
    }

    public void selectGoal(String choice) {
        if (shows("associate a goal")) {
            pressNear("associate a goal", choice);
            fSleep(400);
        }
    }

    public void chooseCarGoal() {
        if (shows("You don't have any existing goals") || findExact("Car") == null) {
            pressFirst("New Goal");
            fSleep(400);
            scrollTo("Car");
            pressFirst("Car");
            replaceHint("Goal name", "Car");
            replaceHint("Goal amount", currentValue());
        } else {
            pressFirst("Existing Goals");
            fSleep(400);
            pressFirst("Car");
        }
    }

    public void openQuestion(int number) {
        openQuestion1();
        if (number >= 2 && onQuestion("1")) {
            answerAndNext("Above 5 years");
        }
        if (number >= 3 && onQuestion("2")) {
            answerAndNext("No");
        }
        if (number >= 4 && onQuestion("3")) {
            answerAndNext("Balanced returns with some fluctuations");
        }
        if (number >= 5 && onQuestion("4")) {
            answerAndNext("Upto 5% to 10% of my portfolio");
        }
    }

    public boolean headerBackDisplayed() {
        return findExact("Back") != null
                || findContains("Back") != null
                || onRiskIntro()
                || onPersonal()
                || onIncome();
    }

    public void addLoan(String loanType) {
        openAddLoan();
        selectLoanType(loanType);
        enterPrincipal();
        enterEmi();
        enterRate();
        tapAdd();
    }

    public void deleteAddedLoan() {
        openIncome();
        if (!loanTypeOnMyLoansTile(loanType()) && !revealLoanCardInCarousel(loanType())) {
            addLoan(loanType());
        }
        for (int n = 0; n < 3 && loanTypeOnMyLoansTile(loanType()); n++) {
            tapDeleteOnLoanCard(loanType());
            confirmDelete();
            invalidateDump();
        }
    }

    public boolean loanCardGone() {
        if (!onIncome()) {
            openIncome();
        }
        invalidateDump();
        if (!loanTypeOnMyLoansTile(loanType())) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            swipeMyLoansCarouselTowardNext();
            if (!loanTypeOnMyLoansTile(loanType())) {
                return true;
            }
        }
        for (int i = 0; i < 2; i++) {
            swipeMyLoansCarouselTowardPrev();
            if (!loanTypeOnMyLoansTile(loanType())) {
                return true;
            }
        }
        return !loanTypeOnMyLoansTile(loanType());
    }

    public void deleteAddedInvestment() {
        openSavings();
        if (!revealInvestmentCardInCarousel("Provident")) {
            addInvestmentWithoutGoal();
            revealInvestmentCardInCarousel("Provident");
        }
        tapDeleteOnInvestmentCard("Provident");
        confirmDelete();
        waitUntil(this::investmentCardGone, MoneyFunctionalFast.uiWait(context, 4_000));
    }

    public boolean investmentCardGone() {
        return !shows("Public Provident") && !shows("Provident Fund");
    }

    private boolean showsAmount(String digits) {
        return shows(digits) || shows(withCommas(digits));
    }

    private static String withCommas(String digits) {
        String clean = digits.replace(",", "");
        if (clean.length() <= 3) {
            return clean;
        }
        StringBuilder out = new StringBuilder(clean);
        int index = out.length() - 3;
        while (index > 0) {
            out.insert(index, ',');
            index -= 2;
        }
        return out.toString();
    }

    /** Swipe My Loans until the loan title is fully on screen, not a sliver of the next card. */
    private boolean revealLoanCardInCarousel(String loanType) {
        dismissKeyboard();
        for (int pass = 0; pass < 6; pass++) {
            if (loanTitleFullyOnScreen(loanType)) {
                LOG.info("FHC My Loans carousel shows loan title: {}", loanType);
                return true;
            }
            int[] raw = loanTitleBounds(loanType);
            int screenW = AdbInput.screenSize(context.config().get("android.udid", ""))[0];
            if (raw != null && raw[0] < 12 && raw[2] < screenW - 40) {
                swipeMyLoansCarouselTowardPrev();
            } else {
                swipeMyLoansCarouselTowardNext();
            }
        }
        return loanTitleFullyOnScreen(loanType);
    }

    private boolean loanTitleFullyOnScreen(String loanType) {
        int[] b = loanTitleBounds(loanType);
        if (b == null) {
            return false;
        }
        int screenW = AdbInput.screenSize(context.config().get("android.udid", ""))[0];
        return b[0] >= 12 && b[2] <= screenW - 40 && (b[2] - b[0]) >= 48;
    }

    /** Unpadded title bounds of a loan card that overlaps the My Loans row. */
    private int[] loanTitleBounds(String loanType) {
        if (loanType == null || loanType.isBlank() || loanTypePickerOpen(text())) {
            return null;
        }
        int bandTop = myLoansBandTop();
        int bandHeight = myLoansBandHeight();
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        Pattern node = Pattern.compile(
                "content-desc=\"([^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"");
        Matcher m = node.matcher(pageSource());
        int bestScore = Integer.MIN_VALUE;
        int[] best = null;
        while (m.find()) {
            if (!isCreatedLoanTitle(m.group(1), loanType)) {
                continue;
            }
            int x1 = Integer.parseInt(m.group(2));
            int y1 = Integer.parseInt(m.group(3));
            int x2 = Integer.parseInt(m.group(4));
            int y2 = Integer.parseInt(m.group(5));
            if (x2 - x1 < 36 || y2 - y1 < 18) {
                continue;
            }
            if (bandTop >= 0 && (y1 < bandTop - 8 || y1 > bandTop + bandHeight)) {
                continue;
            }
            if (!boundsOverlapViewport(x1, y1, x2, y2, size)) {
                continue;
            }
            int score = (m.group(1).trim().equals(loanType) ? 1_000 : 0) + (x2 - x1);
            if (score > bestScore) {
                bestScore = score;
                best = new int[]{x1, y1, x2, y2};
            }
        }
        return best;
    }

    /** Created loan title visible in the My Loans row (not off-screen dump nodes). */
    private boolean loanTypeOnMyLoansTile(String loanType) {
        return findLoanTileBounds(loanType, myLoansBandTop(), myLoansBandHeight()) != null;
    }

    private int myLoansBandHeight() {
        int top = myLoansBandTop();
        int addLoan = boundsTopFromDump("Add Loan");
        if (top >= 0 && addLoan > top) {
            return addLoan - top + 24;
        }
        return 380;
    }

    private boolean isCreatedLoanTitle(String desc, String loanType) {
        if (desc == null || loanType == null) {
            return false;
        }
        String flat = desc.replace('\n', ' ').replace("&#10;", " ").trim();
        if (flat.contains("Loan EMIs") || flat.contains("running Loan") || flat.contains("running loan")) {
            return false;
        }
        if (flat.equals(loanType)) {
            return true;
        }
        if (flat.startsWith(loanType + " ") || flat.startsWith(loanType + "\n")) {
            return true;
        }
        return flat.contains(loanType);
    }

    private int[] findLoanTileBounds(String loanType, int bandTop, int bandHeight) {
        if (loanType == null || loanType.isBlank()) {
            return null;
        }
        if (loanTypePickerOpen(text())) {
            return null;
        }
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        Pattern node = Pattern.compile(
                "content-desc=\"([^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"");
        Matcher m = node.matcher(pageSource());
        int bestScore = Integer.MIN_VALUE;
        int[] best = null;
        while (m.find()) {
            String desc = m.group(1);
            if (!isCreatedLoanTitle(desc, loanType)) {
                continue;
            }
            int x1 = Integer.parseInt(m.group(2));
            int y1 = Integer.parseInt(m.group(3));
            int x2 = Integer.parseInt(m.group(4));
            int y2 = Integer.parseInt(m.group(5));
            int w = x2 - x1;
            int h = y2 - y1;
            if (w < 36 || h < 18) {
                continue;
            }
            if (bandTop >= 0 && (y1 < bandTop - 8 || y1 > bandTop + bandHeight)) {
                continue;
            }
            if (!boundsOverlapViewport(x1, y1, x2, y2, size)) {
                continue;
            }
            int score = (desc.trim().equals(loanType) ? 1_000 : 0) + w + h;
            if (score > bestScore) {
                bestScore = score;
                best = padLoanCardBounds(x1, y1, x2, y2, w, h, bandTop, bandHeight);
            }
        }
        return best;
    }

    /** Title text is small; pad so delete icon tap targets the card tile. */
    private int[] padLoanCardBounds(int x1, int y1, int x2, int y2, int w, int h, int bandTop, int bandHeight) {
        if (w >= 120 && h >= 72) {
            return new int[]{x1, y1, x2, y2};
        }
        int padX = Math.max(24, 140 - w);
        int padY = Math.max(16, 88 - h);
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int nx1 = Math.max(0, x1 - padX / 2);
        int nx2 = Math.min(size[0], x2 + padX / 2);
        int ny1 = Math.max(bandTop >= 0 ? bandTop - 4 : 0, y1 - padY / 3);
        int ny2 = Math.min(bandTop >= 0 ? bandTop + bandHeight : size[1], y2 + padY);
        return new int[]{nx1, ny1, nx2, ny2};
    }

    private boolean boundsOverlapViewport(int x1, int y1, int x2, int y2, int[] screen) {
        int w = screen[0];
        int h = screen[1];
        if (x2 <= 12 || x1 >= w - 12) {
            return false;
        }
        if (y2 <= 72 || y1 >= h - 40) {
            return false;
        }
        int visW = Math.min(x2, w) - Math.max(x1, 0);
        return visW >= 48;
    }

    private int myLoansBandTop() {
        int top = boundsTopFromDump("My Loans");
        return top >= 0 ? top : -1;
    }

    private int myLoansCarouselY() {
        int top = myLoansBandTop();
        int addLoan = boundsTopFromDump("Add Loan");
        if (top >= 0 && addLoan > top + 40) {
            return (top + addLoan) / 2;
        }
        if (top >= 0) {
            return top + 110;
        }
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        return (int) (size[1] * 0.58);
    }

    private void swipeMyLoansCarouselTowardNext() {
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int y = myLoansCarouselY();
        adbSwipe((int) (size[0] * 0.78), y, (int) (size[0] * 0.22), y, 260);
        invalidateDump();
    }

    private void swipeMyLoansCarouselTowardPrev() {
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int y = myLoansCarouselY();
        adbSwipe((int) (size[0] * 0.22), y, (int) (size[0] * 0.78), y, 260);
        invalidateDump();
    }

    private int[] tileCenterInBand(String labelPart, int bandTop, int bandHeight) {
        int[] b = findLoanTileBounds(labelPart, bandTop, bandHeight);
        if (b == null) {
            return null;
        }
        return new int[]{(b[0] + b[2]) / 2, (b[1] + b[3]) / 2};
    }

    private void tapDeleteOnLoanCard(String cardPart) {
        if (!revealLoanCardInCarousel(cardPart)) {
            return;
        }
        closeEditSheet();
        int[] box = findLoanTileBounds(cardPart, myLoansBandTop(), myLoansBandHeight());
        int[] title = loanTitleBounds(cardPart);
        int pencilX = box != null
                ? Math.min(box[2] - 28, box[0] + (box[2] - box[0]) * 9 / 10)
                : AdbInput.screenSize(context.config().get("android.udid", ""))[0] - 180;
        int y = title != null ? (title[1] + title[3]) / 2
                : box != null ? box[1] + 36 : myLoansCarouselY();
        // The 9/10 point opens Update (the pencil). Trash is the icon just to its left.
        int[] icon = findSmallIcon(pencilX - 130, pencilX - 36, y - 40, y + 50, true);
        int tapX = icon != null ? icon[0] : pencilX - 72;
        int tapY = icon != null ? icon[1] : y;
        LOG.info("FHC tap loan trash at {},{}", tapX, tapY);
        shellPress(tapX, tapY);
        waitAfterTap();
        if (editSheetOpen()) {
            closeEditSheet();
            LOG.info("FHC edit sheet opened — trash is left of the pencil");
            shellPress(tapX - 70, tapY);
            waitAfterTap();
            if (editSheetOpen()) {
                closeEditSheet();
            }
        }
    }

    private void tapDeleteOnInvestmentCard(String cardPart) {
        revealInvestmentCardInCarousel(cardPart);
        int[] title = investmentTitleBounds(cardPart);
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int y = title != null ? (title[1] + title[3]) / 2 : boundsTopFromDump("My Investments") + 70;
        int right = size[0] - 16;
        int minX = title != null && title[2] - title[0] > 280 ? title[2] - 140 : size[0] / 2;
        int[] icon = findSmallIcon(minX, right - 8, y - 40, y + 50, false);
        int tapX = icon != null ? icon[0] : right - 64;
        int tapY = icon != null ? icon[1] : y;
        LOG.info("FHC tap investment trash at {},{}", tapX, tapY);
        shellPress(tapX, tapY);
        waitAfterTap();
    }

    /** Square icon in a band. Loans use the left icon (trash); investments use the right icon. */
    private int[] findSmallIcon(int minX, int maxX, int minY, int maxY, boolean leftmost) {
        Matcher m = Pattern.compile("bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"").matcher(pageSource());
        int best = leftmost ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        int[] tap = null;
        while (m.find()) {
            int x1 = Integer.parseInt(m.group(1));
            int y1 = Integer.parseInt(m.group(2));
            int x2 = Integer.parseInt(m.group(3));
            int y2 = Integer.parseInt(m.group(4));
            int w = x2 - x1;
            int h = y2 - y1;
            if (w < 24 || h < 24 || w > 88 || h > 88 || Math.abs(w - h) > 28) {
                continue;
            }
            int cx = (x1 + x2) / 2;
            int cy = (y1 + y2) / 2;
            if (cx < minX || cx > maxX || cy < minY || cy > maxY) {
                continue;
            }
            if (leftmost ? cx < best : cx > best) {
                best = cx;
                tap = new int[]{cx, cy};
            }
        }
        return tap;
    }

    private int[] investmentTitleBounds(String cardPart) {
        int bandTop = boundsTopFromDump("My Investments");
        Matcher m = Pattern.compile(
                "content-desc=\"([^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"")
                .matcher(pageSource());
        int best = Integer.MIN_VALUE;
        int[] tap = null;
        while (m.find()) {
            if (!m.group(1).contains(cardPart)) {
                continue;
            }
            int x1 = Integer.parseInt(m.group(2));
            int y1 = Integer.parseInt(m.group(3));
            int x2 = Integer.parseInt(m.group(4));
            int y2 = Integer.parseInt(m.group(5));
            if (bandTop >= 0 && (y1 < bandTop - 12 || y1 > bandTop + 340)) {
                continue;
            }
            int score = (x2 - x1) + (y2 - y1);
            if (score > best) {
                best = score;
                tap = new int[]{x1, y1, x2, y2};
            }
        }
        return tap;
    }

    private boolean editSheetOpen() {
        String src = text();
        return src.contains("Update") && src.contains("Cancel")
                && (src.contains("Loan Type") || src.contains("Principal"));
    }

    private void closeEditSheet() {
        if (!editSheetOpen()) {
            return;
        }
        pressFirst("Cancel");
        invalidateDump();
    }

    private void tapDeleteOnTile(String cardPart, int bandTop, int bandHeight) {
        int[] tile = tileCenterInBand(cardPart, bandTop, bandHeight);
        if (tapDeleteIconNear(tile, bandTop, bandHeight)) {
            return;
        }
        int[] box = findLoanTileBounds(cardPart, bandTop, bandHeight);
        if (box != null) {
            int tapX = Math.min(box[2] - 28, box[0] + (box[2] - box[0]) * 9 / 10);
            int tapY = box[1] + Math.max(28, (box[3] - box[1]) / 5);
            LOG.info("FHC tap loan card delete icon at {},{} for {}", tapX, tapY, cardPart);
            shellPress(tapX, tapY);
            waitAfterTap();
            return;
        }
        if (tapLabel("Delete")) {
            waitAfterTap();
        }
    }

    private boolean tapDeleteIconNear(int[] tileCenter, int bandTop, int bandHeight) {
        Pattern del = Pattern.compile(
                "content-desc=\"([^\"]*(?:Delete|Remove|Trash|delete)[^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"",
                Pattern.CASE_INSENSITIVE);
        Matcher m = del.matcher(pageSource());
        int best = Integer.MAX_VALUE;
        int[] tap = null;
        while (m.find()) {
            int y1 = Integer.parseInt(m.group(3));
            if (bandTop >= 0 && (y1 < bandTop - 8 || y1 > bandTop + bandHeight)) {
                continue;
            }
            int cx = (Integer.parseInt(m.group(2)) + Integer.parseInt(m.group(4))) / 2;
            int cy = (y1 + Integer.parseInt(m.group(5))) / 2;
            int dist = tileCenter != null
                    ? Math.abs(cy - tileCenter[1]) + Math.abs(cx - tileCenter[0]) / 4
                    : 0;
            if (dist < best) {
                best = dist;
                tap = new int[]{cx, cy};
            }
        }
        if (tap != null) {
            shellPress(tap[0], tap[1]);
            waitAfterTap();
            return true;
        }
        return false;
    }

    private boolean revealInvestmentCardInCarousel(String cardPart) {
        if (investmentTypeOnTile(cardPart)) {
            return true;
        }
        for (int pass = 0; pass < 4; pass++) {
            if (pass % 2 == 0) {
                swipeInvestmentsCarouselTowardNext();
            } else {
                swipeInvestmentsCarouselTowardPrev();
            }
            if (investmentTypeOnTile(cardPart)) {
                return true;
            }
        }
        return investmentTypeOnTile(cardPart);
    }

    private boolean investmentTypeOnTile(String cardPart) {
        int bandTop = boundsTopFromDump("My Investments");
        Pattern node = Pattern.compile(
                "content-desc=\"([^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"");
        Matcher m = node.matcher(pageSource());
        while (m.find()) {
            if (!m.group(1).contains(cardPart)) {
                continue;
            }
            int y1 = Integer.parseInt(m.group(3));
            if (bandTop >= 0 && (y1 < bandTop - 12 || y1 > bandTop + 340)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private void swipeInvestmentsCarouselTowardNext() {
        int top = boundsTopFromDump("My Investments");
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int y = top >= 0 ? top + 100 : (int) (size[1] * 0.62);
        adbSwipe((int) (size[0] * 0.78), y, (int) (size[0] * 0.22), y, 260);
        invalidateDump();
    }

    private void swipeInvestmentsCarouselTowardPrev() {
        int top = boundsTopFromDump("My Investments");
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int y = top >= 0 ? top + 100 : (int) (size[1] * 0.62);
        adbSwipe((int) (size[0] * 0.22), y, (int) (size[0] * 0.78), y, 260);
        invalidateDump();
    }

    private void confirmDelete() {
        if (editSheetOpen()) {
            closeEditSheet();
            return;
        }
        String src = text();
        boolean prompt = src.contains("Are you sure")
                || src.contains("want to delete")
                || src.contains("Delete this")
                || src.contains("content-desc=\"Delete\"")
                || src.contains("content-desc=\"Remove\"");
        if (!prompt) {
            return;
        }
        String before = screenFingerprint();
        pressFirst("Yes", "Delete", "Remove", "Confirm");
        waitUntilChanged(before, MoneyFunctionalFast.uiWait(context, 2_500));
    }

    public void openInsurance() {
        if (onInsurance()) {
            return;
        }
        if (pastIncome() && !onIncome()) {
            rewindToIncome();
        }
        if (!onIncome()) {
            fillIncome();
        }
        advanceFromIncomeToInsurance();
    }

    public void selectInsuranceChoice(String choice) {
        openInsurance();
        pressNear("any Insurance", choice);
        fSleep(600);
    }

    private int fieldsBeforeToggle = -1;

    public void enableSkipToggle(String labelPart) {
        selectInsuranceChoice("Yes");
        scrollTo(labelPart);
        fieldsBeforeToggle = editCount();
        WebElement toggle = findContains(labelPart);
        if (toggle != null && !"true".equalsIgnoreCase(String.valueOf(toggle.getAttribute("checked")))) {
            pressElement(toggle);
            fSleep(500);
        }
    }

    public boolean fewerAmountFields() {
        return fieldsBeforeToggle >= 0 && editCount() < fieldsBeforeToggle;
    }

    private int editCount() {
        return findElementsTimed(MobileWaits.xpath("//android.widget.EditText"), 400).size();
    }

    public void openSavings() {
        if (onSavings()) {
            return;
        }
        selectInsuranceChoice("No");
        tapNext();
        fSleep(800);
    }

    public void selectInvestmentChoice(String choice) {
        openSavings();
        if ("Yes".equalsIgnoreCase(choice) && (shows("Add Investment") || shows("Add New"))) {
            return;
        }
        if ("No".equalsIgnoreCase(choice) && investmentNoAnswered()) {
            return;
        }
        LOG.info("FHC select investment {}", choice);
        if (!tapChoiceBeside("any Investment", choice)) {
            pressNear("any Investment", choice);
        }
        invalidateDump();
        if ("No".equalsIgnoreCase(choice)) {
            waitUntil(this::investmentNoAnswered, MoneyFunctionalFast.uiWait(context, 2_000));
        }
    }

    /** Neither radio is selected until No is tapped. Add Investment being hidden is not a No. */
    private boolean investmentNoAnswered() {
        return choiceChecked("Investment", "No")
                || shows("create one")
                || shows("have a plan")
                || shows("creating a plan");
    }

    /** Shown only after No on investments. Next stays on this page until it is answered. */
    public void declinePlan() {
        if (!(shows("create one") || shows("have a plan") || shows("creating a plan"))) {
            return;
        }
        if (!tapChoiceBeside("plan", "No")) {
            pressNear("create one", "No");
        }
        invalidateDump();
    }

    private boolean tapChoiceBeside(String questionPart, String choice) {
        int qTop = boundsTopFromDump(questionPart);
        if (qTop < 0) {
            return false;
        }
        Matcher m = Pattern.compile(
                "(?:content-desc|text)=\"" + Pattern.quote(choice)
                        + "\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"")
                .matcher(pageSource());
        int[] tap = null;
        int bestDy = Integer.MAX_VALUE;
        while (m.find()) {
            int yMid = (Integer.parseInt(m.group(2)) + Integer.parseInt(m.group(4))) / 2;
            int dy = yMid - qTop;
            if (dy < -20 || dy > 280 || dy >= bestDy) {
                continue;
            }
            bestDy = dy;
            tap = new int[]{
                    (Integer.parseInt(m.group(1)) + Integer.parseInt(m.group(3))) / 2,
                    yMid
            };
        }
        if (tap == null) {
            return false;
        }
        LOG.info("FHC ADB tap {} beside {} at {},{}", choice, questionPart, tap[0], tap[1]);
        shellPress(tap[0], tap[1]);
        invalidateDump();
        return true;
    }

    public void openAddInvestment() {
        LOG.info("FHC open Add Investment Details");
        dismissHeadsUpNotification();
        dismissOverlays();
        returnToSavingsFromLaterFhcPages();
        if (!onSavings() && !onAddInvestment()) {
            walkForwardToSavings();
        }
        if (onAddInvestment()) {
            return;
        }
        if (onSavings() && !shows("Add Investment") && !shows("Add New")) {
            selectInvestmentChoice("Yes");
        }
        if (!pressInvestmentOption("Add New") && !pressInvestmentOption("Add Investment")) {
            tapAddInvestmentButton();
        }
        pause(500);
        if (!onAddInvestment()) {
            pressInvestmentOption("Add New");
            pressInvestmentOption("Add Investment");
            pause(400);
        }
    }

    /** TC_015+ may start on home or mid-journey (e.g. Personal); reach Savings before Add Investment. */
    private void walkForwardToSavings() {
        for (int i = 0; i < MoneyFunctionalFast.rounds(context, 16); i++) {
            dismissOverlays();
            if (fieldErrorMessage() != null) {
                resolveVisibleValidationErrors();
            }
            if (onAddInvestment() || onSavings()) {
                return;
            }
            if (onAddLoan()) {
                goBack();
                pause(350);
                continue;
            }
            if (onPersonal() || onIncome() || onInsurance()) {
                openSavings();
                fSleep(600);
                continue;
            }
            if (pastIncome()) {
                returnToSavingsFromLaterFhcPages();
                if (onSavings() || onAddInvestment()) {
                    return;
                }
                continue;
            }
            openSavings();
            fSleep(800);
        }
        LOG.warn("FHC walkForwardToSavings did not reach Savings; visible: {}", visibleLabels());
    }

    /** Later FHC pages (risk intro onward) must use header Back, never a root-tab Back. */
    private void returnToSavingsFromLaterFhcPages() {
        for (int i = 0; i < 8; i++) {
            String src = text();
            if (src.contains("Investment Type") || src.contains("Add Investment Details")
                    || (src.contains("Savings") && (src.contains("Add Investment")
                    || src.contains("Add New") || src.contains("My Investments")))) {
                return;
            }
            boolean later = src.contains("Lets do risk profile")
                    || src.contains("Question ")
                    || src.contains("Let us know your risk")
                    || src.contains("Summary Details")
                    || src.contains("YOUR FINANCIAL HEALTH SCORE")
                    || src.contains("Your Money Diagnosed");
            if (!later) {
                return;
            }
            LOG.info("FHC header Back to Savings");
            goBack();
            pause(350);
        }
    }

    /** Teams / SMS heads-up on this phone sits on the form and wedges UiAutomator dumps. */
    private void dismissHeadsUpNotification() {
        String udid = context.config().get("android.udid", "");
        int[] size = AdbInput.screenSize(udid);
        int x = Math.max(40, size[0] / 2);
        int y1 = Math.max(80, (int) (size[1] * 0.12));
        int y2 = Math.max(20, (int) (size[1] * 0.02));
        AdbInput.swipe(udid, x, y1, x, y2, 120);
        pause(200);
    }

    private void tapAddInvestmentButton() {
        String udid = context.config().get("android.udid", "");
        int[] size = AdbInput.screenSize(udid);
        int x = size[0] / 2;
        int y = (int) (size[1] * 0.48);
        LOG.info("FHC ADB tap Add Investment at {},{}", x, y);
        shellPress(x, y);
        invalidateDump();
    }

    public void addInvestmentWithoutGoal() {
        openAddInvestment();
        chooseInvestmentType();
        enterCurrentValue();
        selectGoal("No");
        pressFirst("Add");
        fSleep(500);
    }

    public void addInvestmentWithGoal() {
        openAddInvestment();
        chooseInvestmentType();
        replaceHint("Current Value", currentValue());
        pressNear("associate a goal", "Yes");
        fSleep(400);
        if (shows("You don't have any existing goals") || findExact("Car") == null) {
            pressFirst("New Goal");
            fSleep(400);
            scrollTo("Car");
            pressFirst("Car");
            replaceHint("Goal name", "Car");
            replaceHint("Goal amount", currentValue());
        } else {
            pressFirst("Existing Goals");
            fSleep(400);
            pressFirst("Car");
        }
        pressFirst("Add");
        fSleep(900);
    }

    public void openRiskIntro() {
        if (onRiskIntro()) {
            return;
        }
        openSavings();
        replaceHint("Bank Balance", bankBalance());
        selectInvestmentChoice("No");
        declinePlan();
        tapNext();
        fSleep(800);
    }

    public void openQuestion1() {
        if (onQuestion("1")) {
            return;
        }
        if (shows("Question ")) {
            for (int i = 0; i < 4 && !onQuestion("1"); i++) {
                goBack();
            }
            if (onQuestion("1")) {
                return;
            }
        }
        if (onRiskResult() || checkAlreadyComplete()) {
            return;
        }
        FunctionalModuleReset.escapeAccidentalProfile(context);
        openRiskIntro();
        pressFirst("Lets do risk profile");
        fSleep(800);
    }

    public void answer(String option) {
        scrollTo(option);
        dismissSelectPrompt();
        WebElement row = optionRow(option);
        if (row == null) {
            pressFirst(option);
        } else {
            pressElement(row);
        }
        pause(400);
        if (!optionChosen(option)) {
            row = optionRow(option);
            if (row != null) {
                pressElement(row);
                pause(400);
            }
        }
    }

    private void dismissSelectPrompt() {
        // The next answer tap clears "Please select".
    }

    /** Clickable option row. Live labels sometimes carry a trailing space. */
    private WebElement optionRow(String option) {
        WebElement best = null;
        int bestArea = Integer.MAX_VALUE;
        java.util.List<WebElement> rows;
        try {
            rows = findElementsTimed(MobileWaits.xpath(
                    "//*[@content-desc=" + xpathLiteral(option)
                            + " or @text=" + xpathLiteral(option)
                            + " or contains(@content-desc," + xpathLiteral(option) + ")]"), 400);
        } catch (Exception e) {
            return null;
        }
        for (WebElement el : rows) {
            try {
                String desc = String.valueOf(el.getAttribute("content-desc")).trim();
                String text = String.valueOf(el.getAttribute("text")).trim();
                if (!option.equals(desc) && !option.equals(text)
                        && !desc.contains(option) && !text.contains(option)) {
                    continue;
                }
                var rect = el.getRect();
                int area = rect.getWidth() * rect.getHeight();
                if (area > 0 && area < bestArea) {
                    best = el;
                    bestArea = area;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return best;
    }

    private boolean optionChosen(String option) {
        WebElement row = optionRow(option);
        if (row == null) {
            return false;
        }
        try {
            for (WebElement radio : row.findElements(MobileWaits.xpath(".//*[@checkable='true']"))) {
                if ("true".equalsIgnoreCase(String.valueOf(radio.getAttribute("checked")))) {
                    return true;
                }
            }
        } catch (Exception ignored) {
            return false;
        }
        return false;
    }

    private int scrollBudget(String label) {
        if (onIncome()) {
            return 0;
        }
        if (onPersonal() || onAddLoan() || onAddInvestment()) {
            return 3;
        }
        return MoneyFunctionalFast.rounds(context, 5);
    }

    private boolean scrollUntilShows(String label, int maxSwipes) {
        if (shows(label) || labelVisible(label)) {
            return true;
        }
        for (int i = 0; i < maxSwipes; i++) {
            String before = screenFingerprint();
            swipeUp();
            invalidateDump();
            if (waitUntil(() -> shows(label) || labelVisible(label),
                    MoneyFunctionalFast.uiWait(context, 800))) {
                return true;
            }
            waitUntilChanged(before, MoneyFunctionalFast.uiWait(context, 500));
        }
        for (int i = 0; i < Math.min(2, maxSwipes) && !(shows(label) || labelVisible(label)); i++) {
            String before = screenFingerprint();
            swipeDown();
            invalidateDump();
            if (waitUntil(() -> shows(label) || labelVisible(label),
                    MoneyFunctionalFast.uiWait(context, 600))) {
                return true;
            }
            waitUntilChanged(before, MoneyFunctionalFast.uiWait(context, 400));
        }
        return shows(label) || labelVisible(label);
    }

    private Duration incomeUiWait() {
        return MoneyFunctionalFast.uiWait(context,
                MoneyFunctionalFast.enabled(context) ? 450 : 900);
    }

    private void waitAfterTap() {
        String before = screenFingerprint();
        Duration wait = onIncome() ? incomeUiWait() : MoneyFunctionalFast.uiWait(context, 1200);
        waitUntilChanged(before, wait);
    }

    private boolean waitUntilTrue(java.util.function.BooleanSupplier condition, long budgetMs) {
        return waitUntil(condition, MoneyFunctionalFast.uiWait(context, budgetMs));
    }

    /** Refresh the dump. Navigation uses {@link #waitUntilChanged} or {@link #waitUntil}. */
    private void fSleep(long ignored) {
        invalidateDump();
    }

    private void pause(long budgetMs) {
        fSleep(budgetMs);
    }

    public void answerAndNext(String option) {
        answer(option);
        tapNext();
        fSleep(700);
    }

    public void completeQuestionnaire() {
        openQuestion1();
        if (onQuestion("1")) {
            answerAndNext("Above 5 years");
        }
        if (onQuestion("2")) {
            answerAndNext("No");
        }
        if (onQuestion("3")) {
            answerAndNext("Balanced returns with some fluctuations");
        }
        if (onQuestion("4")) {
            answerAndNext("Upto 5% to 10% of my portfolio");
        }
        if (onQuestion("5")) {
            answerAndNext("Stay Invested");
        }
    }

    /** The check is already finished, so later cases must not rebuild the questionnaire. */
    public boolean checkAlreadyComplete() {
        return onCapability() || onSummary() || onScore() || onDiagnosis()
                || shows("View Details") || shows("See the diagnosis")
                || shows("Your Financial Health Score:");
    }

    public void openRiskResult() {
        if (onRiskResult() || checkAlreadyComplete()) {
            return;
        }
        completeQuestionnaire();
        if (onQuestion("5") && !onRiskResult() && !onCapability()) {
            tapNext();
        }
    }

    public void openCapability() {
        if (onCapability()) {
            return;
        }
        if (!onRiskResult() && !onSummary() && !onScore() && !onDiagnosis()) {
            openRiskResult();
        }
        if (onSummary()) {
            goBack();
            invalidateDump();
        }
        if (onRiskResult()) {
            tapNext();
            waitUntil(() -> onCapability() || onSummary(), MoneyFunctionalFast.uiWait(context, 4_000));
        }
    }

    public void openSummary() {
        if (onSummary() || onScore() || onDiagnosis()) {
            return;
        }
        if (!onCapability()) {
            openCapability();
        }
        if (onSummary()) {
            return;
        }
        if (onCapability()) {
            tapNext();
            waitUntil(this::onSummary, MoneyFunctionalFast.uiWait(context, 4_000));
        }
    }

    public void openScore() {
        if (onScore()) {
            return;
        }
        if (onDiagnosis()) {
            goBack();
            invalidateDump();
        }
        if (onScore()) {
            return;
        }
        if (!onSummary()) {
            openSummary();
        }
        if (onSummary()) {
            tapNext();
            waitUntil(this::onScore, MoneyFunctionalFast.uiWait(context, 4_000));
            return;
        }
        if (shows("View Details")) {
            pressFirst("View Details");
            waitUntil(this::onScore, MoneyFunctionalFast.uiWait(context, 3_000));
        }
    }

    public void openDiagnosis() {
        if (onDiagnosis()) {
            return;
        }
        if (!onScore()) {
            openScore();
        }
        if (onScore()) {
            pressFirst("See the diagnosis", "See the Diagnosis");
            invalidateDump();
            pause(800);
        }
    }

    public void tapNext() {
        if (onIncome()) {
            tapNextOnIncome();
            return;
        }
        dismissOverlays();
        dismissKeyboard();
        if (findExact("Next") == null) {
            scrollTo("Next");
        }
        String before = screenFingerprint();
        pressFirst("Next");
        invalidateDump();
        waitUntilChanged(before, Duration.ofSeconds(1));
    }

    /** Presses Next only when that button is already on screen. */
    public boolean tapNextIfShown() {
        WebElement next = findExact("Next");
        if (next == null) {
            return false;
        }
        pressElement(next);
        invalidateDump();
        pause(800);
        return true;
    }

    /** Top-left in-app Back on FHC pages. Never system Back and never Open settings. */
    public boolean goBack() {
        String src = text();
        if (headerIsOpenSettings(src)) {
            return false;
        }
        String before = src.substring(0, Math.min(src.length(), 800));
        if (!pressHeaderBackChip()) {
            tapUnlabeledTopLeftBackBounds();
        }
        invalidateDump();
        return waitUntil(() -> {
            String now = text();
            int n = Math.min(120, Math.min(before.length(), now.length()));
            return n < 40 || !now.regionMatches(true, 0, before, 0, n);
        }, Duration.ofMillis(1200));
    }

    public boolean nextEnabled() {
        WebElement next = findExact("Next");
        return next != null && "true".equalsIgnoreCase(String.valueOf(next.getAttribute("enabled")));
    }

    public String fieldDigits(String hintPart) {
        WebElement field = fieldByHint(hintPart);
        if (field == null) {
            return "";
        }
        String value = String.valueOf(field.getAttribute("text"));
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    public boolean namePrefilled() {
        reveal("Full Name");
        WebElement field = fieldByHint("Name");
        if (field != null) {
            String value = String.valueOf(field.getAttribute("text")).trim();
            if (value.length() > 1 && !value.equalsIgnoreCase("Name") && !value.equalsIgnoreCase("null")) {
                return true;
            }
        }
        String src = text();
        return src.matches("(?s).*Full Name.{0,80}[A-Za-z]{2,}.*")
                || src.contains("Renny")
                || src.contains("Shubham");
    }

    public boolean choiceChecked(String questionPart, String choice) {
        WebElement option = nearest(questionPart, choice);
        if (option == null) {
            option = findExact(choice);
        }
        if (option == null) {
            return false;
        }
        if ("true".equalsIgnoreCase(String.valueOf(option.getAttribute("checked")))
                || "true".equalsIgnoreCase(String.valueOf(option.getAttribute("selected")))) {
            return true;
        }
        WebElement radio = nearestCheckable(option);
        return radio != null && "true".equalsIgnoreCase(String.valueOf(radio.getAttribute("checked")));
    }

    public boolean toggleChecked(String labelPart) {
        WebElement toggle = findContains(labelPart);
        return toggle != null && ("true".equalsIgnoreCase(String.valueOf(toggle.getAttribute("checked")))
                || "true".equalsIgnoreCase(String.valueOf(toggle.getAttribute("selected"))));
    }

    public String healthScore() {
        if (shows("OUT OF 100")) {
            Matcher gauge = Pattern.compile("content-desc=\"(\\d{1,3})\"").matcher(text());
            while (gauge.find()) {
                int value = Integer.parseInt(gauge.group(1));
                if (value > 0 && value < 100) {
                    return gauge.group(1);
                }
            }
        }
        Matcher card = Pattern.compile("Your Financial Health Score:\\s*(\\d{1,3})/100").matcher(text());
        return card.find() ? card.group(1) : "";
    }

    public String healthCategory() {
        for (String category : new String[] {"Excellent", "Good", "Average", "Poor", "Weak"}) {
            if (findExact(category) != null) {
                return category;
            }
        }
        return "";
    }

    public String riskScore() {
        Matcher matcher = DECIMAL_SCORE.matcher(text());
        return matcher.find() ? matcher.group(1) : "";
    }

    public String riskCategory() {
        for (String category : new String[] {
                "Moderately Conservative", "Moderately Aggressive", "Conservative", "Balanced", "Moderate", "Aggressive"}) {
            if (findExact(category) != null) {
                return category;
            }
        }
        return "";
    }

    public boolean recommendationShown() {
        return shows("URGENT GAPS") && shows("financial stress");
    }

    public boolean gapsButtonEnabled() {
        WebElement gaps = findExact("Show me the gaps");
        return gaps != null && "true".equalsIgnoreCase(String.valueOf(gaps.getAttribute("enabled")));
    }

    private static final String[] INVESTMENT_LIST_MARKERS = {
            "Fixed Deposit", "Recurring Deposit", "Mutual Fund", "National Pension", "Stocks", "Gold"
    };

    private void chooseInvestmentType() {
        if (investmentTypeSelected() && !investmentDropdownOpen(text())) {
            return;
        }
        dismissKeyboard();
        for (int round = 0; round < 2 && !investmentTypeSelected(); round++) {
            openInvestmentDropdown();
            tapPpfOption();
            invalidateDump();
            waitUntilTrue(() -> investmentTypeSelected() || !investmentDropdownOpen(text()), 2_000);
        }
        if (!investmentTypeSelected()) {
            FhcErrorLog.record("select PPF", "ASSERT",
                    "Investment type PPF was not selected. Visible: " + visibleLabels());
        }
        storeFieldErrors("select PPF");
    }

    private void openInvestmentDropdown() {
        if (investmentDropdownOpen(text())) {
            return;
        }
        dismissKeyboard();
        LOG.info("FHC open Investment Type dropdown");
        if (!tapSelectUnderInvestmentType()) {
            tapInvestmentTypeField();
        }
        waitUntilTrue(() -> investmentDropdownOpen(text()), 2_000);
    }

    /** The dropdown is the Select box under the label, not the "Investment type" text. */
    private boolean tapSelectUnderInvestmentType() {
        int labelY = boundsTopFromDump("Investment type");
        if (labelY < 0) {
            labelY = boundsTopFromDump("Investment Type");
        }
        Matcher row = Pattern.compile(
                "content-desc=\"Select\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"")
                .matcher(pageSource());
        int[] tap = null;
        int bestY = -1;
        while (row.find()) {
            int y1 = Integer.parseInt(row.group(2));
            if (labelY >= 0 && y1 < labelY - 8) {
                continue;
            }
            if (y1 > bestY) {
                bestY = y1;
                tap = new int[]{
                        (Integer.parseInt(row.group(1)) + Integer.parseInt(row.group(3))) / 2,
                        (y1 + Integer.parseInt(row.group(4))) / 2
                };
            }
        }
        if (tap == null && labelY >= 0) {
            int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
            tap = new int[]{size[0] / 2, labelY + 90};
        }
        if (tap == null) {
            return false;
        }
        LOG.info("FHC ADB tap Investment Select at {},{}", tap[0], tap[1]);
        shellPress(tap[0], tap[1]);
        invalidateDump();
        return true;
    }

    private void tapPpfOption() {
        if (pressInvestmentOption(investment())
                || pressInvestmentOption("Public Provident Fund (PPF)")
                || pressInvestmentOption("Public Provident Fund")
                || pressInvestmentOption("PPF")) {
            return;
        }
        if (!investmentDropdownOpen(text())) {
            return;
        }
        swipeUp();
        invalidateDump();
        pressInvestmentOption("Public Provident Fund (PPF)");
        pressInvestmentOption("PPF");
    }

    private boolean tapInvestmentTypeField() {
        if (tapDescriptionBounds("Select Investment Type")) {
            return true;
        }
        String src = pageSource();
        Matcher row = Pattern.compile(
                "content-desc=\"([^\"]*Investment Type[^\"]*)\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"",
                Pattern.CASE_INSENSITIVE).matcher(src);
        int bestY = -1;
        int[] tap = null;
        while (row.find()) {
            int y1 = Integer.parseInt(row.group(3));
            if (y1 > bestY) {
                bestY = y1;
                tap = new int[]{
                        (Integer.parseInt(row.group(2)) + Integer.parseInt(row.group(4))) / 2,
                        (y1 + Integer.parseInt(row.group(5))) / 2
                };
            }
        }
        if (tap != null) {
            LOG.info("FHC ADB tap Investment Type field at {},{}", tap[0], tap[1]);
            shellPress(tap[0], tap[1]);
            invalidateDump();
            return true;
        }
        return tapDescriptionBounds("Investment Type");
    }

    private boolean investmentDropdownOpen() {
        return investmentDropdownOpen(text());
    }

    private boolean investmentDropdownOpen(String src) {
        int visible = 0;
        for (String marker : INVESTMENT_LIST_MARKERS) {
            if (src.contains(marker)) {
                visible++;
            }
        }
        return visible >= 2;
    }

    private boolean pressInvestmentOption(String label) {
        LOG.info("FHC tap investment option {}", label);
        return pressLabeledOption(label);
    }

    private boolean pressLabeledOption(String label) {
        if (label == null || label.isBlank()) {
            return false;
        }
        if (tapDescriptionBounds(label)) {
            return true;
        }
        try {
            for (WebElement el : findElementsTimed(MobileWaits.xpath(
                    "//*[@content-desc=" + xpathLiteral(label)
                            + " or @text=" + xpathLiteral(label)
                            + " or contains(@content-desc," + xpathLiteral(label) + ")"
                            + " or contains(@text," + xpathLiteral(label) + ")]"), 400)) {
                try {
                    var rect = el.getRect();
                    if (rect.getWidth() > 8 && rect.getHeight() > 8) {
                        pressElement(el);
                        waitAfterTap();
                        invalidateDump();
                        return true;
                    }
                } catch (Exception ignored) {
                    // stale
                }
            }
        } catch (Exception ignored) {
            // locator miss
        }
        return false;
    }

    /** Flutter dropdown rows are often isDisplayed=false; tap the dump bounds instead. */
    private boolean tapDescriptionBounds(String label) {
        String source = text();
        Matcher matcher = Pattern.compile(
                "content-desc=\"" + Pattern.quote(label) + "\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"")
                .matcher(source);
        if (!matcher.find()) {
            matcher = Pattern.compile(
                    "bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"[^>]*content-desc=\""
                            + Pattern.quote(label) + "\"")
                    .matcher(source);
            if (!matcher.find()) {
                matcher = Pattern.compile(
                        "content-desc=\"[^\"]*" + Pattern.quote(label)
                                + "[^\"]*\"[^>]*bounds=\"\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]\"")
                        .matcher(source);
                if (!matcher.find()) {
                    return false;
                }
            }
        }
        int x = (Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(3))) / 2;
        int y = (Integer.parseInt(matcher.group(2)) + Integer.parseInt(matcher.group(4))) / 2;
        LOG.info("FHC ADB tap {} at {},{}", label, x, y);
        shellPress(x, y);
        waitAfterTap();
        invalidateDump();
        return true;
    }

    private void fillEmptyInvestmentAmounts() {
        for (WebElement field : findElementsTimed(MobileWaits.xpath("//android.widget.EditText"), 400)) {
            try {
                String hint = String.valueOf(field.getAttribute("hint"));
                String desc = String.valueOf(field.getAttribute("content-desc"));
                String value = String.valueOf(field.getAttribute("text"));
                boolean empty = value == null || value.isBlank() || "null".equals(value);
                String combined = (hint + " " + desc).toLowerCase();
                if (empty && (combined.contains("value") || combined.contains("amount")
                        || combined.contains("invest") || combined.contains("current"))) {
                    replaceField(field, currentValue());
                }
            } catch (Exception ignored) {
                // stale
            }
        }
    }

    private void scrollTo(String label) {
        if (onIncome()) {
            return;
        }
        scrollUntilShows(label, scrollBudget(label));
    }

    private void pressFirst(String... labels) {
        for (String label : labels) {
            WebElement el = findExact(label);
            if (el == null) {
                el = findContains(label);
            }
            if (el != null) {
                pressElement(el);
                return;
            }
        }
    }

    private void pressNear(String questionPart, String choice) {
        WebElement el = nearest(questionPart, choice);
        if (el != null) {
            pressElement(el);
        } else {
            pressFirst(choice);
        }
    }

    private WebElement nearest(String questionPart, String choice) {
        WebElement question = findContains(questionPart);
        int minY = question == null ? 0 : question.getRect().getY();
        WebElement best = null;
        int bestDy = Integer.MAX_VALUE;
        try {
            for (WebElement el : findElementsTimed(MobileWaits.xpath(
                    "//*[@content-desc=" + xpathLiteral(choice)
                            + " or @text=" + xpathLiteral(choice) + "]"), 400)) {
                try {
                    int dy = el.getRect().getY() - minY;
                    if (dy >= -40 && dy < bestDy) {
                        best = el;
                        bestDy = dy;
                    }
                } catch (Exception ignored) {
                    // stale
                }
            }
        } catch (Exception ignored) {
            // locator miss
        }
        return best;
    }

    private WebElement nearestCheckable(WebElement label) {
        int labelY = label.getRect().getY();
        WebElement closest = null;
        int closestDy = Integer.MAX_VALUE;
        for (WebElement el : findElementsTimed(MobileWaits.xpath("//*[@checkable='true']"), 400)) {
            try {
                int dy = Math.abs(el.getRect().getY() - labelY);
                if (dy < closestDy) {
                    closestDy = dy;
                    closest = el;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return closest;
    }

    private WebElement findExact(String label) {
        return firstPresent(Duration.ofMillis(600),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"),
                MobileWaits.xpath("//*[@text=" + xpathLiteral(label) + "]"));
    }

    private WebElement findContains(String label) {
        return firstPresent(Duration.ofMillis(600),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"),
                MobileWaits.xpath("//*[contains(@text," + xpathLiteral(label) + ")]"));
    }

    WebElement labelled(String label) {
        WebElement exact = findExact(label);
        return exact != null ? exact : findContains(label);
    }

    private WebElement fieldByHint(String hintPart) {
        return firstPresent(Duration.ofMillis(800),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint," + xpathLiteral(hintPart) + ")]"),
                MobileWaits.xpath("//android.widget.EditText[contains(@content-desc," + xpathLiteral(hintPart) + ")]"));
    }

    private void replaceHint(String hintPart, String value) {
        WebElement field = fieldByHint(hintPart);
        if (field != null) {
            replaceField(field, value);
        }
    }

    private void replaceField(WebElement field, String value) {
        try {
            pressElement(field);
        } catch (RuntimeException e) {
            LOG.warn("FHC field tap missed: {}", e.toString());
            return;
        }
        String udid = context.config().get("android.udid", "");
        String current = "";
        try {
            current = String.valueOf(field.getAttribute("text"));
        } catch (RuntimeException ignored) {
            current = "";
        }
        int digits = current == null ? 0 : current.replaceAll("\\D", "").length();
        int deletes = Math.max(digits + 2, 8);
        String[] keys = new String[deletes + 3];
        keys[0] = "input";
        keys[1] = "keyevent";
        keys[2] = "123";
        for (int i = 0; i < deletes; i++) {
            keys[i + 3] = "67";
        }
        AdbCommands.shell(udid, keys);
        AdbCommands.shell(udid, "input", "text", value);
        fSleep(150);
        dismissKeyboard();
    }

    private void dismissKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            String udid = context.config().get("android.udid", "");
            AdbCommands.shell(udid, "input", "keyevent", "111");
        }
        fSleep(200);
    }

}
