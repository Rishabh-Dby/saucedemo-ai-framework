package com.rishabh.framework.listeners;

import com.rishabh.framework.ai.FailureSummaryGenerator;
import com.rishabh.framework.ai.HealingStore;
import com.rishabh.framework.driver.DriverManager;
import com.rishabh.framework.utils.ScreenshotUtils;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AIFailureSummaryListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Path screenshot = ScreenshotUtils.capture(DriverManager.getDriver(), result.getMethod().getMethodName());
        String summary = FailureSummaryGenerator.generate(result, result.getThrowable(), HealingStore.getReports());
        writeSummary(result.getMethod().getMethodName(), summary + "Screenshot: " + screenshot.toAbsolutePath());
        System.out.println("\n" + summary + "Screenshot: " + screenshot.toAbsolutePath() + "\n");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (!HealingStore.getReports().isEmpty()) {
            StringBuilder builder = new StringBuilder("Healing Report for ")
                    .append(result.getMethod().getMethodName())
                    .append(System.lineSeparator());
            HealingStore.getReports().forEach(report -> builder
                    .append(report.elementName())
                    .append(" healed from ")
                    .append(report.originalLocator())
                    .append(" to ")
                    .append(report.healedLocator())
                    .append(System.lineSeparator()));
            writeSummary(result.getMethod().getMethodName() + "_healing", builder.toString());
        }
    }

    private void writeSummary(String filePrefix, String content) {
        try {
            Path outputDir = Path.of("test-output", "ai-summary");
            Files.createDirectories(outputDir);
            Files.writeString(outputDir.resolve(filePrefix + ".txt"), content);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write AI summary", e);
        }
    }
}
