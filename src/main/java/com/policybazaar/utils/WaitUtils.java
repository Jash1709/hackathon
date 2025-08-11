package com.policybazaar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Utility class for common WebDriver wait operations
 */
public class WaitUtils {
    
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT = 30;
    
    /**
     * Wait for an element to be visible
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @param timeoutSeconds timeout in seconds
     * @return the visible WebElement
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, WebElement element, int timeoutSeconds) {
        logger.info("Waiting for element to be visible with timeout: {} seconds", timeoutSeconds);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
            logger.info("Element is now visible");
            return visibleElement;
        } catch (Exception e) {
            logger.error("Element did not become visible within {} seconds", timeoutSeconds, e);
            throw e;
        }
    }
    
    /**
     * Wait for an element to be visible with default timeout
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @return the visible WebElement
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, WebElement element) {
        logger.info("Waiting for element to be visible with default timeout: {} seconds", DEFAULT_TIMEOUT);
        return waitForElementToBeVisible(driver, element, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for an element to be clickable
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @param timeoutSeconds timeout in seconds
     * @return the clickable WebElement
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element, int timeoutSeconds) {
        logger.info("Waiting for element to be clickable with timeout: {} seconds", timeoutSeconds);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.info("Element is now clickable");
            return clickableElement;
        } catch (Exception e) {
            logger.error("Element did not become clickable within {} seconds", timeoutSeconds, e);
            throw e;
        }
    }
    
    /**
     * Wait for an element to be clickable with default timeout
     * @param driver WebDriver instance
     * @param element WebElement to wait for
     * @return the clickable WebElement
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element) {
        logger.info("Waiting for element to be clickable with default timeout: {} seconds", DEFAULT_TIMEOUT);
        return waitForElementToBeClickable(driver, element, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for an element located by locator to be present
     * @param driver WebDriver instance
     * @param locator By locator
     * @param timeoutSeconds timeout in seconds
     * @return the present WebElement
     */
    public static WebElement waitForElementToBePresent(WebDriver driver, By locator, int timeoutSeconds) {
        logger.info("Waiting for element to be present with locator: {} and timeout: {} seconds", locator, timeoutSeconds);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            WebElement presentElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("Element is now present");
            return presentElement;
        } catch (Exception e) {
            logger.error("Element did not become present within {} seconds with locator: {}", timeoutSeconds, locator, e);
            throw e;
        }
    }
    
    /**
     * Wait for an element located by locator to be present with default timeout
     * @param driver WebDriver instance
     * @param locator By locator
     * @return the present WebElement
     */
    public static WebElement waitForElementToBePresent(WebDriver driver, By locator) {
        logger.info("Waiting for element to be present with default timeout: {} seconds", DEFAULT_TIMEOUT);
        return waitForElementToBePresent(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for text to be present in an element
     * @param driver WebDriver instance
     * @param element WebElement to check
     * @param text text to wait for
     * @param timeoutSeconds timeout in seconds
     * @return true if text is present
     */
    public static boolean waitForTextToBePresentInElement(WebDriver driver, WebElement element, String text, int timeoutSeconds) {
        logger.info("Waiting for text '{}' to be present in element with timeout: {} seconds", text, timeoutSeconds);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            boolean textPresent = wait.until(ExpectedConditions.textToBePresentInElement(element, text));
            logger.info("Text '{}' is now present in element", text);
            return textPresent;
        } catch (Exception e) {
            logger.error("Text '{}' did not appear in element within {} seconds", text, timeoutSeconds, e);
            throw e;
        }
    }
    
    /**
     * Wait for text to be present in an element with default timeout
     * @param driver WebDriver instance
     * @param element WebElement to check
     * @param text text to wait for
     * @return true if text is present
     */
    public static boolean waitForTextToBePresentInElement(WebDriver driver, WebElement element, String text) {
        logger.info("Waiting for text '{}' to be present with default timeout: {} seconds", text, DEFAULT_TIMEOUT);
        return waitForTextToBePresentInElement(driver, element, text, DEFAULT_TIMEOUT);
    }
    
    /**
     * Sleep for specified milliseconds with logging
     * @param milliseconds time to sleep
     */
    public static void sleep(long milliseconds) {
        logger.info("Sleeping for {} milliseconds", milliseconds);
        try {
            Thread.sleep(milliseconds);
            logger.info("Sleep completed");
        } catch (InterruptedException e) {
            logger.error("Sleep was interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
} 