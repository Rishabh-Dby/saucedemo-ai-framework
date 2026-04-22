package com.rishabh.framework.ai;

import java.time.Instant;
import java.util.List;

public record HealingReport(
        String elementName,
        String originalLocator,
        String healedLocator,
        List<String> attemptedLocators,
        Instant healedAt,
        String pageUrl
) {
}
