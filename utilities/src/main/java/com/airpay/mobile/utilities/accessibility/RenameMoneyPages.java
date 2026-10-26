package com.airpay.mobile.utilities.accessibility;

import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.config.ProjectPaths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Names each captured screen by module and sub-module, the same way Mutual Funds pages are named.
 * Profile settings, General settings, Investments, Insurance, Loans, FinSight and Calculators
 * come from the capture id, not from a random heading on the screen.
 */
public final class RenameMoneyPages {

    private RenameMoneyPages() {
    }

    public static void main(String[] args) throws Exception {
        Path repo = Path.of(args.length > 0 ? args[0] : ".").toAbsolutePath().normalize();
        Path capture = repo.resolve("target/money/manual-capture");
        Path scan = repo.resolve("target/money/accessibility_testing_result/money-scan.json");
        List<A11yPageScanResult> pages = A11yJson.parsePages(Files.readString(scan));
        List<A11yPageScanResult> renamed = new ArrayList<>();
        Map<String, Integer> used = new LinkedHashMap<>();
        int changed = 0;
        for (A11yPageScanResult page : pages) {
            String label = screenName(capture, page);
            if (!label.equals(page.page().label())) {
                changed++;
            }
            renamed.add(retitle(page, unique(label, used)));
        }
        System.setProperty("airpay.product", "money");
        Path html = A11yScorecardWriter.writeReplacing(
                ConfigLoader.load(),
                "com.finmapp.fininfinity",
                renamed,
                ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y),
                "money",
                "airpay money Android");
        System.out.println("RENAME pages=" + renamed.size() + " changed=" + changed + " html=" + html);
    }

    private static String unique(String label, Map<String, Integer> used) {
        int n = used.merge(label, 1, Integer::sum);
        return n == 1 ? label : label + " (" + n + ")";
    }

    private static A11yPageScanResult retitle(A11yPageScanResult page, String label) {
        A11yPageRef ref = page.page();
        A11yPageRef updated = new A11yPageRef(
                ref.id(), ref.module(), label, ref.route(),
                ref.authRequired(), ref.nav(), ref.menuGroup(), ref.pageObject(), ref.a11yPriority());
        List<A11yFinding> findings = new ArrayList<>();
        for (A11yFinding finding : page.findings()) {
            findings.add(new A11yFinding(
                    finding.module(), finding.pageId(), label, finding.route(),
                    finding.ruleId(), finding.impact(), finding.description(), finding.help(),
                    finding.helpUrl(), finding.wcagTags(), finding.field(), finding.selector(),
                    finding.htmlSnippet(), finding.failureSummary(), finding.expected(),
                    finding.actual(), finding.status()));
        }
        return new A11yPageScanResult(
                updated, page.url(), page.score(), page.violationsCount(), page.passesCount(),
                page.incompleteCount(), findings, page.incompleteFindings(), page.passedRuleIds(),
                page.error());
    }

    private static String screenName(Path capture, A11yPageScanResult page) {
        String id = page.page().id().toLowerCase(Locale.ROOT);
        Path xml = capture.resolve("uidump-" + page.page().id() + ".xml");
        boolean capturedFile = Files.isRegularFile(xml);
        if (!capturedFile && !id.startsWith("prod-")) {
            return page.page().label();
        }
        if (capturedFile) {
            String seen = fromDump(id, xml);
            if (seen != null) {
                return seen;
            }
        }
        if (id.startsWith("prof-")) {
            return profileName(id, xml);
        }
        if (id.startsWith("calc-")) {
            return calcName(id);
        }
        if (id.startsWith("fh-")) {
            return join(join("FinSight", finsightScreen(id)), detail(id, "fh"));
        }
        if (id.startsWith("gold-")) {
            return join("Products / Investments / Digital Gold", detail(id, "gold"));
        }
        if (id.startsWith("fdx-") || id.startsWith("fd-")) {
            return join("Products / Investments / Fixed Deposit", detail(id, id.startsWith("fdx-") ? "fdx" : "fd"));
        }
        if (id.startsWith("instk-") || id.startsWith("in-")) {
            return join("Products / Investments / Indian Stocks", detail(id, id.startsWith("instk-") ? "instk" : "in"));
        }
        if (id.startsWith("usstk-") || id.startsWith("us-")) {
            return join("Products / Investments / US Stocks", detail(id, id.startsWith("usstk-") ? "usstk" : "us"));
        }
        if (id.startsWith("ins-")) {
            return join(insuranceScreen(id), detail(id, "ins"));
        }
        if (id.startsWith("credit-")) {
            return join("Products / Loans & Credit Cards / Credit Cards", detail(id, "credit"));
        }
        if (id.startsWith("apply-")) {
            return join(loanScreen(id), detail(id, "apply"));
        }
        if (id.startsWith("tax-")) {
            return join("Products / Tax Filing & Planning", detail(id, "tax"));
        }
        if (id.startsWith("notif-")) {
            return join("Notifications", detail(id, "notif"));
        }
        if (id.startsWith("deep-")) {
            return join("Products / Loans & Credit Cards / Application", detail(id, "deep"));
        }
        if (id.startsWith("prod-")) {
            return productLive(id);
        }
        return page.page().label();
    }

    private static String profileName(String id, Path xml) {
        if (id.contains("personal")) {
            return join("Profile / Profile settings / Personal Details", detail(id, "prof-personal"));
        }
        if (id.contains("-work")) {
            return join("Profile / Profile settings / Work Details", detail(id, "prof-work"));
        }
        if (id.contains("-addr")) {
            return join("Profile / Profile settings / Address Details", detail(id, "prof-addr"));
        }
        if (id.contains("-bank")) {
            return join("Profile / Profile settings / Bank Account", detail(id, "prof-bank"));
        }
        if (id.contains("-docs")) {
            return join("Profile / Profile settings / Document Details", detail(id, "prof-docs"));
        }
        if (id.contains("-kyc")) {
            return join("Profile / Profile settings / KYC Details", detail(id, "prof-kyc"));
        }
        if (id.contains("-tx-") || id.startsWith("prof-tx")) {
            return join("Profile / Profile settings / Transaction history", detail(id, "prof-tx"));
        }
        if (id.contains("contact")) {
            return join("Profile / Profile settings / Contact Us", detail(id, "prof"));
        }
        if (id.contains("-help")) {
            return join("Profile / Profile settings / How can we help you", detail(id, "prof-help"));
        }
        if (id.contains("screenlock") || id.contains("set-01")) {
            return join("Profile / General settings / Screen Lock", detail(id, "prof-set-01-screenlock"));
        }
        if (id.contains("changepin") || id.contains("set-02")) {
            return join("Profile / General settings / Change PIN", detail(id, "prof-set-02-changepin"));
        }
        if (id.contains("utilities") || id.contains("set-04")) {
            return join("Profile / General settings / Utilities", detail(id, "prof-set-04-utilities"));
        }
        if (id.contains("-tnc") || id.contains("set-05")) {
            return join("Profile / General settings / Terms & Conditions", detail(id, "prof-set-05-tnc"));
        }
        if (id.contains("privacy") || id.contains("set-06")) {
            return join("Profile / General settings / Privacy Policy", detail(id, "prof-set-06-privacy"));
        }
        if (id.contains("acctmgmt") || id.contains("set-07")) {
            return join("Profile / General settings / Account Management", detail(id, "prof-set-07-acctmgmt"));
        }
        if (id.contains("rate") || id.contains("set-08") || id.contains("set-09")
                || id.contains("set-10") || id.contains("set-11")) {
            return join("Profile / General settings / Rate Us", detail(id, "prof"));
        }
        if (id.contains("faq") || id.contains("set-12")) {
            return join("Profile / General settings / FAQs", detail(id, "prof"));
        }
        if (id.contains("what-fhc")) {
            return "FinSight / What is FHC";
        }
        if (id.contains("how-works") || id.contains("how-it-works")) {
            return "FinSight / How it works";
        }
        if (id.contains("start-fhc") || id.contains("fhc")) {
            return join("FinSight / Financial Health Check", detail(id, "prof"));
        }
        if (id.contains("about") || id.contains("testimonial") || id.contains("blog")
                || id.contains("set-13") || id.contains("set-14") || id.contains("set-15") || id.contains("set-16")) {
            return join("Profile / General settings / About airpay money", detail(id, "prof"));
        }
        return join("Profile", scrollHint(id));
    }

    /**
     * Names the screen from what is actually open. A profile dump can be Home,
     * General settings, or the photo picker. A calculator file can still be the
     * calculator list. The file prefix is not the screen name.
     */
    private static String fromDump(String id, Path xml) {
        List<String> labels = contentLabels(xml);
        if (labels.isEmpty()) {
            return null;
        }
        if (isDeviceHome(labels)) {
            return "Device home";
        }
        if (has(labels, "Photos") && has(labels, "Collections")) {
            return withScroll("Profile / Change profile picture", id);
        }
        int profileRows = countAny(labels, "Personal Details", "Work Details", "Address Details",
                "Bank Account", "Document Details", "KYC Details");
        int generalRows = countAny(labels, "Screen Lock", "Change PIN", "Utilities", "Terms & Conditions",
                "Terms and Conditions", "Privacy Policy", "Account Management", "Rate Us", "FAQs",
                "About airpay money");
        if (has(labels, "PROFILE SETTINGS") || profileRows >= 2) {
            return withScroll("Profile / Profile settings", id);
        }
        if (has(labels, "GENERAL SETTINGS") || generalRows >= 2) {
            return withScroll("Profile / General settings", id);
        }
        int calcTiles = countAny(labels,
                "EMI Calculator", "SIP Calculator", "Lumpsum Calculator", "PPF Calculator", "EPF Calculator",
                "FD Calculator", "NPS Calculator", "Retirement Calculator", "Crorepati Calculator",
                "Term Insurance Calculator", "Buy vs Rent Calculator", "Inflation Calculator", "Budget Calculator");
        if (calcTiles >= 3) {
            return withScroll("Calculators", id);
        }
        String oneCalc = firstMatch(labels,
                "Term Insurance Calculator", "Buy vs Rent Calculator", "Crorepati Calculator",
                "Retirement Calculator", "Inflation Calculator", "Lumpsum Calculator", "Budget Calculator",
                "EMI Calculator", "SIP Calculator", "PPF Calculator", "EPF Calculator", "NPS Calculator",
                "FD Calculator");
        if (oneCalc != null && (calcTiles == 1 || id.startsWith("calc-"))) {
            return withScroll("Calculators / " + oneCalc, id);
        }
        if (!id.startsWith("prof-")) {
            String product = productFromLabels(labels);
            if (product != null) {
                return withScroll(product, id);
            }
        }
        String inner = innerScreen(labels, id);
        if (inner != null) {
            return withScroll(inner, id);
        }
        if (has(labels, "Follow us on") && profileRows == 0 && generalRows < 2) {
            return withScroll("Profile / Follow us on", id);
        }
        if (has(labels, "Open settings") && has(labels, "Hello") && profileRows == 0 && generalRows == 0
                && calcTiles < 3) {
            return withScroll("Home", id);
        }
        return null;
    }

    private static String productFromLabels(List<String> labels) {
        int investments = countAny(labels, "Mutual Funds", "Digital Gold", "Fixed Deposit", "US Stocks",
                "Indian Stocks", "NPS");
        if (investments >= 2) {
            return "Products / Investments";
        }
        int insurance = countAny(labels, "2-wheeler insurance", "2 Wheeler", "4-wheeler insurance", "4 Wheeler",
                "Health Insurance", "Life Insurance");
        if (insurance >= 2) {
            return "Products / Insurance";
        }
        int loans = countAny(labels, "Gold Loan", "Credit Cards", "Credit Card", "Choose Your Card Type",
                "Personal Loan", "Home Loan", "Loan Against Property");
        if (loans >= 2) {
            return "Products / Loans & Credit Cards";
        }
        if (hasPrefix(labels, "2-wheeler") || has(labels, "2 Wheeler")) {
            return "Products / Insurance / 2 Wheeler";
        }
        if (hasPrefix(labels, "4-wheeler") || has(labels, "4 Wheeler")) {
            return "Products / Insurance / 4 Wheeler";
        }
        if (has(labels, "Health Insurance")) {
            return "Products / Insurance / Health";
        }
        if (has(labels, "Life Insurance")) {
            return "Products / Insurance / Life";
        }
        if (has(labels, "Choose Your Card Type") || has(labels, "Credit Cards") || has(labels, "Credit Card")) {
            return "Products / Loans & Credit Cards / Credit Cards";
        }
        if (has(labels, "Gold Loan")) {
            return "Products / Loans & Credit Cards / Gold Loan";
        }
        if (has(labels, "Personal Loan")) {
            return "Products / Loans & Credit Cards / Personal Loan";
        }
        if (has(labels, "Home Loan")) {
            return "Products / Loans & Credit Cards / Home Loan";
        }
        if (has(labels, "Loan Against Property")) {
            return "Products / Loans & Credit Cards / Loan Against Property";
        }
        if (has(labels, "Mutual Funds")) {
            return "Products / Investments / Mutual Funds";
        }
        if (has(labels, "Digital Gold")) {
            return "Products / Investments / Digital Gold";
        }
        if (has(labels, "Fixed Deposit")) {
            return "Products / Investments / Fixed Deposit";
        }
        if (has(labels, "US Stocks")) {
            return "Products / Investments / US Stocks";
        }
        if (has(labels, "Indian Stocks")) {
            return "Products / Investments / Indian Stocks";
        }
        if (has(labels, "NPS")) {
            return "Products / Investments / NPS";
        }
        if (has(labels, "Tax Planning") || hasPrefix(labels, "File ITR") || has(labels, "Tax Filing")) {
            return "Products / Tax Filing & Planning";
        }
        return null;
    }

    private static String innerScreen(List<String> labels, String id) {
        String title = visibleTitle(labels);
        if (title.equalsIgnoreCase("Personal Details")) {
            return "Profile / Profile settings / Personal Details";
        }
        if (title.equalsIgnoreCase("Work Details") || title.equalsIgnoreCase("Work Profile")) {
            return "Profile / Profile settings / Work Details";
        }
        if (title.equalsIgnoreCase("Address Details") || title.equalsIgnoreCase("Address details")) {
            if (id.contains("home") || has(labels, "Home Loan")) {
                return "Products / Loans & Credit Cards / Home Loan / Address Details";
            }
            return "Profile / Profile settings / Address Details";
        }
        if (title.equalsIgnoreCase("Bank Account")) {
            return "Profile / Profile settings / Bank Account";
        }
        if (title.equalsIgnoreCase("Document Details")) {
            return "Profile / Profile settings / Document Details";
        }
        if (title.toLowerCase(Locale.ROOT).startsWith("kyc details")) {
            return "Profile / Profile settings / KYC Details";
        }
        if (title.equalsIgnoreCase("Transaction history") || title.equalsIgnoreCase("Transaction History")) {
            return "Profile / Profile settings / Transaction history";
        }
        if (title.equalsIgnoreCase("Contact Us")) {
            return "Profile / Profile settings / Contact Us";
        }
        if (title.equalsIgnoreCase("How can we help you") || title.equalsIgnoreCase("How can we help you?")) {
            return "Profile / Profile settings / How can we help you";
        }
        if (title.equalsIgnoreCase("Screen Lock")) {
            return "Profile / General settings / Screen Lock";
        }
        if (title.equalsIgnoreCase("Change PIN")) {
            return "Profile / General settings / Change PIN";
        }
        if (title.equalsIgnoreCase("Utilities")) {
            return "Profile / General settings / Utilities";
        }
        if (title.equalsIgnoreCase("Terms & Conditions") || title.equalsIgnoreCase("Terms and Conditions")) {
            return "Profile / General settings / Terms & Conditions";
        }
        if (title.equalsIgnoreCase("Privacy Policy")) {
            return "Profile / General settings / Privacy Policy";
        }
        if (title.equalsIgnoreCase("Account Management")) {
            return "Profile / General settings / Account Management";
        }
        if (title.equalsIgnoreCase("Rate Us")) {
            return "Profile / General settings / Rate Us";
        }
        if (title.equalsIgnoreCase("FAQs")) {
            return "Profile / General settings / FAQs";
        }
        if (title.equalsIgnoreCase("About airpay money")) {
            return "Profile / General settings / About airpay money";
        }
        if (title.equalsIgnoreCase("Reassess")) {
            return "FinSight / Reassess";
        }
        if (title.equalsIgnoreCase("View Details") || title.equalsIgnoreCase("View details")) {
            return "FinSight / View Details";
        }
        if (title.equalsIgnoreCase("Overview")) {
            return "FinSight / Overview";
        }
        if (title.equalsIgnoreCase("Take Actions") || title.equalsIgnoreCase("Take Action")) {
            return "FinSight / Take Actions";
        }
        if (title.toLowerCase(Locale.ROOT).startsWith("what is") && containsText(labels, "Investment")) {
            return "Products / Investments / What is an investment";
        }
        return null;
    }

    private static List<String> contentLabels(Path xml) {
        List<String> labels = new ArrayList<>();
        try {
            if (!Files.isRegularFile(xml)) {
                return labels;
            }
            Matcher matcher = Pattern.compile("content-desc=\"([^\"]*)\"").matcher(Files.readString(xml));
            while (matcher.find()) {
                String decoded = matcher.group(1)
                        .replace("&amp;", "&")
                        .replace("&#10;", "\n")
                        .replace("&lt;", "<")
                        .replace("&gt;", ">");
                if (decoded.isBlank()) {
                    continue;
                }
                String first = decoded.split("\n", 2)[0].trim();
                if (!first.isBlank()) {
                    labels.add(first);
                }
            }
        } catch (Exception ignored) {
            return labels;
        }
        return labels;
    }

    private static String visibleTitle(List<String> labels) {
        for (String label : labels) {
            String name = label.toLowerCase(Locale.ROOT);
            if (name.equals("back") || name.equals("close") || name.equals("search")
                    || name.equals("drag handle") || name.equals("menu option") || name.equals("notifications")) {
                continue;
            }
            return label;
        }
        return labels.get(0);
    }

    private static boolean isDeviceHome(List<String> labels) {
        for (String label : labels) {
            if (label.startsWith("Home screen Page")) {
                return true;
            }
        }
        return has(labels, "Amazon") && has(labels, "PhonePe");
    }

    private static int countAny(List<String> labels, String... keys) {
        int count = 0;
        for (String key : keys) {
            if (has(labels, key) || hasPrefix(labels, key)) {
                count++;
            }
        }
        return count;
    }

    private static String firstMatch(List<String> labels, String... keys) {
        for (String key : keys) {
            if (has(labels, key)) {
                return key;
            }
        }
        return null;
    }

    private static boolean has(List<String> labels, String expected) {
        for (String label : labels) {
            if (label.equalsIgnoreCase(expected)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasPrefix(List<String> labels, String prefix) {
        String needle = prefix.toLowerCase(Locale.ROOT);
        for (String label : labels) {
            if (label.toLowerCase(Locale.ROOT).startsWith(needle)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsText(List<String> labels, String part) {
        String needle = part.toLowerCase(Locale.ROOT);
        for (String label : labels) {
            if (label.toLowerCase(Locale.ROOT).contains(needle)) {
                return true;
            }
        }
        return false;
    }

    private static String withScroll(String name, String id) {
        String hint = scrollHint(id);
        if (hint.isBlank() || name.toLowerCase(Locale.ROOT).contains(hint)) {
            return name;
        }
        return name + " / " + hint;
    }

    private static String calcName(String id) {
        String[][] tiles = {
                {"terminsurance", "Term Insurance Calculator"},
                {"buyvsrent", "Buy vs Rent Calculator"},
                {"crorepati", "Crorepati Calculator"},
                {"retirement", "Retirement Calculator"},
                {"inflation", "Inflation Calculator"},
                {"lumpsum", "Lumpsum Calculator"},
                {"budget", "Budget Calculator"},
                {"emi", "EMI Calculator"},
                {"sip", "SIP Calculator"},
                {"ppf", "PPF Calculator"},
                {"epf", "EPF Calculator"},
                {"nps", "NPS Calculator"},
                {"fd", "FD Calculator"}
        };
        for (String[] tile : tiles) {
            if (id.contains("-" + tile[0])) {
                return join("Calculators / " + tile[1], detail(id, "calc-" + tile[0]));
            }
        }
        return join("Calculators", scrollHint(id));
    }

    private static String finsightScreen(String id) {
        if (id.contains("reassess")) {
            return "Reassess";
        }
        if (id.contains("view-detail") || id.contains("viewdetail")) {
            return "View Details";
        }
        if (id.contains("overview")) {
            return "Overview";
        }
        if (id.contains("health-score")) {
            return "Financial Health Score";
        }
        if (id.contains("health")) {
            return "Health Insurance";
        }
        if (id.contains("take-action")) {
            return "Take Actions";
        }
        if (id.contains("income")) {
            return "Income & Expense";
        }
        if (id.contains("dependant")) {
            return "Dependant";
        }
        if (id.contains("insurance")) {
            return "Insurance Details";
        }
        if (id.contains("saving") || id.contains("investment")) {
            return "Savings and Investments";
        }
        if (id.contains("risk")) {
            return "Risk Profile";
        }
        if (id.contains("diag") || id.contains("diagnosis")) {
            return "Diagnosis";
        }
        if (id.contains("plan")) {
            return "Plan of Action";
        }
        if (id.contains("net-worth") || id.contains("networth")) {
            return "Net Worth";
        }
        if (id.contains("entry") || id.contains("dob") || id.contains("pincode")) {
            return "Personal Details";
        }
        return "FinSight";
    }

    private static String insuranceScreen(String id) {
        if (id.contains("2w") || id.contains("2-wheeler")) {
            return "Products / Insurance / 2 Wheeler";
        }
        if (id.contains("4w") || id.contains("4-wheeler")) {
            return "Products / Insurance / 4 Wheeler";
        }
        if (id.contains("health")) {
            return "Products / Insurance / Health";
        }
        if (id.contains("life")) {
            return "Products / Insurance / Life";
        }
        return "Products / Insurance";
    }

    private static String loanScreen(String id) {
        if (id.contains("gold")) {
            return "Products / Loans & Credit Cards / Gold Loan";
        }
        if (id.contains("credit")) {
            return "Products / Loans & Credit Cards / Credit Cards";
        }
        if (id.contains("personal")) {
            return "Products / Loans & Credit Cards / Personal Loan";
        }
        if (id.contains("home")) {
            return "Products / Loans & Credit Cards / Home Loan";
        }
        if (id.contains("lap") || id.contains("against") || id.contains("property")) {
            return "Products / Loans & Credit Cards / Loan Against Property";
        }
        return "Products / Loans & Credit Cards";
    }

    private static String productLive(String id) {
        String rest = id.substring("prod-".length());
        if (rest.startsWith("mutual")) {
            return join("Products / Investments / Mutual Funds", detail(rest, "mutual-funds"));
        }
        if (rest.startsWith("nps")) {
            return join("Products / Investments / NPS", detail(rest, "nps"));
        }
        if (rest.startsWith("digital-gold") || rest.startsWith("gold")) {
            return join("Products / Investments / Digital Gold", detail(rest, "digital-gold"));
        }
        if (rest.startsWith("fixed")) {
            return join("Products / Investments / Fixed Deposit", detail(rest, "fixed-deposit"));
        }
        if (rest.startsWith("us-stock") || rest.startsWith("us")) {
            return join("Products / Investments / US Stocks", detail(rest, "us-stocks"));
        }
        if (rest.startsWith("indian")) {
            return join("Products / Investments / Indian Stocks", detail(rest, "indian-stocks"));
        }
        if (rest.contains("2-wheeler") || rest.contains("2 wheeler")) {
            return "Products / Insurance / 2 Wheeler";
        }
        if (rest.contains("4-wheeler")) {
            return "Products / Insurance / 4 Wheeler";
        }
        if (rest.startsWith("health")) {
            return "Products / Insurance / Health";
        }
        if (rest.startsWith("life")) {
            return "Products / Insurance / Life";
        }
        if (rest.contains("gold-loan")) {
            return "Products / Loans & Credit Cards / Gold Loan";
        }
        if (rest.contains("credit")) {
            return "Products / Loans & Credit Cards / Credit Cards";
        }
        if (rest.contains("personal")) {
            return "Products / Loans & Credit Cards / Personal Loan";
        }
        if (rest.contains("home-loan") || rest.startsWith("home")) {
            return "Products / Loans & Credit Cards / Home Loan";
        }
        if (rest.contains("property") || rest.contains("lap")) {
            return "Products / Loans & Credit Cards / Loan Against Property";
        }
        if (rest.contains("tax")) {
            return "Products / Tax Filing & Planning";
        }
        return "Products";
    }

    private static String join(String path, String... extras) {
        String result = path;
        if (extras == null) {
            return result;
        }
        for (String extra : extras) {
            if (extra == null || extra.isBlank()
                    || result.toLowerCase(Locale.ROOT).contains(extra.toLowerCase(Locale.ROOT))) {
                continue;
            }
            result = result + " / " + extra;
        }
        return result;
    }

    private static String scrollHint(String id) {
        if (id.contains("scroll")) {
            return "scrolled";
        }
        if (id.contains("end")) {
            return "end";
        }
        return "";
    }

    private static String detail(String id, String prefix) {
        String rest = id;
        int at = rest.indexOf(prefix);
        if (at >= 0) {
            rest = rest.substring(at + prefix.length());
        }
        rest = rest.replace('-', ' ').replaceAll("\\b\\d+[a-z]?\\b", " ");
        rest = rest.replaceAll("\\b(root|hub|top|now|fresh|probe)\\b", " ");
        if (rest.contains("scroll")) {
            rest = rest.replaceAll("\\bscroll\\w*", "scrolled");
        }
        rest = rest.replaceAll("\\s+", " ").trim();
        if (rest.isBlank()) {
            return "";
        }
        return titleCase(rest);
    }

    private static String titleCase(String text) {
        StringBuilder sb = new StringBuilder();
        for (String word : text.split(" ")) {
            if (word.isBlank()) {
                continue;
            }
            if (!sb.isEmpty()) {
                sb.append(' ');
            }
            if (word.equalsIgnoreCase("nps") || word.equalsIgnoreCase("emi") || word.equalsIgnoreCase("sip")
                    || word.equalsIgnoreCase("kyc") || word.equalsIgnoreCase("fd") || word.equalsIgnoreCase("faq")
                    || word.equalsIgnoreCase("faqs") || word.equalsIgnoreCase("ifsc") || word.equalsIgnoreCase("pan")
                    || word.equalsIgnoreCase("us") || word.equalsIgnoreCase("fhc")) {
                sb.append(word.toUpperCase(Locale.ROOT));
            } else {
                sb.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    sb.append(word.substring(1));
                }
            }
        }
        return sb.toString();
    }
}
