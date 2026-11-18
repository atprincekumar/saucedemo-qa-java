package com.prince.qa.listeners;

import com.prince.qa.base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest baseTest)) return;
        WebDriver driver = baseTest.getDriver();
        if (!(driver instanceof TakesScreenshot screenshotDriver)) return;

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String safeName = result.getMethod().getMethodName().replaceAll("[^a-zA-Z0-9-_]", "_");
        Path destination = Path.of("target", "screenshots", safeName + "-" + timestamp + ".png");
        try {
            Files.createDirectories(destination.getParent());
            File source = screenshotDriver.getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Failure screenshot saved: " + destination.toAbsolutePath());
            String method = result.getMethod().getMethodName();
            String bugEvidence = switch (method) {
                case "problemUserProductImagesMatchBaseline" -> "bug-001-problem-user-product-images.png";
                case "problemUserCanAddFleeceJacket" -> "bug-002-problem-user-add-to-cart.png";
                case "emptyCartCannotCompleteCheckout" -> "bug-003-empty-cart-checkout.png";
                default -> null;
            };
            if (bugEvidence != null) {
                Path evidence = Path.of("docs", "bug-reports", "evidence", bugEvidence);
                Files.createDirectories(evidence.getParent());
                Files.copy(source.toPath(), evidence, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Bug evidence saved: " + evidence.toAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Could not save failure screenshot: " + e.getMessage());
        }
    }
}
