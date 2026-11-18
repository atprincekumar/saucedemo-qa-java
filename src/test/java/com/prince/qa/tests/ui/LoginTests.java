package com.prince.qa.tests.ui;

import com.prince.qa.base.BaseTest;
import com.prince.qa.pages.LoginPage;
import com.prince.qa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    @Test(description = "A standard user can log in successfully")
    public void validLogin() {
        ProductsPage products = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
        Assert.assertEquals(products.getTitle(), "Products");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test(description = "Login rejects an invalid password")
    public void invalidPasswordShowsError() {
        LoginPage login = new LoginPage(driver);
        login.loginAs("standard_user", "wrong_password");
        Assert.assertTrue(login.getErrorMessage().contains("Username and password do not match"));
    }

    @Test(description = "Login rejects a locked-out user")
    public void lockedOutUserShowsError() {
        LoginPage login = new LoginPage(driver);
        login.loginAs("locked_out_user", "secret_sauce");
        Assert.assertTrue(login.getErrorMessage().contains("Sorry, this user has been locked out"));
    }

    @Test(description = "Login requires a username")
    public void emptyUsernameShowsError() {
        LoginPage login = new LoginPage(driver);
        login.loginAs("", "secret_sauce");
        Assert.assertTrue(login.getErrorMessage().contains("Username is required"));
    }
}
