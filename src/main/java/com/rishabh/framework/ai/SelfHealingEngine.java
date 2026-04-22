package com.rishabh.framework.ai;

import com.rishabh.framework.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class SelfHealingEngine {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public SelfHealingEngine(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getLong("explicit.wait.seconds")));
    }

    public WebElement findElement(String elementName, List<LocatorCandidate> candidates) {
        List<String> attempts = new ArrayList<>();
        boolean healingEnabled = ConfigReader.getBoolean("heal.enabled");
        int index = 0;

        for (LocatorCandidate candidate : candidates) {
            attempts.add(candidate.toString());
            try {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(candidate.by()));
                if (index > 0 && healingEnabled) {
                    HealingStore.add(new HealingReport(
                            elementName,
                            candidates.get(0).by().toString(),
                            candidate.by().toString(),
                            attempts,
                            Instant.now(),
                            driver.getCurrentUrl()
                    ));
                }
                return element;
            } catch (TimeoutException | NoSuchElementException ignored) {
                index++;
            }
        }

        throw new NoSuchElementException("Unable to find element '" + elementName + "'. Attempted locators: " + attempts);
    }

    public List<LocatorCandidate> candidates(By primary, By... fallbacks) {
        List<LocatorCandidate> all = new ArrayList<>();
        all.add(new LocatorCandidate("primary", primary));
        for (int i = 0; i < fallbacks.length; i++) {
            all.add(new LocatorCandidate("fallback-" + (i + 1), fallbacks[i]));
        }
        return all;
    }
}
