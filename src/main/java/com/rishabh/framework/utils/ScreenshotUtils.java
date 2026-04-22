package com.rishabh.framework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    public static Path capture(WebDriver driver, String testName) {
        try {
            Path outputDir = Path.of("test-output", "screenshots");
            Files.createDirectories(outputDir);
            Path destination = outputDir.resolve(testName + "_" + LocalDateTime.now().format(FORMATTER) + ".png");
            Path source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return destination;
        } catch (IOException e) {
            throw new RuntimeException("Unable to save screenshot", e);
        }
    }
}
