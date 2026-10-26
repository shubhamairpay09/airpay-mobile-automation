package com.airpay.mobile.utilities.accessibility;

import java.util.List;

public record A11yPageScanResult(
        A11yPageRef page,
        String url,
        int score,
        int violationsCount,
        int passesCount,
        int incompleteCount,
        List<A11yFinding> findings,
        List<A11yFinding> incompleteFindings,
        List<String> passedRuleIds,
        String error
) {
    public A11yPageScanResult {
        findings = findings == null ? List.of() : List.copyOf(findings);
        incompleteFindings = incompleteFindings == null ? List.of() : List.copyOf(incompleteFindings);
        passedRuleIds = passedRuleIds == null ? List.of() : List.copyOf(passedRuleIds);
    }

    public boolean hasError() {
        return error != null && !error.isBlank();
    }

    public boolean passedGate() {
        return !hasError() && findings.stream().noneMatch(f ->
                "critical".equalsIgnoreCase(f.impact()) || "serious".equalsIgnoreCase(f.impact()));
    }
}
