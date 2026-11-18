
package com.prince.qa.tests.bugs;

import com.prince.qa.base.BaseTest;
import com.prince.qa.pages.LoginPage;
import com.prince.qa.pages.ProductsPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class PerformanceUserExplorationTests extends BaseTest {

    @Test(description = "Diagnose add-to-cart automation for problem_user")
    public void problemUserCanAddFleeceJacket() {

        // Step 1: Login
        ProductsPage products = new LoginPage(driver)
                .loginAs("standard_user", "secret_sauce");

        By addButton = By.cssSelector(
                "[data-test='add-to-cart-sauce-labs-fleece-jacket']"
        );

        By removeButton = By.cssSelector(
                "[data-test='remove-sauce-labs-fleece-jacket']"
        );

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );

        // Step 2: Locate the Add to Cart button
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(addButton)
        );

        System.out.println("===== BUTTON DETAILS =====");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("Page title: " + products.getTitle());
        System.out.println("Button text: " + button.getText());
        System.out.println("Button HTML: " + button.getAttribute("outerHTML"));
        System.out.println("Button enabled: " + button.isEnabled());
        System.out.println("Button displayed: " + button.isDisplayed());

        // Step 3: Monitor browser click events
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "window.clickEvents = [];" +
                        "document.addEventListener('click', function(event) {" +
                        "  window.clickEvents.push({" +
                        "    target: event.target.outerHTML," +
                        "    trusted: event.isTrusted" +
                        "  });" +
                        "}, true);"
        );

        // Step 4: Perform Selenium click
        button.click();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Step 5: Print captured click events
        Object events = js.executeScript(
                "return window.clickEvents;"
        );

        System.out.println("===== CLICK EVENT DETAILS =====");
        System.out.println("Captured click events: " + events);

        // Step 6: Check cart status
        System.out.println("===== CART DETAILS =====");

        int removeCount = driver.findElements(removeButton).size();

        System.out.println("Remove button count: " + removeCount);
        System.out.println("Cart badge visible: "
                + products.isCartBadgeVisible());

        if (products.isCartBadgeVisible()) {
            System.out.println("Cart count: " + products.getCartCount());
        }

        // Step 7: Print browser console errors
        System.out.println("===== BROWSER CONSOLE LOGS =====");

        try {
            List<LogEntry> logs = driver.manage().logs()
                    .get("browser").getAll();

            if (logs.isEmpty()) {
                System.out.println("No browser console messages found.");
            }

            for (LogEntry log : logs) {
                System.out.println(
                        log.getLevel() + " : " + log.getMessage()
                );
            }

        } catch (Exception e) {
            System.out.println(
                    "Could not read browser logs: " + e.getMessage()
            );
        }

        System.out.println("===== END CONSOLE LOGS =====");

        // Step 8: Confirm that Selenium delivered a trusted click
        Assert.assertTrue(
                events.toString().contains("trusted=true"),
                "The browser did not receive a trusted click event."
        );
    }
}
