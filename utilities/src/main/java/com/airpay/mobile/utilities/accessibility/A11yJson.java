package com.airpay.mobile.utilities.accessibility;

import java.util.List;

public final class A11yJson {

    private A11yJson() {
    }

    public static String reportDocument(String baseUrl, String generatedAt, List<A11yPageScanResult> pages,
                                        String application) {
        int totalV = pages.stream().mapToInt(A11yPageScanResult::violationsCount).sum();
        int totalP = pages.stream().mapToInt(A11yPageScanResult::passesCount).sum();
        List<Integer> scores = pages.stream()
                .filter(p -> !p.hasError())
                .map(A11yPageScanResult::score)
                .toList();
        int worst = scores.stream().mapToInt(Integer::intValue).min().orElse(0);
        int best = scores.stream().mapToInt(Integer::intValue).max().orElse(0);
        int avg = scores.isEmpty() ? 0
                : (int) Math.round(scores.stream().mapToInt(Integer::intValue).average().orElse(0));

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  ").append(kv("timestamp", generatedAt)).append(",\n");
        sb.append("  ").append(kv("baseUrl", baseUrl)).append(",\n");
        sb.append("  ").append(kv("application", application)).append(",\n");
        sb.append("  \"summary\":{\n");
        sb.append("    \"totalPages\":").append(pages.size()).append(",\n");
        sb.append("    \"pagesWithErrors\":")
                .append(pages.stream().filter(A11yPageScanResult::hasError).count()).append(",\n");
        sb.append("    \"totalViolations\":").append(totalV).append(",\n");
        sb.append("    \"totalPasses\":").append(totalP).append(",\n");
        sb.append("    \"worstScore\":").append(worst).append(",\n");
        sb.append("    \"bestScore\":").append(best).append(",\n");
        sb.append("    \"averageScore\":").append(avg).append("\n");
        sb.append("  },\n");
        sb.append("  \"pages\":[\n");
        for (int i = 0; i < pages.size(); i++) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append("    ").append(pageResultObject(pages.get(i)));
        }
        sb.append("\n  ]\n}");
        return sb.toString();
    }

    public static String pageResultObject(A11yPageScanResult r) {
        A11yPageRef p = r.page();
        return "{"
                + kv("id", p.id()) + ","
                + kv("module", p.module()) + ","
                + kv("label", p.label()) + ","
                + kv("route", p.route()) + ","
                + kv("url", r.url()) + ","
                + "\"score\":" + r.score() + ","
                + "\"violationsCount\":" + r.violationsCount() + ","
                + "\"passesCount\":" + r.passesCount() + ","
                + "\"incompleteCount\":" + r.incompleteCount() + ","
                + kv("error", r.error() == null ? "" : r.error()) + ","
                + "\"passedRuleIds\":" + stringArray(r.passedRuleIds()) + ","
                + "\"findings\":" + findingsArray(r.findings())
                + "}";
    }

    public static String findingsArray(List<A11yFinding> findings) {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < findings.size(); i++) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append("  ").append(findingObject(findings.get(i)));
        }
        return sb.append("\n]").toString();
    }

    public static String findingObject(A11yFinding f) {
        return "{"
                + kv("module", f.module()) + ","
                + kv("pageId", f.pageId()) + ","
                + kv("pageLabel", f.pageLabel()) + ","
                + kv("route", f.route()) + ","
                + kv("ruleId", f.ruleId()) + ","
                + kv("impact", f.impact()) + ","
                + kv("description", f.description()) + ","
                + kv("help", f.help()) + ","
                + kv("helpUrl", f.helpUrl()) + ","
                + "\"wcagTags\":" + stringArray(f.wcagTags()) + ","
                + kv("field", f.field()) + ","
                + kv("selector", f.selector()) + ","
                + kv("htmlSnippet", f.htmlSnippet()) + ","
                + kv("failureSummary", f.failureSummary()) + ","
                + kv("expected", f.expected()) + ","
                + kv("actual", f.actual()) + ","
                + kv("status", f.status())
                + "}";
    }

    private static String stringArray(List<String> values) {
        if (values == null || values.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append('"').append(esc(values.get(i))).append('"');
        }
        return sb.append(']').toString();
    }

    private static String kv(String key, String value) {
        return "\"" + esc(key) + "\":\"" + esc(value) + "\"";
    }

    public static List<A11yPageScanResult> parsePages(String json) {
        if (json == null || json.isBlank()) {
            return List.of();
        }
        int pagesKey = json.indexOf("\"pages\":");
        if (pagesKey < 0) {
            return List.of();
        }
        int bracket = json.indexOf('[', pagesKey);
        if (bracket < 0) {
            return List.of();
        }
        List<A11yPageScanResult> pages = new java.util.ArrayList<>();
        int i = bracket + 1;
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == ']') {
                break;
            }
            if (c == '{') {
                int end = matchBrace(json, i);
                pages.add(parsePage(json.substring(i, end + 1)));
                i = end + 1;
                continue;
            }
            i++;
        }
        return pages;
    }

    private static A11yPageScanResult parsePage(String obj) {
        int findingsKey = obj.indexOf("\"findings\":");
        String head = findingsKey < 0 ? obj : obj.substring(0, findingsKey);
        List<A11yFinding> findings = findingsKey < 0 ? List.of() : parseFindings(obj.substring(findingsKey));
        List<String> passed = stringArrayField(head, "passedRuleIds");
        A11yPageRef page = new A11yPageRef(
                stringField(head, "id"),
                stringField(head, "module"),
                stringField(head, "label"),
                stringField(head, "route"),
                false, "screen", "", "", "high");
        return new A11yPageScanResult(
                page,
                stringField(head, "url"),
                intField(head, "score"),
                intField(head, "violationsCount"),
                intField(head, "passesCount"),
                intField(head, "incompleteCount"),
                findings,
                List.of(),
                passed,
                stringField(head, "error"));
    }

    private static List<A11yFinding> parseFindings(String fromFindingsKey) {
        int bracket = fromFindingsKey.indexOf('[');
        if (bracket < 0) {
            return List.of();
        }
        List<A11yFinding> findings = new java.util.ArrayList<>();
        int i = bracket + 1;
        while (i < fromFindingsKey.length()) {
            char c = fromFindingsKey.charAt(i);
            if (c == ']') {
                break;
            }
            if (c == '{') {
                int end = matchBrace(fromFindingsKey, i);
                findings.add(parseFinding(fromFindingsKey.substring(i, end + 1)));
                i = end + 1;
                continue;
            }
            i++;
        }
        return findings;
    }

    private static A11yFinding parseFinding(String obj) {
        return new A11yFinding(
                stringField(obj, "module"),
                stringField(obj, "pageId"),
                stringField(obj, "pageLabel"),
                stringField(obj, "route"),
                stringField(obj, "ruleId"),
                stringField(obj, "impact"),
                stringField(obj, "description"),
                stringField(obj, "help"),
                stringField(obj, "helpUrl"),
                stringArrayField(obj, "wcagTags"),
                stringField(obj, "field"),
                stringField(obj, "selector"),
                stringField(obj, "htmlSnippet"),
                stringField(obj, "failureSummary"),
                stringField(obj, "expected"),
                stringField(obj, "actual"),
                stringField(obj, "status"));
    }

    private static int matchBrace(String json, int open) {
        int depth = 0;
        boolean inString = false;
        boolean escape = false;
        for (int i = open; i < json.length(); i++) {
            char c = json.charAt(i);
            if (inString) {
                if (escape) {
                    escape = false;
                } else if (c == '\\') {
                    escape = true;
                } else if (c == '"') {
                    inString = false;
                }
                continue;
            }
            if (c == '"') {
                inString = true;
            } else if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Unclosed JSON object");
    }

    private static String stringField(String obj, String key) {
        String token = "\"" + key + "\":\"";
        int at = obj.indexOf(token);
        if (at < 0) {
            return "";
        }
        int i = at + token.length();
        StringBuilder value = new StringBuilder();
        while (i < obj.length()) {
            char c = obj.charAt(i);
            if (c == '\\' && i + 1 < obj.length()) {
                char n = obj.charAt(++i);
                value.append(switch (n) {
                    case 'n' -> '\n';
                    case 'r' -> '\r';
                    case 't' -> '\t';
                    default -> n;
                });
            } else if (c == '"') {
                break;
            } else {
                value.append(c);
            }
            i++;
        }
        return value.toString();
    }

    private static int intField(String obj, String key) {
        String token = "\"" + key + "\":";
        int at = obj.indexOf(token);
        if (at < 0) {
            return 0;
        }
        int i = at + token.length();
        int end = i;
        while (end < obj.length() && (Character.isDigit(obj.charAt(end)) || obj.charAt(end) == '-')) {
            end++;
        }
        if (end == i) {
            return 0;
        }
        return Integer.parseInt(obj.substring(i, end));
    }

    private static List<String> stringArrayField(String obj, String key) {
        String token = "\"" + key + "\":";
        int at = obj.indexOf(token);
        if (at < 0) {
            return List.of();
        }
        int bracket = obj.indexOf('[', at);
        int close = obj.indexOf(']', bracket);
        if (bracket < 0 || close < 0) {
            return List.of();
        }
        String body = obj.substring(bracket + 1, close);
        List<String> values = new java.util.ArrayList<>();
        int i = 0;
        while (i < body.length()) {
            if (body.charAt(i) == '"') {
                int end = i + 1;
                StringBuilder value = new StringBuilder();
                while (end < body.length()) {
                    char c = body.charAt(end);
                    if (c == '\\' && end + 1 < body.length()) {
                        value.append(body.charAt(++end));
                    } else if (c == '"') {
                        break;
                    } else {
                        value.append(c);
                    }
                    end++;
                }
                values.add(value.toString());
                i = end + 1;
                continue;
            }
            i++;
        }
        return values;
    }

    private static String esc(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\r", "\\r")
                .replace("\n", "\\n").replace("\t", "\\t");
    }
}
