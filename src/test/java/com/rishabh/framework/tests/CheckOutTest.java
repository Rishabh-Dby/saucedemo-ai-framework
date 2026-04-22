package com.rishabh.framework.tests;

import com.rishabh.framework.base.BaseTest;
import com.rishabh.framework.pages.InventoryPage;
import com.rishabh.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class CheckOutTest extends BaseTest {

    @Test
    public void userShouldBeAbleToReachCartPage() {
        InventoryPage inventoryPage =
                new LoginPage(getDriver())
                        .loginAs("standard_user", "secret_sauce");

        inventoryPage.openCart();

        Assert.assertTrue(
                Objects.requireNonNull(getDriver().getCurrentUrl()).contains("cart")
        );
    }
}