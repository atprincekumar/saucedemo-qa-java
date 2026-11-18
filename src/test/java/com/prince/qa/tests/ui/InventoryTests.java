package com.prince.qa.tests.ui;

import com.prince.qa.base.BaseTest;
import com.prince.qa.pages.LoginPage;
import com.prince.qa.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTests extends BaseTest {
    private ProductsPage products;

    @BeforeMethod
    public void login() {
        products = new LoginPage(driver).loginAs("standard_user", "secret_sauce");
    }

    @Test(description = "The inventory page displays six product cards")
    public void sixProductsAreVisible() {
        Assert.assertEquals(products.getProductCount(), 6);
    }

    @Test(description = "Price low-to-high sort is numeric and ascending")
    public void sortPriceLowToHigh() {
        products.sortBy("lohi");
        List<Double> prices = products.getProductPrices();
        List<Double> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);
        Assert.assertEquals(prices, sorted);
    }

    @Test(description = "Product names can be sorted Z-to-A")
    public void sortNamesZToA() {
        products.sortBy("za");
        List<String> names = products.getProductNames();
        List<String> sorted = new ArrayList<>(names);
        sorted.sort(Collections.reverseOrder(String.CASE_INSENSITIVE_ORDER));
        Assert.assertEquals(names, sorted);
    }
}
