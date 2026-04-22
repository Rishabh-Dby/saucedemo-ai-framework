package com.rishabh.framework.pages;

import com.rishabh.framework.ai.SelfHealingEngine;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    private final WebDriver driver;
    private final SelfHealingEngine healingEngine;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.healingEngine = new SelfHealingEngine(driver);
    }

    public boolean isLoaded() {
        return healingEngine.findElement(
                "Inventory Container",
                healingEngine.candidates(
                        By.id("inventory_container"),
                        By.cssSelector(".inventory_container")
                )
        ).isDisplayed();
    }

    public String getTitleText() {
        return healingEngine.findElement(
                "Page Title",
                healingEngine.candidates(
                        By.cssSelector(".title"),
                        By.className("title")
                )
        ).getText();
    }

    public void addBackpackToCart() {
        healingEngine.findElement(
                "Add Backpack To Cart Button",
                healingEngine.candidates(
                        By.id("add-to-cart-sauce-labs-backpack"),
                        By.cssSelector("button[data-test='add-to-cart-sauce-labs-backpack']")
                )
        ).click();
    }

    public void removeBackpackFromCart() {
        healingEngine.findElement(
                "Remove Backpack Button",
                healingEngine.candidates(
                        By.id("remove-sauce-labs-backpack"),
                        By.cssSelector("button[data-test='remove-sauce-labs-backpack']")
                )
        ).click();
    }

    public String getCartBadgeCount() {
        return healingEngine.findElement(
                "Cart Badge",
                healingEngine.candidates(
                        By.cssSelector(".shopping_cart_badge"),
                        By.className("shopping_cart_badge")
                )
        ).getText();
    }

    public boolean isCartBadgeDisplayed() {
        try {
            return healingEngine.findElement(
                    "Cart Badge",
                    healingEngine.candidates(
                            By.cssSelector(".shopping_cart_badge"),
                            By.className("shopping_cart_badge")
                    )
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFirstProductName() {
        return healingEngine.findElement(
                "First Product Name",
                healingEngine.candidates(
                        By.cssSelector(".inventory_item_name"),
                        By.xpath("(//div[@class='inventory_item_name'])[1]")
                )
        ).getText();
    }

    public void openCart() {
        healingEngine.findElement(
                "Cart Icon",
                healingEngine.candidates(
                        By.id("shopping_cart_container"),
                        By.cssSelector(".shopping_cart_link"),
                        By.xpath("//a[@class='shopping_cart_link']")
                )
        ).click();
    }

    public void logout() {
        healingEngine.findElement(
                "Menu Button",
                healingEngine.candidates(
                        By.id("react-burger-menu-btn"),
                        By.cssSelector("#react-burger-menu-btn")
                )
        ).click();

        healingEngine.findElement(
                "Logout Link",
                healingEngine.candidates(
                        By.id("logout_sidebar_link"),
                        By.cssSelector("#logout_sidebar_link")
                )
        ).click();
    }
}