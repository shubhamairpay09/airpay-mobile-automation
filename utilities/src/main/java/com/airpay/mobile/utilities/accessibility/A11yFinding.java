package com.airpay.mobile.utilities.accessibility;

import java.util.List;

public record A11yFinding(
        String module,
        String pageId,
        String pageLabel,
        String route,
        String ruleId,
        String impact,
        String description,
        String help,
        String helpUrl,
        List<String> wcagTags,
        String field,
        String selector,
        String htmlSnippet,
        String failureSummary,
        String expected,
        String actual,
        String status
) {
}
