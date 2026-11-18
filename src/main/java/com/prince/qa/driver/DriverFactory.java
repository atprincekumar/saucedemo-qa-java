
package com.prince.qa.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER =
            new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver createDriver() {

        ChromeOptions options = new ChromeOptions();

        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);

        options.setCapability("goog:loggingPrefs", logs);

        options.addArguments(
                "--disable-notifications",
                "--disable-popup-blocking",
                "--window-size=1440,1000"
        );

        if (Boolean.parseBoolean(
                System.getProperty("headless", "false"))) {

            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage"
            );
        }

        WebDriver driver = new ChromeDriver(options);
        DRIVER.set(driver);

        return driver;
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {

        WebDriver driver = DRIVER.get();

        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }
}
