package com.rishabh.framework.tests;

import com.rishabh.framework.base.BaseTest;
import com.rishabh.framework.pages.InventoryPage;
import com.rishabh.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginShouldNavigateToInventoryPage() {
        InventoryPage inventoryPage =
                new LoginPage(getDriver())
                        .loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isLoaded());
        Assert.assertEquals(inventoryPage.getTitleText(), "Products");
    }

    @Test
    public void invalidLoginShouldShowErrorMessage() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginAs("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
    }

    @Test
    public void lockedUserShouldShowError() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginAs("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("locked"));
    }
}