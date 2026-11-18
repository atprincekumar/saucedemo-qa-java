package com.prince.qa.tests.ui;

import com.prince.qa.base.BaseTest;
import com.prince.qa.pages.CartPage;
import com.prince.qa.pages.LoginPage;
import com.prince.qa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {

    @Test(description = "A product can be added to and removed from the cart")
    public void addAndRemoveProduct() {
        ProductsPage products = new LoginPage(driver)
                .loginAs("standard_user", "secret_sauce");

        String item = "Sauce Labs Backpack";
        products.addProduct(item);

        Assert.assertEquals(products.getCartCount(), "1");

        CartPage cart = products.openCart();
        Assert.assertTrue(cart.getItemNames().contains(item));

        cart.removeItem(item);
        Assert.assertEquals(cart.getItemCount(), 0);
    }

    @Test(description = "Multiple selected products appear in the cart")
    public void multipleProductsAppearInCart() {
        ProductsPage products = new LoginPage(driver)
                .loginAs("standard_user", "secret_sauce");

        products.addProduct("Sauce Labs Backpack");
        products.addProduct("Sauce Labs Bike Light");
        products.addProduct("Sauce Labs Bolt T-Shirt");

        CartPage cart = products.openCart();

        Assert.assertEquals(cart.getItemNames().size(), 3);
        Assert.assertEquals(cart.getItemCount(), 3);
    }
}
