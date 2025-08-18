package com.policybazaar.main;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.policybazaar.pages.TravelInsuranceHomePage;
import com.policybazaar.pages.TravelInsuranceResultsPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
    
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
        logger.info("=== STARTING POLICYBAZAAR AUTOMATION ===");
        WebDriver driver = null;
        
        try {
            // Setup Chrome driver automatically
            logger.info("Setting up WebDriverManager for Chrome");
            WebDriverManager.chromedriver().setup();
            logger.info("WebDriverManager setup completed successfully");
            
            
            // Minimal Chrome options
            logger.info("Configuring Chrome options");
            ChromeOptions options = new ChromeOptions();
         // options.addArguments("--headless=new"); // add later if needed
         options.addArguments("--no-sandbox");
         options.addArguments("--disable-dev-shm-usage");
         // Remove: --disable-web-security, --allow-running-insecure-content
         
         	
         	
            
            logger.info("Initializing Chrome WebDriver");
            
            driver = new ChromeDriver(options);
            logger.info("Chrome WebDriver created successfully");
            
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            
            
            driver.manage().window().maximize();
            
           
            logger.info("Browser setup completed successfully");
            
            // Navigate to the website
            String url = "https://www.policybazaar.com/";
            logger.info("Navigating to URL: {}", url);
            driver.get(url);
            logger.info("Successfully navigated to PolicyBazaar page");
            
            
            // Run the form automation
            logger.info("Initializing TravelInsuranceHomePage");
            
            
            TravelInsuranceHomePage hp = new TravelInsuranceHomePage(driver);
            logger.info("=== STARTING FORM AUTOMATION PROCESS ===");
            hp.clickTravelInsurance();
            hp.fillFormWithNo();
            
           

            
            logger.info("=== FORM AUTOMATION COMPLETED SUCCESSFULLY ===");
            
            // Handle the results page
            logger.info("=== STARTING RESULTS PAGE PROCESSING ===");
            logger.info("Initializing TravelInsuranceResultsPage");
            TravelInsuranceResultsPage resultsPage = new TravelInsuranceResultsPage(driver);
            
            logger.info("Waiting for results page to load");
            resultsPage.waitForResultsToLoad();
            
            logger.info("Sorting results by price: Low to High");
            resultsPage.sortLowtoHigh();
            
            logger.info("Extracting first 3 insurance plans");
            resultsPage.extractFirst3Plans();
            
            logger.info("=== RESULTS PAGE PROCESSING COMPLETED ===");
            logger.info("=== AUTOMATION COMPLETED SUCCESSFULLY ===");
            
        } catch (Exception e) {
            logger.error("Fatal error during automation execution", e);
            logger.error("Error details: {}", e.getMessage());
            
        } 
        }
    }

