package com.rishabh.framework.tests;

import com.rishabh.framework.base.BaseTest;
import com.rishabh.framework.pages.InventoryPage;
import com.rishabh.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void addProductShouldUpdateCartBadge() {
        InventoryPage inventoryPage =
                new LoginPage(getDriver())
                        .loginAs("standard_user", "secret_sauce");

        inventoryPage.addBackpackToCart();

        Assert.assertEquals(
                inventoryPage.getCartBadgeCount(),
                "1"
        );
    }

    @Test
    public void removeProductShouldClearCartBadge() {
        InventoryPage inventoryPage =
                new LoginPage(getDriver())
                        .loginAs("standard_user", "secret_sauce");

        inventoryPage.addBackpackToCart();
        inventoryPage.removeBackpackFromCart();

        Assert.assertFalse(
                inventoryPage.isCartBadgeDisplayed()
        );
    }
}