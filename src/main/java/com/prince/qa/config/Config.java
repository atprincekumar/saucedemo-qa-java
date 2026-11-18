package com.prince.qa.config;

public final class Config {
    private Config() {}

    private static String setting(String property, String environmentVariable, String fallback) {
        String value = System.getProperty(property);
        if (value == null || value.isBlank()) value = System.getenv(environmentVariable);
        return value == null || value.isBlank() ? fallback : value;
    }

    public static final String BASE_URL = setting(
            "baseUrl", "SAUCEDEMO_URL", "https://www.saucedemo.com/");
    public static final String USERNAME = System.getenv().getOrDefault("SAUCEDEMO_USERNAME", "standard_user");
    public static final String PASSWORD = System.getenv().getOrDefault("SAUCEDEMO_PASSWORD", "secret_sauce");
    public static final int EXPLICIT_WAIT_SECONDS = 10;
}
