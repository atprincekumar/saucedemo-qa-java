package com.prince.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {
    private final By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) { super(driver); }

    public int getItemCount() {
        return driver.findElements(itemNames).size();
    }
    public List<String> getItemNames() {
        wait.until(ExpectedConditions.presenceOfElementLocated(itemNames));
        @SuppressWarnings("unchecked")
        List<String> names = (List<String>) ((JavascriptExecutor) driver).executeScript(
                "return Array.from(document.querySelectorAll(arguments[0])).map(e => e.textContent.trim())",
                "[data-test='inventory-item-name']");
        return names;
    }

    public void removeItem(String productName) {
        By remove = By.xpath("//*[@data-test='inventory-item-name' and normalize-space()="
                + "'" + productName + "']/ancestor::*[.//button[normalize-space()='Remove']][1]//button[normalize-space()='Remove']");
        click(remove);
    }

    public CheckoutPage checkout() {
        click(checkoutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
        return new CheckoutPage(driver);
    }

    public void continueShopping() { click(continueShoppingButton); }
}
