package com.airpay.mobile.utilities.locators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Parses UiAutomator page-source XML to named XPath hints (used by a11y scanner unit tests).
 * Locators for automation live in Java {@code *Screen} classes — no JSON locator files.
 */
public final class PageXpathStore {

    private static final Logger LOG = LogManager.getLogger(PageXpathStore.class);

    private PageXpathStore() {
    }

    /** Named XPath for each labelled node on one screen. */
    public static Map<String, String> namedXpaths(String xml) {
        return extract(xml);
    }

    static Map<String, String> extract(String xml) {
        Map<String, String> out = new LinkedHashMap<>();
        if (xml == null || xml.isBlank()) {
            return out;
        }
        try {
            int unnamed = 0;
            var nodes = UiAutomatorXml.elements(xml);
            for (Element el : nodes) {
                String cls = UiAutomatorXml.className(el);
                String desc = UiAutomatorXml.attr(el, "content-desc");
                String text = UiAutomatorXml.attr(el, "text");
                String hint = UiAutomatorXml.attr(el, "hint");
                String rid = UiAutomatorXml.attr(el, "resource-id");
                boolean hasName = !desc.isBlank() || !hint.isBlank() || !text.isBlank() || !rid.isBlank();
                if (!hasName) {
                    continue;
                }
                if (!rid.isBlank() && rid.startsWith("android:id/") && desc.isBlank() && text.isBlank() && hint.isBlank()) {
                    continue;
                }
                String xpath = buildXpath(cls, rid, desc, text, hint);
                String key = nameKey(desc, text, hint, rid, cls, ++unnamed);
                if (key.startsWith("unnamed") || key.matches(".*\\d{6,}.*")) {
                    continue;
                }
                out.putIfAbsent(key, xpath);
            }
            LOG.debug("Page-source extract: {} hierarchy nodes → {} named XPaths", nodes.size(), out.size());
        } catch (Exception e) {
            LOG.warn("Page-source parse failed: {}", e.toString());
        }
        return out;
    }

    private static String buildXpath(String cls, String rid, String desc, String text, String hint) {
        String simpleClass = cls.isBlank() ? "*" : cls;
        if (!desc.isBlank()) {
            String first = desc.split("\\n")[0].trim();
            return "//" + simpleClass + "[contains(@content-desc," + quote(first) + ")]";
        }
        if (!hint.isBlank()) {
            return "//" + simpleClass + "[@hint=" + quote(hint) + "]";
        }
        if (!text.isBlank()) {
            return "//" + simpleClass + "[@text=" + quote(text) + "]";
        }
        if (!rid.isBlank()) {
            return "//" + simpleClass + "[@resource-id=" + quote(rid) + "]";
        }
        return "//" + simpleClass;
    }

    private static String nameKey(String desc, String text, String hint, String rid, String cls, int n) {
        String raw = firstNonBlank(desc, text, hint, rid);
        if (raw.isBlank()) {
            String shortClass = cls.contains(".") ? cls.substring(cls.lastIndexOf('.') + 1) : cls;
            return camel("unnamed-" + shortClass + "-" + n);
        }
        return camel(raw.split("\\n")[0].trim());
    }

    private static String camel(String raw) {
        String cleaned = raw.replaceAll("[^A-Za-z0-9]+", " ").trim();
        if (cleaned.isBlank()) {
            return "element";
        }
        String[] parts = cleaned.split("\\s+");
        StringBuilder sb = new StringBuilder(parts[0].toLowerCase());
        for (int i = 1; i < parts.length && i < 8; i++) {
            String p = parts[i];
            sb.append(Character.toUpperCase(p.charAt(0))).append(p.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    private static String firstNonBlank(String... values) {
        return UiAutomatorXml.firstNonBlank(values);
    }

    private static String quote(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }
}
