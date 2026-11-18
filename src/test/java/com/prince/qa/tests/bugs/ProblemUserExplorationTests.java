package com.prince.qa.tests.bugs;

import com.prince.qa.base.BaseTest;
import com.prince.qa.pages.CartPage;
import com.prince.qa.pages.CheckoutPage;
import com.prince.qa.pages.LoginPage;
import com.prince.qa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Exploratory checks for candidate problem_user defects. These are not part of the default
 * regression suite. Run with -DsuiteXmlFile=testng-bugs.xml and classify only observed failures.
 */
public class ProblemUserExplorationTests extends BaseTest {
    @Test(description = "BUG-CANDIDATE-001: problem_user product images match standard_user baseline")
    public void problemUserProductImagesMatchBaseline() {
        LoginPage login = new LoginPage(driver);
        ProductsPage standard = login.loginAs("standard_user", "secret_sauce");
        List<String> baselineImages = standard.getProductImageSources();
        standard.logout();

        driver.get("https://www.saucedemo.com/");
        ProductsPage problem = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        List<String> problemImages = problem.getProductImageSources();
        Assert.assertEquals(problemImages, baselineImages,
                "Candidate issue: problem_user displays product image sources different from standard_user");
    }

    @Test(description = "BUG-CANDIDATE-002: problem_user adds fleece jacket to the cart")
    public void problemUserCanAddFleeceJacket() {
        ProductsPage products = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        products.addProduct("Sauce Labs Fleece Jacket");
        Assert.assertEquals(products.getCartCount(), "1",
                "Candidate issue: cart badge did not update after adding Fleece Jacket");
        CartPage cart = products.openCart();
        Assert.assertTrue(cart.getItemNames().contains("Sauce Labs Fleece Jacket"),
                "Candidate issue: Fleece Jacket is not present in the cart");
    }

    @Test(description = "BUG-CANDIDATE-003: problem_user can continue from cart to checkout")
    public void problemUserCheckoutStarts() {
        ProductsPage products = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        products.addProduct("Sauce Labs Backpack");
        CartPage cart = products.openCart();
        Assert.assertEquals(cart.getItemCount(), 1, "Precondition: backpack should be present in cart");
        cart.checkout();
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"),
                "Candidate issue: Checkout button did not open the information step for problem_user");
    }

    @Test(description = "BUG-CANDIDATE-003: the application must block checkout with an empty cart")
    public void emptyCartCannotCompleteCheckout() {
        ProductsPage products = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        CartPage cart = products.openCart();
        Assert.assertEquals(cart.getItemCount(), 0, "Precondition: cart should be empty");
        CheckoutPage checkout = cart.checkout();
        checkout.enterInformation("QA", "Tester", "10001");
        checkout.continueCheckout();
        Assert.assertEquals(checkout.getOverviewItemNames().size(), 0,
                "Precondition: checkout overview should still have no line items");
        checkout.finish();
        Assert.assertFalse(driver.getCurrentUrl().contains("checkout-complete"),
                "Candidate issue: an order with zero items was completed");
    }
}
