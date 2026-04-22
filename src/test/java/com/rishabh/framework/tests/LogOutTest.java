package com.rishabh.framework.tests;

import com.rishabh.framework.pages.InventoryPage;
import com.rishabh.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.rishabh.framework.driver.DriverManager.getDriver;

public class LogOutTest {

    @Test
    public void logoutShouldNavigateBackToLoginPage() {
        InventoryPage inventoryPage =
                new LoginPage(getDriver())
                        .loginAs("standard_user", "secret_sauce");

        inventoryPage.logout();

        LoginPage loginPage = new LoginPage(getDriver());

        Assert.assertTrue(
                loginPage.isLoginButtonDisplayed(),
                "Login button should be visible after logout."
        );
    }
}
