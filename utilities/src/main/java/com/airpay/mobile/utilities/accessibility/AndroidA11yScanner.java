package com.airpay.mobile.utilities.accessibility;

import com.airpay.mobile.utilities.config.FrameworkConfig;
import com.airpay.mobile.utilities.locators.UiAutomatorXml;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Deep native-Android accessibility scan of the UiAutomator hierarchy
 * (TalkBack-relevant names, labels, touch targets). Produces the same
 * finding/scorecard model as the web axe reports.
 */
public final class AndroidA11yScanner {

    private static final Logger LOG = LogManager.getLogger(AndroidA11yScanner.class);
    private static final Pattern BOUNDS = Pattern.compile("\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]");
    private static final double DEFAULT_DPI = 2.75; // ~xxhdpi; 48dp ≈ 132px on many devices

    private AndroidA11yScanner() {
    }

    public static A11yPageScanResult scan(AndroidDriver driver, FrameworkConfig config, A11yPageRef page) {
        try {
            String xml = driver.getPageSource();
            return scanXml(xml, config, page, currentActivity(driver));
        } catch (Exception e) {
            LOG.warn("A11y scan failed for {}: {}", page.id(), e.toString());
            return new A11yPageScanResult(page, page.route(), 0, 0, 0, 0,
                    List.of(), List.of(), List.of(), e.toString());
        }
    }

    /** Scan a UiAutomator hierarchy dump (no Appium taps). */
    public static A11yPageScanResult scanHierarchy(String xml, FrameworkConfig config, A11yPageRef page) {
        return scanXml(xml, config, page, "");
    }

    static A11yPageScanResult scanXml(String xml, FrameworkConfig config, A11yPageRef page, String activity) {
        List<A11yFinding> findings = new ArrayList<>();
        Set<String> passed = new LinkedHashSet<>();
        Map<String, Integer> nameCounts = new LinkedHashMap<>();
        int minTouchPx = minTouchPixels(config);
        int interactiveOk = 0;

        List<Element> nodes = allNodes(xml);
        for (Element el : nodes) {
            String cls = UiAutomatorXml.className(el);
            String desc = attr(el, "content-desc");
            String text = attr(el, "text");
            String hint = attr(el, "hint");
            String name = firstNonBlank(desc, text, hint);
            boolean clickable = "true".equals(attr(el, "clickable"));
            boolean checkable = "true".equals(attr(el, "checkable"));
            boolean focusable = "true".equals(attr(el, "focusable"));
            boolean enabled = !"false".equals(attr(el, "enabled"));
            boolean interactive = enabled && (clickable || checkable
                    || cls.contains("EditText") || cls.contains("Button") || cls.contains("CheckBox"));
            String xpath = selector(el);
            String snippet = snippet(el);
            String field = fieldName(name, cls);

            if (interactive && name.isBlank() && !isContainer(cls, el)) {
                findings.add(finding(page, "name-missing", "serious",
                        "Interactive control has no accessible name",
                        "Every button, link, checkbox and input must expose a name to TalkBack (contentDescription, text, or hint).",
                        "https://www.w3.org/WAI/WCAG21/Understanding/name-role-value.html",
                        List.of("wcag412", "wcag111"),
                        field, xpath, snippet,
                        "TalkBack will announce this as an unnamed control.",
                        "Accessible name via contentDescription / text / hint",
                        "No content-desc, text, or hint on " + cls));
            } else if (interactive && !name.isBlank()) {
                interactiveOk++;
                passed.add("name-missing");
                nameCounts.merge(name.split("\\n")[0].trim(), 1, Integer::sum);
            }

            if (cls.contains("EditText") && hint.isBlank() && desc.isBlank() && text.isBlank()) {
                findings.add(finding(page, "input-label", "serious",
                        "Edit field has no label or hint",
                        "Inputs must have an accessible name so users know what to enter (WCAG 1.3.1 / 3.3.2).",
                        "https://www.w3.org/WAI/WCAG21/Understanding/labels-or-instructions.html",
                        List.of("wcag131", "wcag332"),
                        field, xpath, snippet,
                        "EditText has empty hint and content-desc.",
                        "Hint or contentDescription describing the field",
                        "Empty hint and content-desc"));
            } else if (cls.contains("EditText")) {
                passed.add("input-label");
            }

            if ((cls.contains("ImageView") || cls.contains("ImageButton")) && desc.isBlank() && clickable) {
                findings.add(finding(page, "image-alt", "serious",
                        "Image control has no alternative text",
                        "Informative / interactive images must have contentDescription (WCAG 1.1.1).",
                        "https://www.w3.org/WAI/WCAG21/Understanding/non-text-content.html",
                        List.of("wcag111"),
                        field, xpath, snippet,
                        "Clickable image has empty content-desc.",
                        "Non-empty contentDescription",
                        "content-desc is empty"));
            }

            int[] box = bounds(attr(el, "bounds"));
            if (interactive && box != null) {
                int w = box[2] - box[0];
                int h = box[3] - box[1];
                if (w > 0 && h > 0 && (w < minTouchPx || h < minTouchPx) && w < 800) {
                    findings.add(finding(page, "target-size", "moderate",
                            "Touch target is smaller than 48×48 dp",
                            "Pointer targets should be at least 48×48 dp (WCAG 2.5.8 / Material).",
                            "https://www.w3.org/WAI/WCAG22/Understanding/target-size-minimum.html",
                            List.of("wcag258"),
                            field, xpath, snippet,
                            "Bounds " + w + "×" + h + " px; minimum ~" + minTouchPx + " px.",
                            "At least 48×48 dp touch target",
                            w + "×" + h + " px"));
                } else if (w >= minTouchPx && h >= minTouchPx) {
                    passed.add("target-size");
                }
            }

            if (checkable && name.isBlank()) {
                findings.add(finding(page, "checkbox-name", "serious",
                        "Checkbox has no accessible name",
                        "Checkboxes must announce their purpose and checked state (WCAG 4.1.2).",
                        "https://www.w3.org/WAI/WCAG21/Understanding/name-role-value.html",
                        List.of("wcag412"),
                        field, xpath, snippet,
                        "CheckBox content-desc/text empty.",
                        "Accessible name describing the consent / option",
                        "Unnamed checkbox"));
            }
        }

        for (Map.Entry<String, Integer> e : nameCounts.entrySet()) {
            if (e.getValue() != null && e.getValue() > 1 && e.getKey().length() > 2) {
                findings.add(finding(page, "duplicate-name", "moderate",
                        "Duplicate accessible name on the screen",
                        "Distinct controls should have unique names so TalkBack users can tell them apart (WCAG 2.4.6).",
                        "https://www.w3.org/WAI/WCAG21/Understanding/headings-and-labels.html",
                        List.of("wcag246"),
                        e.getKey(), "//*[@content-desc='" + e.getKey().replace("'", "") + "']", "",
                        e.getValue() + " controls share the name \"" + e.getKey() + "\".",
                        "Unique accessible name per control",
                        e.getValue() + " duplicates"));
            }
        }

        int violations = findings.size();
        int score = Math.max(0, 100 - weightedPenalty(findings));
        String route = activity == null || activity.isBlank() ? page.route() : activity;
        LOG.info("A11y {} | score={} | violations={} | namedInteractive={}",
                page.id(), score, violations, interactiveOk);
        return new A11yPageScanResult(page, route, score, violations, interactiveOk, 0,
                findings, List.of(), List.copyOf(passed), null);
    }

    private static int weightedPenalty(List<A11yFinding> findings) {
        int p = 0;
        for (A11yFinding f : findings) {
            p += switch (f.impact() == null ? "" : f.impact().toLowerCase()) {
                case "critical" -> 12;
                case "serious" -> 8;
                case "moderate" -> 4;
                default -> 2;
            };
        }
        return Math.min(100, p);
    }

    private static boolean isContainer(String cls, Element el) {
        if (cls.contains("FrameLayout") || cls.contains("LinearLayout") || cls.contains("RelativeLayout")
                || cls.contains("ScrollView") || cls.endsWith(".ViewGroup")) {
            return true;
        }
        int[] box = bounds(attr(el, "bounds"));
        return box != null && (box[2] - box[0]) > 900 && (box[3] - box[1]) > 400
                && attr(el, "content-desc").isBlank() && attr(el, "text").isBlank();
    }

    private static A11yFinding finding(A11yPageRef page, String rule, String impact, String description,
                                       String help, String helpUrl, List<String> tags,
                                       String field, String selector, String snippet,
                                       String failure, String expected, String actual) {
        return new A11yFinding(page.module(), page.id(), page.label(), page.route(),
                rule, impact, description, help, helpUrl, tags, field, selector, snippet,
                failure, expected, actual, "FAIL");
    }

    private static List<Element> allNodes(String xml) {
        return UiAutomatorXml.elements(xml);
    }

    private static int minTouchPixels(FrameworkConfig config) {
        int dp = config.getInt("a11y.min.touch.dp", 48);
        return (int) Math.round(dp * DEFAULT_DPI);
    }

    private static String currentActivity(AndroidDriver driver) {
        try {
            return String.valueOf(driver.currentActivity());
        } catch (Exception e) {
            return "";
        }
    }

    private static int[] bounds(String raw) {
        Matcher m = BOUNDS.matcher(raw == null ? "" : raw);
        if (!m.find()) {
            return null;
        }
        return new int[]{
                Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))
        };
    }

    private static String selector(Element el) {
        String cls = UiAutomatorXml.className(el);
        String desc = attr(el, "content-desc");
        String hint = attr(el, "hint");
        String rid = attr(el, "resource-id");
        String simple = cls.isBlank() ? "*" : cls;
        if (!rid.isBlank()) {
            return "//" + simple + "[@resource-id='" + rid.replace("'", "") + "']";
        }
        if (!desc.isBlank()) {
            String first = desc.split("\\n")[0].replace("'", "");
            return "//" + simple + "[contains(@content-desc,'" + first + "')]";
        }
        if (!hint.isBlank()) {
            return "//" + simple + "[@hint='" + hint.replace("'", "") + "']";
        }
        return "//" + simple;
    }

    private static String snippet(Element el) {
        return "<node class=\"" + UiAutomatorXml.className(el) + "\" content-desc=\"" + attr(el, "content-desc")
                + "\" hint=\"" + attr(el, "hint") + "\" text=\"" + attr(el, "text")
                + "\" clickable=\"" + attr(el, "clickable") + "\" bounds=\"" + attr(el, "bounds") + "\"/>";
    }

    private static String fieldName(String name, String cls) {
        if (name != null && !name.isBlank()) {
            return name.split("\\n")[0].trim();
        }
        return cls.contains(".") ? cls.substring(cls.lastIndexOf('.') + 1) : cls;
    }

    private static String attr(Element el, String name) {
        String v = el.getAttribute(name);
        return v == null ? "" : v.trim();
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v.trim();
            }
        }
        return "";
    }
}
