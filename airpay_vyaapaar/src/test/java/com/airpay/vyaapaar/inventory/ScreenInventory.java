package com.airpay.vyaapaar.inventory;

import com.airpay.mobile.utilities.config.ConfigLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Writes one Java screen class per captured page and one Cucumber feature per module.
 */
public final class ScreenInventory {

    private static final Logger LOG = LogManager.getLogger(ScreenInventory.class);

    private static final Set<String> JAVA_KEYWORDS = Set.of(
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
            "volatile", "while", "var", "record", "yield", "sealed", "permits", "true", "false", "null");

    private final Map<String, ModulePages> modules = new LinkedHashMap<>();

    public void add(String module, String screenId, String label, Map<String, String> xpaths) {
        String mod = moduleKey(module);
        ModulePages pages = modules.computeIfAbsent(mod, ModulePages::new);
        Page page = pages.pages.computeIfAbsent(screenId, id -> new Page(id, label, className(id, pages.usedClasses)));
        if (label != null && !label.isBlank()) {
            page.label = label;
        }
        if (xpaths != null) {
            for (Map.Entry<String, String> entry : xpaths.entrySet()) {
                if (entry.getValue() == null || entry.getValue().isBlank()) {
                    continue;
                }
                if (entry.getValue().contains("action_bar") || entry.getValue().contains("android:id")) {
                    continue;
                }
                page.xpaths.putIfAbsent(entry.getKey(), entry.getValue());
            }
        }
        writePage(pages, page);
        writeFeature(pages);
    }

    public int pageCount() {
        return modules.values().stream().mapToInt(m -> m.pages.size()).sum();
    }

    public boolean hasPages() {
        return pageCount() > 0;
    }

    private void writePage(ModulePages module, Page page) {
        writeInventory(javaRoot().resolve(module.packageName), page.className + ".java",
                renderJava(module, page));
    }

    private void writeFeature(ModulePages module) {
        Path file = featureRoot().resolve(module.featureName + ".feature");
        writeInventory(file.getParent(), file.getFileName().toString(), renderFeature(module));
    }

    /** Inventory writes never fail the scan: one retry after a pause, then a warning with the cause. */
    private void writeInventory(Path dir, String name, String content) {
        Path file = dir.resolve(name);
        try {
            Files.createDirectories(dir);
            Files.writeString(file, content);
            return;
        } catch (IOException first) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
            }
            try {
                Files.writeString(file, content);
            } catch (IOException retry) {
                LOG.warn("Screen inventory: could not write {} — scan continues", file, retry);
            }
        }
    }

    private String renderJava(ModulePages module, Page page) {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.airpay.vyaapaar.screens.").append(module.packageName).append(";\n\n");
        sb.append("import com.airpay.vyaapaar.screens.base.BaseScreen;\n");
        sb.append("import com.airpay.mobile.utilities.context.TestContext;\n");
        sb.append("import io.appium.java_client.pagefactory.AndroidFindBy;\n");
        sb.append("import org.openqa.selenium.WebElement;\n\n");
        sb.append("/** ").append(javadoc(page.label)).append(". Module ").append(module.module).append(". */\n");
        sb.append("public class ").append(page.className).append(" extends BaseScreen {\n\n");
        sb.append("    public static final String PAGE_ID = \"").append(escape(page.screenId)).append("\";\n\n");
        Set<String> fields = new java.util.LinkedHashSet<>();
        for (Map.Entry<String, String> entry : page.xpaths.entrySet()) {
            String field = fieldName(entry.getKey(), fields);
            String constant = field.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase(Locale.ROOT);
            sb.append("    public static final String ").append(constant).append(" = ")
                    .append(javaString(entry.getValue())).append(";\n\n");
            sb.append("    @AndroidFindBy(xpath = ").append(constant).append(")\n");
            sb.append("    private WebElement ").append(field).append(";\n\n");
        }
        sb.append("    public ").append(page.className).append("(TestContext context) {\n");
        sb.append("        super(context);\n");
        sb.append("    }\n");
        sb.append("}\n");
        return sb.toString();
    }

    private String renderFeature(ModulePages module) {
        StringBuilder sb = new StringBuilder();
        sb.append("@airpay-vyaapaar @vyaapaar @android @a11y @").append(module.featureName).append("\n");
        sb.append("Feature: airpay vyaapaar ").append(module.module).append(" screens\n");
        sb.append("  Xpaths for this module live in screens/").append(module.packageName).append("/.\n");
        sb.append("  Each screen was captured once during the deep scan.\n\n");
        int n = 1;
        for (Page page : module.pages.values()) {
            String tag = module.featureName.toUpperCase(Locale.ROOT).replace('-', '_') + "_"
                    + String.format("%03d", n++);
            sb.append("  @").append(tag).append("\n");
            sb.append("  Scenario: ").append(oneLine(page.label)).append("\n");
            sb.append("    Given the airpay vyaapaar app is installed on the device\n");
            sb.append("    Then airpay vyaapaar screen \"").append(escape(page.screenId))
                    .append("\" in module \"").append(module.packageName).append("\" has stored xpaths\n\n");
        }
        return sb.toString();
    }

    private static String fieldName(String raw, Set<String> used) {
        String name = raw == null ? "" : raw.replaceAll("[^A-Za-z0-9]", "");
        if (name.isBlank()) {
            name = "element";
        }
        name = name.substring(0, 1).toLowerCase(Locale.ROOT) + name.substring(1);
        if (JAVA_KEYWORDS.contains(name) || !Character.isJavaIdentifierStart(name.charAt(0))) {
            name = "el" + name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1);
        }
        String unique = name;
        int i = 2;
        while (!used.add(unique)) {
            unique = name + i++;
        }
        return unique;
    }

    private static String className(String screenId, Set<String> used) {
        String[] parts = screenId.split("[^A-Za-z0-9]+");
        StringBuilder sb = new StringBuilder();
        int words = 0;
        for (String part : parts) {
            if (part.isBlank() || words >= 6) {
                continue;
            }
            sb.append(Character.toUpperCase(part.charAt(0)));
            if (part.length() > 1) {
                sb.append(part.substring(1).toLowerCase(Locale.ROOT));
            }
            words++;
        }
        if (sb.isEmpty()) {
            sb.append("Page");
        }
        String base = sb + "Screen";
        String name = base;
        int n = 2;
        while (!used.add(name)) {
            name = base.substring(0, base.length() - "Screen".length()) + (n++) + "Screen";
        }
        return name;
    }

    private static String moduleKey(String module) {
        String slug = module == null ? "" : module.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug.isBlank() ? "home" : slug;
    }

    private static String javaString(String value) {
        return "\"" + value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "").replace("\n", " ") + "\"";
    }

    private static String escape(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String javadoc(String label) {
        return oneLine(label).replace("*/", "* /");
    }

    private static String oneLine(String label) {
        if (label == null || label.isBlank()) {
            return "Screen";
        }
        return label.replace('\n', ' ').replace("|", "/").trim();
    }

    private static Path javaRoot() {
        return ConfigLoader.projectRoot().resolve("airpay_vyaapaar/src/test/java/com/airpay/vyaapaar/screens");
    }

    private static Path featureRoot() {
        return ConfigLoader.projectRoot().resolve("airpay_vyaapaar/src/test/resources/features/a11y");
    }

    private static final class ModulePages {
        private final String module;
        private final String featureName;
        private final String packageName;
        private final Map<String, Page> pages = new LinkedHashMap<>();
        private final Set<String> usedClasses = new java.util.LinkedHashSet<>();

        private ModulePages(String module) {
            this.module = module;
            this.featureName = module;
            String pkg = module.replace("-", "");
            if (pkg.isBlank() || !Character.isJavaIdentifierStart(pkg.charAt(0)) || JAVA_KEYWORDS.contains(pkg)) {
                pkg = "m" + pkg;
            }
            this.packageName = pkg;
        }
    }

    private static final class Page {
        private final String screenId;
        private final String className;
        private String label;
        private final Map<String, String> xpaths = new LinkedHashMap<>();

        private Page(String screenId, String label, String className) {
            this.screenId = screenId;
            this.label = label;
            this.className = className;
        }
    }

    public List<String> screenIds() {
        List<String> ids = new ArrayList<>();
        for (ModulePages module : modules.values()) {
            ids.addAll(module.pages.keySet());
        }
        return ids;
    }
}
