package com.policybazaar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


//Utility class to read configuration properties
public class ConfigReader {

	 private static final Logger logger = LogManager.getLogger(ConfigReader.class);
	 private static Properties properties;
	
	 // Path to the config file
	 private static final String CONFIG_FILE_PATH = "C:/Users/2421191/OneDrive - Cognizant/Desktop/hackathon/policybazaar/src/main/resources/config.properties";
	
	 // Static block to load properties when class is loaded
	 static {
	     loadProperties();
	 }
	
	 // Loads properties from the config file
	 private static void loadProperties() {
	     try {
	         properties = new Properties();
	         FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
	         properties.load(fileInputStream);
	         fileInputStream.close();
	         logger.info("Configuration properties loaded successfully");
	     } catch (IOException e) {
	         logger.error("Failed to load configuration properties: {}", e.getMessage());
	         throw new RuntimeException("Configuration file not found", e);
	     }
	 }
	
	 // Retrieves property value by key
	 public static String getProperty(String key) {
	     String value = properties.getProperty(key);
	     if (value == null) {
	         logger.warn("Property not found for key: {}", key);
	     }
	     return value;
	 }
	
	 // Specific property access methods
	 public static String getBrowser() {
	     return getProperty("browser");
	 }
	
	 public static String getAppUrl() {
	     return getProperty("app.url");
	 }
	
	 public static int getImplicitWait() {
	     return Integer.parseInt(getProperty("implicit.wait"));
	 }
	
	 public static int getExplicitWait() {
	     return Integer.parseInt(getProperty("explicit.wait"));
	 }
	
	 public static int getPageLoadTimeout() {
	     return Integer.parseInt(getProperty("page.load.timeout"));
	 }
	
	 
	 public static String getExtentReportPath() {
	     return getProperty("extent.report.path");
	 }
	
	 public static String getScreenshotPath() {
	     return getProperty("screenshot.path");
	 }
	
}
