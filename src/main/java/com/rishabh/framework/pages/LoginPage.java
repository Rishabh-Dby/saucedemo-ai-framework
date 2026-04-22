package com.rishabh.framework.pages;

import com.rishabh.framework.ai.SelfHealingEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private final WebDriver driver;
    private final SelfHealingEngine healingEngine;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.healingEngine = new SelfHealingEngine(driver);
    }

    public LoginPage enterUsername(String username) {
        WebElement userNameInput = healingEngine.findElement(
                "Username Input",
                healingEngine.candidates(
                        By.id("user-name"),
                        By.name("user-name"),
                        By.cssSelector("input[data-test='username']"),
                        By.xpath("//input[@placeholder='Username']")
                )
        );
        userNameInput.clear();
        userNameInput.sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement passwordInput = healingEngine.findElement(
                "Password Input",
                healingEngine.candidates(
                        By.id("password"),
                        By.name("password"),
                        By.cssSelector("input[data-test='password']"),
                        By.xpath("//input[@placeholder='Password']")
                )
        );
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public InventoryPage clickLogin() {
        healingEngine.findElement(
                "Login Button",
                healingEngine.candidates(
                        By.id("login-button-wrong"),
                        By.cssSelector("input[data-test='login-button']"),
                        By.xpath("//input[@type='submit']")
                )
        ).click();
        return new InventoryPage(driver);
    }

    public String getErrorMessage() {
        return healingEngine.findElement(
                "Login Error Message",
                healingEngine.candidates(
                        By.cssSelector("h3[data-test='error']"),
                        By.cssSelector("div.error-message-container h3"),
                        By.xpath("//h3[contains(text(),'Epic sadface')]")
                )
        ).getText();
    }

    public boolean isLoginButtonDisplayed() {
        return healingEngine.findElement(
                "Login Button",
                healingEngine.candidates(
                        By.id("login-button"),
                        By.cssSelector("input[data-test='login-button']"),
                        By.xpath("//input[@type='submit']")
                )
        ).isDisplayed();
    }

    public InventoryPage loginAs(String username, String password) {
        return enterUsername(username).enterPassword(password).clickLogin();
    }
}
