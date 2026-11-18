package com.prince.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutPage extends BasePage {
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By overviewItems = By.cssSelector(".cart_item .inventory_item_name");
    private final By subtotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) { super(driver); }

    public void enterInformation(String first, String last, String postal) {
        if (first != null) type(firstName, first);
        if (last != null) type(lastName, last);
        if (postal != null) type(postalCode, postal);
    }

    public String getLastNameValue() { return visible(lastName).getAttribute("value"); }

    public void continueCheckout() { click(continueButton); }

    public String getErrorMessage() { return text(errorMessage); }

    public List<String> getOverviewItemNames() {
        return driver.findElements(overviewItems).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public double getSubtotal() { return amount(text(subtotal)); }
    public double getTax() { return amount(text(tax)); }
    public double getTotal() { return amount(text(total)); }

    public void finish() { click(finishButton); }

    public String getCompleteHeader() { return text(completeHeader); }

    private double amount(String label) {
        String numeric = label.replaceAll("[^0-9.]", "");
        return Double.parseDouble(numeric);
    }
}
