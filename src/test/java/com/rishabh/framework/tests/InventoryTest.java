package com.rishabh.framework.tests;

import com.rishabh.framework.base.BaseTest;
import com.rishabh.framework.pages.InventoryPage;
import com.rishabh.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {

    @Test
    public void inventoryPageShouldLoadAfterLogin() {
        InventoryPage inventoryPage =
                new LoginPage(getDriver())
                        .loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isLoaded());
    }

    @Test
    public void firstProductShouldBeBackpack() {
        InventoryPage inventoryPage =
                new LoginPage(getDriver())
                        .loginAs("standard_user", "secret_sauce");

        Assert.assertEquals(
                inventoryPage.getFirstProductName(),
                "Sauce Labs Backpack"
        );
    }
}