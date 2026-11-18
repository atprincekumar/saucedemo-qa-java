package com.prince.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {
    private final By pageTitle = By.cssSelector(".title");
    private final By productCards = By.cssSelector(".inventory_item");
    private final By productNames = By.cssSelector(".inventory_item_name");
    private final By productPrices = By.cssSelector(".inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartLink = By.className("shopping_cart_link");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() { return text(pageTitle); }

    public int getProductCount() { return visibleAll(productCards).size(); }

    public List<String> getProductNames() {
        return visibleAll(productNames).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return visibleAll(productPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "").trim()))
                .collect(Collectors.toList());
    }

    public List<String> getProductImageSources() {
        return visibleAll(By.cssSelector(".inventory_item_img img"))
                .stream().map(e -> e.getAttribute("src")).collect(Collectors.toList());
    }

    public void sortBy(String optionValue) {
        new Select(visible(sortDropdown)).selectByValue(optionValue);
    }

    public void addProduct(String productName) {
        String productId = productName.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-");

        By addButton = By.cssSelector(
                "[data-test='add-to-cart-" + productId + "']"
        );

        By removeButton = By.cssSelector(
                "[data-test='remove-" + productId + "']"
        );

        click(addButton);

        // Wait until the application confirms the product was added.
        wait.until(ExpectedConditions.visibilityOfElementLocated(removeButton));
    }



    public void removeProduct(String productName) {
        String productId = productName.toLowerCase()
                .replaceAll("[^a-z0-9]+", "-");

        By removeButton = By.cssSelector(
                "[data-test='remove-" + productId + "']"
        );

        By addButton = By.cssSelector(
                "[data-test='add-to-cart-" + productId + "']"
        );

        click(removeButton);

        // Wait until the application confirms the product was removed.
        wait.until(ExpectedConditions.visibilityOfElementLocated(addButton));
    }

    public String getCartCount() {
        return visible(cartBadge).getText().trim();
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public CartPage openCart() {
        click(cartLink);
        wait.until(ExpectedConditions.urlContains("cart.html"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("checkout")
        ));
        return new CartPage(driver);
    }

    public void logout() {
        click(menuButton);
        click(logoutLink);
    }

    private String xpathLiteral(String value) {
        // Product names used by this demo site do not contain apostrophes.
        return "'" + value + "'";
    }
}
