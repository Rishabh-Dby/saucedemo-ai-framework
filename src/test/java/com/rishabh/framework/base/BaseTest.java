package com.rishabh.framework.base;

import com.rishabh.framework.ai.HealingStore;
import com.rishabh.framework.driver.DriverManager;
import com.rishabh.framework.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
        getDriver().get(ConfigReader.get("base.url"));
        HealingStore.clear();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
        HealingStore.clear();
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
