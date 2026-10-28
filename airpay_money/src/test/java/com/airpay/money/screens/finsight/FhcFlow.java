package com.airpay.money.screens.finsight;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Financial Health Check journey from the sheet.
 * Dummy amounts only. KYC is not started. Diagnosis share actions are not tapped.
 */
public class FhcFlow extends BaseScreen {

    public static final String PINCODE = "247001";
    public static final String CITY = "Saharanpur";
    public static final String STATE = "Uttar Pradesh";
    public static final String INCOME = "100000";
    public static final String EXPENSE = "40000";
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

    public FhcFlow(TestContext context) {
        super(context);
        this.home = new HomeScreen(context);
        this.finsight = new FinSightHomeScreen(context);
    }

    public String text() {
        return pageSource().replace("&amp;", "&").replace("&#10;", " ").replace('\n', ' ');
    }

    public boolean shows(String label) {
        return text().contains(label);
    }

    public boolean errorShown() {
        String src = text().toLowerCase();
        return src.contains("something went wrong")
                || src.contains("please enter")
                || src.contains("unable to")
                || src.contains("could not")
                || src.contains("couldn't");
    }

    public boolean onPersonal() {
        return shows("Personal Details") && shows("Pincode") && shows("Full Name");
    }

    public boolean onIncome() {
        boolean title = shows("Income & Expense") || shows("Income and Expense");
        boolean body = shows("Net Monthly Income") || shows("My Loans") || shows("running Loan");
        return title && body;
    }

    public boolean runningLoanQuestionDisplayed() {
        return shows("running Loan");
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
        return shows("Add Investment") && shows("Investment");
    }

    public boolean onRiskIntro() {
        return shows("Lets do risk profile") && shows("I already know");
    }

    public boolean onQuestion(String number) {
        return shows("Question " + number + "/5");
    }

    public boolean onRiskResult() {
        return shows("Your Risk Profile");
    }

    public boolean onCapability() {
        String src = text();
        return src.contains("Let us know your risk") && src.contains("capability");
    }

    public boolean onSummary() {
        return shows("Summary Details") && shows("My Personal Details");
    }

    public boolean onScore() {
        return shows("OUT OF 100") && shows("YOUR FINANCIAL HEALTH SCORE");
    }

    public boolean onDiagnosis() {
        return shows("Your Money Diagnosed");
    }

    public void openFinsight() {
        for (int i = 0; i < 4 && !finsight.isDisplayed(); i++) {
            finsight.tapLabel("FinSight");
            home.dismissHomeCoachIfShown();
            sleep(400);
        }
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
        openFinsight();
        scrollTo("Financial Health");
        pressFirst("Continue", "Start Now", "Let's start", "Let's Start", "Reassess", "View Details");
        sleep(900);
        if (!onPersonal() && (onScore() || shows("See the diagnosis"))) {
            pressFirst("Reassess", "Let's start", "Start Now", "Continue");
            sleep(900);
        }
    }

    public void fillPersonalDetails() {
        openPersonalDetails();
        pressFirst("Male");
        if (!shows(city())) {
            replaceHint("Pincode", pincode());
            sleep(1200);
        }
    }

    public void openIncome() {
        if (onIncome()) {
            return;
        }
        fillPersonalDetails();
        tapNext();
        sleep(800);
    }

    public void fillIncome() {
        openIncome();
        replaceHint("Net Monthly Income", income());
        replaceHint("Household", expense());
    }

    public void selectLoanChoice(String choice) {
        ensureIncomeAmounts();
        if (!runningLoanQuestionDisplayed()) {
            return;
        }
        pressNear("running Loan", choice);
        sleep(600);
    }

    public void ensureIncomeAmounts() {
        openIncome();
        if (!income().equals(fieldDigits("Income"))) {
            replaceHint("Net Monthly Income", income());
        }
        if (!expense().equals(fieldDigits("Household"))) {
            replaceHint("Household", expense());
        }
    }

    public void openAddLoan() {
        if (onAddLoan()) {
            return;
        }
        selectLoanChoice("Yes");
        scrollTo("Add Loan");
        pressFirst("Add Loan");
        sleep(700);
    }

    public void searchLoanType(String word) {
        openAddLoan();
        pressFirst("Loan Type");
        sleep(600);
        WebElement search = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.EditText"));
        if (search != null) {
            replaceField(search, word);
            sleep(500);
        }
    }

    public void selectLoanType(String loanType) {
        if (!shows(loanType)) {
            pressFirst("Loan Type");
            sleep(400);
        }
        scrollTo(loanType);
        pressFirst(loanType);
        sleep(400);
    }

    public void enterPrincipal() {
        replaceHint("Principal", principal());
    }

    public void enterEmi() {
        replaceHint("EMI", emi());
    }

    public void enterRate() {
        replaceHint("Interest", rate());
    }

    public void tapAdd() {
        pressFirst("Add");
        sleep(800);
    }

    public void chooseInvestmentTypePublic() {
        chooseInvestmentType();
    }

    public void enterCurrentValue() {
        replaceHint("Current Value", currentValue());
    }

    public void enterBankBalance() {
        replaceHint("Bank Balance", bankBalance());
    }

    public void selectGoal(String choice) {
        if (shows("associate a goal")) {
            pressNear("associate a goal", choice);
            sleep(400);
        }
    }

    public void chooseCarGoal() {
        if (shows("You don't have any existing goals") || findExact("Car") == null) {
            pressFirst("New Goal");
            sleep(400);
            scrollTo("Car");
            pressFirst("Car");
            replaceHint("Goal name", "Car");
            replaceHint("Goal amount", currentValue());
        } else {
            pressFirst("Existing Goals");
            sleep(400);
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
        if (findExact("Back") != null) {
            return true;
        }
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@clickable='true']"))) {
            try {
                var rect = node.getRect();
                if (rect.getY() < 280 && rect.getX() < 220 && rect.getWidth() < 220 && rect.getHeight() < 220) {
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }

    public void addLoan(String loanType) {
        openAddLoan();
        selectLoanType(loanType);
        enterPrincipal();
        enterEmi();
        enterRate();
        tapAdd();
    }

    public void openInsurance() {
        if (onInsurance()) {
            return;
        }
        fillIncome();
        selectLoanChoice("No");
        tapNext();
        sleep(800);
    }

    public void selectInsuranceChoice(String choice) {
        openInsurance();
        pressNear("any Insurance", choice);
        sleep(600);
    }

    private int fieldsBeforeToggle = -1;

    public void enableSkipToggle(String labelPart) {
        selectInsuranceChoice("Yes");
        scrollTo(labelPart);
        fieldsBeforeToggle = editCount();
        WebElement toggle = findContains(labelPart);
        if (toggle != null && !"true".equalsIgnoreCase(String.valueOf(toggle.getAttribute("checked")))) {
            pressElement(toggle);
            sleep(500);
        }
    }

    public boolean fewerAmountFields() {
        return fieldsBeforeToggle >= 0 && editCount() < fieldsBeforeToggle;
    }

    private int editCount() {
        return driver.findElements(MobileWaits.xpath("//android.widget.EditText")).size();
    }

    public void openSavings() {
        if (onSavings()) {
            return;
        }
        selectInsuranceChoice("No");
        tapNext();
        sleep(800);
    }

    public void selectInvestmentChoice(String choice) {
        openSavings();
        pressNear("any Investment", choice);
        sleep(500);
    }

    /** Shown only after No on investments. Next stays on this page until it is answered. */
    public void declinePlan() {
        if (shows("create one") || shows("have a plan")) {
            pressNear("create one", "No");
            sleep(300);
        }
    }

    public void openAddInvestment() {
        if (onAddInvestment()) {
            return;
        }
        selectInvestmentChoice("Yes");
        scrollTo("Add");
        pressFirst("Add Investment", "Add New");
        sleep(700);
    }

    public void addInvestmentWithoutGoal() {
        openAddInvestment();
        chooseInvestmentType();
        replaceHint("Current Value", currentValue());
        if (shows("associate a goal")) {
            pressNear("associate a goal", "No");
        }
        pressFirst("Add");
        sleep(900);
    }

    public void addInvestmentWithGoal() {
        openAddInvestment();
        chooseInvestmentType();
        replaceHint("Current Value", currentValue());
        pressNear("associate a goal", "Yes");
        sleep(400);
        if (shows("You don't have any existing goals") || findExact("Car") == null) {
            pressFirst("New Goal");
            sleep(400);
            scrollTo("Car");
            pressFirst("Car");
            replaceHint("Goal name", "Car");
            replaceHint("Goal amount", currentValue());
        } else {
            pressFirst("Existing Goals");
            sleep(400);
            pressFirst("Car");
        }
        pressFirst("Add");
        sleep(900);
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
        sleep(800);
    }

    public void openQuestion1() {
        if (onQuestion("1") || onRiskResult()) {
            return;
        }
        if (shows("Question ")) {
            for (int i = 0; i < 4 && !onQuestion("1") && !onRiskResult(); i++) {
                goBack();
            }
            if (onQuestion("1") || onRiskResult()) {
                return;
            }
        }
        if (onProfileSettings()) {
            return;
        }
        openRiskIntro();
        pressFirst("Lets do risk profile");
        sleep(800);
    }

    private boolean onProfileSettings() {
        return shows("PROFILE SETTINGS") || shows("Log Out");
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
        for (int i = 0; i < 5 && shows("Please select"); i++) {
            pause(400);
        }
    }

    /** Clickable option row. Live labels sometimes carry a trailing space. */
    private WebElement optionRow(String option) {
        WebElement best = null;
        int bestArea = Integer.MAX_VALUE;
        for (WebElement el : driver.findElements(MobileWaits.xpath("//*[@clickable='true']"))) {
            try {
                if (!el.isDisplayed()) {
                    continue;
                }
                String desc = String.valueOf(el.getAttribute("content-desc")).trim();
                String text = String.valueOf(el.getAttribute("text")).trim();
                if (!option.equals(desc) && !option.equals(text)) {
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

    private static void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void answerAndNext(String option) {
        answer(option);
        tapNext();
        sleep(700);
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
        if (onRiskResult() || checkAlreadyComplete() || onProfileSettings()) {
            return;
        }
        completeQuestionnaire();
        sleep(800);
    }

    public void openCapability() {
        if (onCapability() || onProfileSettings() || onScore() || onDiagnosis()) {
            return;
        }
        if (onRiskResult()) {
            tapNextIfShown();
            return;
        }
        if (onSummary()) {
            goBack();
        }
    }

    public void openSummary() {
        if (onSummary() || onProfileSettings() || onScore() || onDiagnosis()) {
            return;
        }
        if (onCapability()) {
            tapNextIfShown();
        }
    }

    public void openScore() {
        if (onScore()) {
            return;
        }
        if (onDiagnosis()) {
            goBack();
        }
        if (onScore() || onProfileSettings()) {
            return;
        }
        if (onSummary()) {
            tapNextIfShown();
            return;
        }
        if (shows("View Details")) {
            pressFirst("View Details");
            sleep(900);
        }
    }

    public void openDiagnosis() {
        if (onDiagnosis() || onProfileSettings()) {
            return;
        }
        if (!onScore()) {
            openScore();
        }
        if (onScore()) {
            pressFirst("See the diagnosis", "See the Diagnosis");
            sleep(800);
        }
    }

    public void tapNext() {
        if (findExact("Next") == null) {
            scrollTo("Next");
        }
        pressFirst("Next");
        sleep(700);
    }

    /** Presses Next only when that button is already on screen. */
    public boolean tapNextIfShown() {
        WebElement next = findExact("Next");
        if (next == null) {
            return false;
        }
        pressElement(next);
        sleep(700);
        return true;
    }

    public void goBack() {
        tapHeaderBack();
        sleep(700);
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
        WebElement field = fieldByHint("Name");
        if (field == null) {
            return false;
        }
        String value = String.valueOf(field.getAttribute("text")).trim();
        return value.length() > 1 && !value.equalsIgnoreCase("Name") && !value.equalsIgnoreCase("null");
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

    private void chooseInvestmentType() {
        pressFirst("Investment Type", "Investment type");
        sleep(500);
        scrollTo("Public Provident Fund");
        pressFirst(investment(), "Public Provident Fund (PPF)", "Public Provident Fund");
        sleep(400);
    }

    private void scrollTo(String label) {
        for (int i = 0; i < 6 && !shows(label); i++) {
            swipeUp();
            sleep(300);
        }
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
        for (WebElement el : driver.findElements(MobileWaits.xpath("//*[@content-desc or @text]"))) {
            try {
                String desc = String.valueOf(el.getAttribute("content-desc"));
                String text = String.valueOf(el.getAttribute("text"));
                if (!choice.equals(desc.trim()) && !choice.equals(text.trim())) {
                    continue;
                }
                int dy = el.getRect().getY() - minY;
                if (dy >= -40 && dy < bestDy) {
                    best = el;
                    bestDy = dy;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return best;
    }

    private WebElement nearestCheckable(WebElement label) {
        int labelY = label.getRect().getY();
        WebElement closest = null;
        int closestDy = Integer.MAX_VALUE;
        for (WebElement el : driver.findElements(MobileWaits.xpath("//*[@checkable='true']"))) {
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
        return firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"),
                MobileWaits.xpath("//*[@text=" + xpathLiteral(label) + "]"));
    }

    private WebElement findContains(String label) {
        return firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"),
                MobileWaits.xpath("//*[contains(@text," + xpathLiteral(label) + ")]"));
    }

    private WebElement labelled(String label) {
        WebElement exact = findExact(label);
        return exact != null ? exact : findContains(label);
    }

    private WebElement fieldByHint(String hintPart) {
        return firstPresent(Duration.ofSeconds(3),
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
        pressElement(field);
        sleep(150);
        String udid = context.config().get("android.udid", "");
        String current = String.valueOf(field.getAttribute("text"));
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
        sleep(150);
        dismissKeyboard();
    }

    private void dismissKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            String udid = context.config().get("android.udid", "");
            AdbCommands.shell(udid, "input", "keyevent", "111");
        }
        sleep(200);
    }

    /** Shorter pauses for the sheet run. The phone still has time to draw the next screen. */
    protected static void sleep(long ms) {
        try {
            Thread.sleep(Math.max(80, ms * 2 / 5));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
