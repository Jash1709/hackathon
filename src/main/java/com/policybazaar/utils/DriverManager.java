package com.policybazaar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

/**
 * Utility class for WebDriver management
 */
public class DriverManager {
    
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static WebDriver driver;
    
    /**
     * Initialize Chrome WebDriver with default options
     * @return WebDriver instance
     */
    public static WebDriver initializeChromeDriver() {
        logger.info("Initializing Chrome WebDriver with default options");
        return initializeChromeDriver(getDefaultChromeOptions());
    }
    
    /**
     * Initialize Chrome WebDriver with custom options
     * @param options ChromeOptions to use
     * @return WebDriver instance
     */
    public static WebDriver initializeChromeDriver(ChromeOptions options) {
        logger.info("Initializing Chrome WebDriver with custom options");
        try {
            logger.info("Setting up WebDriverManager for Chrome");
            WebDriverManager.chromedriver().setup();
            logger.info("WebDriverManager setup completed");
            
            logger.info("Creating ChromeDriver instance");
            driver = new ChromeDriver(options);
            logger.info("ChromeDriver created successfully");
            
            logger.info("Configuring driver timeouts and window settings");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().window().maximize();
            logger.info("Driver configuration completed");
            
            return driver;
        } catch (Exception e) {
            logger.error("Failed to initialize Chrome WebDriver", e);
            throw new RuntimeException("Failed to initialize Chrome WebDriver", e);
        }
    }
    
    /**
     * Initialize Firefox WebDriver with default options
     * @return WebDriver instance
     */
    public static WebDriver initializeFirefoxDriver() {
        logger.info("Initializing Firefox WebDriver with default options");
        return initializeFirefoxDriver(getDefaultFirefoxOptions());
    }
    
    /**
     * Initialize Firefox WebDriver with custom options
     * @param options FirefoxOptions to use
     * @return WebDriver instance
     */
    public static WebDriver initializeFirefoxDriver(FirefoxOptions options) {
        logger.info("Initializing Firefox WebDriver with custom options");
        try {
            logger.info("Setting up WebDriverManager for Firefox");
            WebDriverManager.firefoxdriver().setup();
            logger.info("WebDriverManager setup completed");
            
            logger.info("Creating FirefoxDriver instance");
            driver = new FirefoxDriver(options);
            logger.info("FirefoxDriver created successfully");
            
            logger.info("Configuring driver timeouts and window settings");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().window().maximize();
            logger.info("Driver configuration completed");
            
            return driver;
        } catch (Exception e) {
            logger.error("Failed to initialize Firefox WebDriver", e);
            throw new RuntimeException("Failed to initialize Firefox WebDriver", e);
        }
    }
    
    /**
     * Get default Chrome options
     * @return ChromeOptions with default settings
     */
    public static ChromeOptions getDefaultChromeOptions() {
        logger.info("Creating default Chrome options");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        logger.info("Default Chrome options created");
        return options;
    }
    
    /**
     * Get default Firefox options
     * @return FirefoxOptions with default settings
     */
    public static FirefoxOptions getDefaultFirefoxOptions() {
        logger.info("Creating default Firefox options");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--disable-web-security");
        logger.info("Default Firefox options created");
        return options;
    }
    
    /**
     * Get current WebDriver instance
     * @return current WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            logger.warn("WebDriver instance is null, consider initializing it first");
        }
        return driver;
    }
    
    /**
     * Close current WebDriver instance
     */
    public static void closeDriver() {
        if (driver != null) {
            logger.info("Closing WebDriver instance");
            try {
                driver.quit();
                logger.info("WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Error while closing WebDriver", e);
            } finally {
                driver = null;
                logger.info("WebDriver instance set to null");
            }
        } else {
            logger.warn("Attempted to close WebDriver but instance was already null");
        }
    }
} 