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
            options.addArguments("--disable-web-security");
            options.addArguments("--allow-running-insecure-content");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            logger.info("Chrome options configured successfully");
            
            
            logger.info("Initializing Chrome WebDriver");
            
            driver = new ChromeDriver(options);
            logger.info("Chrome WebDriver created successfully");
            
            logger.info("Setting implicit wait timeout to 15 seconds");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            
            logger.info("Maximizing browser window");
            driver.manage().window().maximize();
            
            System.out.println("Chrome started successfully!");
            logger.info("Browser setup completed successfully");
            
            // Navigate to the website
            String url = "https://travel.policybazaar.com/?newpq=1&utm_term=newjourney&utm_content=newpq";
            logger.info("Navigating to URL: {}", url);
            driver.get(url);
            logger.info("Successfully navigated to PolicyBazaar travel page");
            System.out.println("Navigated to PolicyBazaar travel page");
            
            // Run the form automation
            logger.info("Initializing TravelInsuranceHomePage");
            TravelInsuranceHomePage hp = new TravelInsuranceHomePage(driver);
            
            logger.info("=== STARTING FORM AUTOMATION PROCESS ===");
            
            logger.info("Step 1: Selecting destination");
            hp.selectDestinationWithJS();
            logger.info("Destination selection completed successfully");
            
            logger.info("Step 2: Selecting travel dates");
            hp.dateselect();
            logger.info("Date selection completed successfully");
            
            
            logger.info("Step 3: Selecting number of travellers");
            hp.selectTravellerTwo();
            logger.info("Traveller count selection completed successfully");

            
            logger.info("Step 4: Selecting traveller details");
            hp.selectTraveller();
            logger.info("Traveller details selection completed successfully");

            
            logger.info("Step 5: Selecting medical condition option");
            hp.medicalContitionRadio();
            logger.info("Medical condition selection completed successfully");

            
            logger.info("Step 6: Clicking Explore Plans button");
            hp.explorePlanBtn();
            logger.info("Explore Plans button clicked successfully");

            
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
            
        } finally {
            if (driver != null) {
                logger.info("Closing browser and cleaning up resources");
                driver.quit();
                logger.info("Browser closed and resources cleaned up successfully");
                
            }
        }
    }
}
