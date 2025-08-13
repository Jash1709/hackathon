package com.policybazaar.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.policybazaar.utils.ConfigReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class DriverSetup {

    private static final Logger logger = LogManager.getLogger(DriverSetup.class);

    private static WebDriver driver;
    private static WebDriverWait wait;

    private DriverSetup() {}

    public static void initializeDriver(String browserName) {
        try {
            logger.info("Initializing WebDriver for browser: {}", browserName);

            switch (browserName.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--disable-notifications");
                    driver = new EdgeDriver(edgeOptions);
                    driver.manage().window().maximize();
                    break;

                default:
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
            wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));

            logger.info("WebDriver initialized successfully");

        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: {}", e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    public static void navigateToApplication() {
        try {
            String url = ConfigReader.getProperty("app.url");
            logger.info("Navigating to application URL: {}", url);
            driver.get(url);
            logger.info("Successfully navigated to application");
        } catch (Exception e) {
            logger.error("Failed to navigate to application: {}", e.getMessage());
            throw new RuntimeException("Navigation failed", e);
        }
    }

    public static void tearDown() {
        try {
            if (driver != null) {
                logger.info("Closing browser and cleaning up resources");
                driver.quit();
                logger.info("Browser closed successfully");
            }
        } catch (Exception e) {
            logger.error("Error during teardown: {}", e.getMessage());
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriverWait getWait() {
        return wait;
    }
}
