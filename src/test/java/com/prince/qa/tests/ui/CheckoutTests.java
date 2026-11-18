package com.prince.qa.tests.ui;

import com.prince.qa.base.BaseTest;
import com.prince.qa.pages.CartPage;
import com.prince.qa.pages.CheckoutPage;
import com.prince.qa.pages.LoginPage;
import com.prince.qa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckoutTests extends BaseTest {
    @Test(description = "Checkout validates missing first name")
    public void missingFirstNameShowsValidation() {
        ProductsPage products = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        products.addProduct("Sauce Labs Backpack");
        CheckoutPage checkout = products.openCart().checkout();
        checkout.enterInformation("", "Kumar", "110001");
        checkout.continueCheckout();
        Assert.assertTrue(checkout.getErrorMessage().contains("First Name is required"));
    }

    @Test(description = "A standard user can complete an order")
    public void completeCheckoutSuccessfully() {
        ProductsPage products = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        products.addProduct("Sauce Labs Backpack");
        CartPage cart = products.openCart();
        Assert.assertEquals(cart.getItemCount(), 1);

        CheckoutPage checkout = cart.checkout();
        checkout.enterInformation("Prince", "Kumar", "110001");
        checkout.continueCheckout();
        List<String> overviewItems = checkout.getOverviewItemNames();
        Assert.assertTrue(overviewItems.contains("Sauce Labs Backpack"));
        Assert.assertEquals(checkout.getSubtotal(), 29.99, 0.01);
        Assert.assertEquals(checkout.getTotal(), checkout.getSubtotal() + checkout.getTax(), 0.01);

        checkout.finish();
        Assert.assertTrue(checkout.getCompleteHeader().toUpperCase().contains("THANK YOU FOR YOUR ORDER"));
    }
}
