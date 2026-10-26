package com.airpay.mobile.utilities.locators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UiAutomator2 page source uses class names as XML tags
 * ({@code <android.widget.Button .../>}), not {@code <node class="..."/>}.
 */
public final class UiAutomatorXml {

    private static final Logger LOG = LogManager.getLogger(UiAutomatorXml.class);
    private static final Pattern TAG = Pattern.compile("<([A-Za-z_][A-Za-z0-9._]*)([^>]*?)(/?)>");
    private static final Pattern ATTR = Pattern.compile("([A-Za-z_:][A-Za-z0-9_:-]*)=\"([^\"]*)\"");

    private UiAutomatorXml() {
    }

    public static List<Element> elements(String xml) {
        List<Element> fromDom = parseDom(xml);
        if (!fromDom.isEmpty()) {
            return fromDom;
        }
        return parseRegexAsDom(xml);
    }

    public static String className(Element el) {
        return firstNonBlank(attr(el, "class"), el.getTagName());
    }

    public static String attr(Element el, String name) {
        String v = el.getAttribute(name);
        return v == null ? "" : v.trim();
    }

    public static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v.trim();
            }
        }
        return "";
    }

    static List<Map<String, String>> extractAttributeMaps(String xml) {
        List<Map<String, String>> out = new ArrayList<>();
        if (xml == null || xml.isBlank()) {
            return out;
        }
        Matcher tag = TAG.matcher(xml);
        while (tag.find()) {
            String tagName = tag.group(1);
            if ("hierarchy".equalsIgnoreCase(tagName) || "?xml".equalsIgnoreCase(tagName)) {
                continue;
            }
            Map<String, String> attrs = new LinkedHashMap<>();
            attrs.put("tag", tagName);
            Matcher am = ATTR.matcher(tag.group(2));
            while (am.find()) {
                attrs.put(am.group(1), unescapeXml(am.group(2)));
            }
            if (!attrs.containsKey("class") || attrs.get("class").isBlank()) {
                attrs.put("class", tagName);
            }
            out.add(attrs);
        }
        return out;
    }

    private static List<Element> parseDom(String xml) {
        List<Element> list = new ArrayList<>();
        if (xml == null || xml.isBlank()) {
            return list;
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            try {
                factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            } catch (Exception ignored) {
                // parser features vary by JDK
            }
            Document doc = factory.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(sanitize(xml).getBytes(StandardCharsets.UTF_8)));
            NodeList nodes = doc.getElementsByTagName("*");
            for (int i = 0; i < nodes.getLength(); i++) {
                if (nodes.item(i) instanceof Element el
                        && !"hierarchy".equalsIgnoreCase(el.getTagName())) {
                    list.add(el);
                }
            }
        } catch (Exception e) {
            LOG.warn("UiAutomator DOM parse failed ({}). Will try regex fallback. xmlHead={}",
                    e.toString(), head(xml));
        }
        return list;
    }

    private static List<Element> parseRegexAsDom(String xml) {
        List<Element> list = new ArrayList<>();
        if (xml == null || xml.isBlank()) {
            return list;
        }
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            for (Map<String, String> attrs : extractAttributeMaps(xml)) {
                String tag = attrs.getOrDefault("tag", "node");
                String safeTag = tag.replaceAll("[^A-Za-z0-9._-]", "_");
                if (safeTag.isBlank()) {
                    safeTag = "node";
                }
                Element el = doc.createElement(safeTag);
                for (Map.Entry<String, String> e : attrs.entrySet()) {
                    if ("tag".equals(e.getKey())) {
                        continue;
                    }
                    try {
                        el.setAttribute(e.getKey(), e.getValue());
                    } catch (Exception ignored) {
                        // skip illegal attribute names
                    }
                }
                list.add(el);
            }
            LOG.info("UiAutomator regex fallback parsed {} nodes", list.size());
        } catch (Exception e) {
            LOG.warn("UiAutomator regex fallback failed: {}", e.toString());
        }
        return list;
    }

    private static String sanitize(String xml) {
        return xml.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F]", "");
    }

    private static String unescapeXml(String raw) {
        return raw.replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&apos;", "'");
    }

    private static String head(String xml) {
        String t = xml.replaceAll("\\s+", " ").trim();
        return t.length() > 180 ? t.substring(0, 180) : t;
    }
}
