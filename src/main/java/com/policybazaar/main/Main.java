package com.policybazaar.main;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import com.policybazaar.pages.TravelInsuranceHomePage;
import com.policybazaar.pages.TravelInsuranceResultsPage;
import com.policybazaar.pages.HealthInsurancePage;
import com.policybazaar.utils.ExcelUtil;
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
            
            
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            
            
            driver.manage().window().maximize();
            
           
            logger.info("Browser setup completed successfully");
            
            // Navigate to the website
            String url = "https://www.policybazaar.com/";
            logger.info("Navigating to URL: {}", url);
            driver.get(url);
            logger.info("Successfully navigated to PolicyBazaar page");
            
            // Read desired heading(s) from Excel input and print those menu items
            String inFile = "menu_headings.xlsx"; // Provide this file with a sheet and a header 'Name'
            String inSheet = "Sheet1";
            List<String> headings = ExcelUtil.readFirstColumn(inFile, inSheet);
            if (headings.isEmpty()) {
                logger.warn("No headings found in Excel. Defaulting to 'Health Insurance'");
                headings.add("Health Insurance");
            }

            HealthInsurancePage page = new HealthInsurancePage(driver);
            for (String heading : headings) {
                logger.info("=== " + heading + " Menu Items ===");
                for (String item : page.getMenuItemsByHeading(heading)) {
                    System.out.println(item);
                }
            }

            // Optionally export Health Insurance to Excel (kept from earlier)
            String outFile = "health_insurance_items.xlsx";
            String sheet = "Health Insurance";
            page.exportHealthInsuranceMenuItemsToExcel(outFile, sheet);
            logger.info("Exported Health Insurance items to Excel: {} (Sheet: {})", outFile, sheet);
            
            // logger.info("Initializing TravelInsuranceHomePage");
            // TravelInsuranceHomePage hp = new TravelInsuranceHomePage(driver);
            // logger.info("=== STARTING FORM AUTOMATION PROCESS ===");
            // hp.fillFormWithYes();
            // logger.info("=== FORM AUTOMATION COMPLETED SUCCESSFULLY ===");
            // logger.info("=== STARTING RESULTS PAGE PROCESSING ===");
            // logger.info("Initializing TravelInsuranceResultsPage");
            // TravelInsuranceResultsPage resultsPage = new TravelInsuranceResultsPage(driver);
            // logger.info("Waiting for results page to load");
            // resultsPage.waitForResultsToLoad();
            // logger.info("Sorting results by price: Low to High");
            // resultsPage.sortLowtoHigh();
            // logger.info("Extracting first 3 insurance plans");
            // resultsPage.extractFirst3Plans();
            // logger.info("=== RESULTS PAGE PROCESSING COMPLETED ===");
            // logger.info("=== AUTOMATION COMPLETED SUCCESSFULLY ===");
            
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
