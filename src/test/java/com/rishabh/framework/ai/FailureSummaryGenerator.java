package com.rishabh.framework.ai;

import org.testng.ITestResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FailureSummaryGenerator {
    private FailureSummaryGenerator() {
    }

    public static String generate(ITestResult result, Throwable throwable, List<HealingReport> healingReports) {
        String message = throwable == null ? "Unknown error" : Objects.toString(throwable.getMessage(), "Unknown error");
        String type = throwable == null ? "UnknownException" : throwable.getClass().getSimpleName();

        String category = categorize(type, message);
        String probableReason = probableReason(category, message);
        String nextAction = nextAction(category);

        String healingSummary = healingReports.isEmpty()
                ? "No locator healing happened before failure."
                : "Locator healing activity detected: " + healingReports.stream()
                .map(report -> "[" + report.elementName() + ": " + report.originalLocator() + " -> " + report.healedLocator() + "]")
                .collect(Collectors.joining(", "));

        return "AI Failure Summary\n"
                + "Test: " + result.getMethod().getMethodName() + "\n"
                + "Failure Type: " + type + "\n"
                + "Category: " + category + "\n"
                + "Probable Reason: " + probableReason + "\n"
                + "Suggested Action: " + nextAction + "\n"
                + "Details: " + trim(message) + "\n"
                + healingSummary + "\n";
    }

    private static String categorize(String type, String message) {
        String lower = (type + " " + message).toLowerCase();
        if (lower.contains("nosuchelement") || lower.contains("unable to find element")) {
            return "Locator Failure";
        }
        if (lower.contains("timeout")) {
            return "Sync Issue";
        }
        if (lower.contains("assert") || lower.contains("expected")) {
            return "Assertion Failure";
        }
        if (lower.contains("stale")) {
            return "DOM Refresh Issue";
        }
        return "General UI Failure";
    }

    private static String probableReason(String category, String message) {
        return switch (category) {
            case "Locator Failure" -> "Element locator likely changed or element is not rendered in the expected state.";
            case "Sync Issue" -> "Page or element took longer than expected to become ready.";
            case "Assertion Failure" -> "Application behavior differs from expected result in the test.";
            case "DOM Refresh Issue" -> "The page DOM refreshed and the stored element reference became invalid.";
            default -> "Review stack trace and screenshot to confirm the exact UI state. Raw hint: " + trim(message);
        };
    }

    private static String nextAction(String category) {
        return switch (category) {
            case "Locator Failure" -> "Check page object locators, prefer stable attributes, and add a fallback locator if needed.";
            case "Sync Issue" -> "Use explicit waits around the slow interaction instead of sleep().";
            case "Assertion Failure" -> "Validate test data and confirm whether the product behavior changed intentionally.";
            case "DOM Refresh Issue" -> "Re-locate the element after page updates and avoid storing stale references for too long.";
            default -> "Open the screenshot, inspect logs, and replay the test step-by-step.";
        };
    }

    private static String trim(String message) {
        return message == null ? "" : message.substring(0, Math.min(message.length(), 300));
    }
}
