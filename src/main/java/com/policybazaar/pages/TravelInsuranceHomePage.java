package com.policybazaar.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TravelInsuranceHomePage {
    
    private static final Logger logger = LogManager.getLogger(TravelInsuranceHomePage.class);
	private WebDriver driver;
    
    // Locators
    @FindBy(xpath="//*[@id='favourite-country']/li[3]")
    WebElement destination;
    
    @FindBy(xpath="//*[@id=\"newPq_mainWrapper\"]/section/section[2]/article[2]/div[1]")
    WebElement date;
    
    @FindBy(xpath="//*[@id='modal-root']/section/article/div/div/div[2]/div[2]/div/div/div[1]/div[3]/div/div[3]/div[7]/div/button")
    WebElement startDate;
    
    @FindBy(xpath="//*[@id='modal-root']/section/article/div/div/div[2]/div[2]/div/div/div[2]/div[3]/div/div[3]/div[7]/div/button")
    WebElement endDate;
    
    @FindBy(xpath="//*[@id='modal-root']/section/article/div/div/div[2]/div[3]/div/button")
    WebElement continueDate;
    
    @FindBy(xpath = "//label[@for='traveller_2']")
    @CacheLookup
    WebElement numberOfTraveller;
    
    @FindBy(xpath="//*[@id='0']")
    WebElement traveller1option;
    
    @FindBy(xpath="//label[@for='21 years_undefined']")
    WebElement traveller1age;
    
    @FindBy(xpath="//*[@id='1']")
    WebElement traveller2option;
    
    @FindBy(xpath="//label[@for='22 years_undefined']")
    WebElement traveller2age;
    
    @FindBy(id="ped_no")
    WebElement medicalContitionRadio;
    
    @FindBy(xpath="//*[@id=\"modal-root\"]/section/article/div/div/div[2]/div[3]/div/button")
    WebElement travellerDoneBtn;
    
    @FindBy(xpath = "//*[text()='Explore Plans ›']")
    WebElement explorePlanBtn;
    
    public TravelInsuranceHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
        logger.info("TravelInsuranceHomePage initialized with PageFactory");
    }
    
    public void selectDestinationWithJS() {
        logger.info("Starting destination selection process");
        try {
            logger.info("Scrolling destination element into view");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", destination);
            logger.info("Destination element scrolled into view successfully");
            
            logger.info("Clicking destination element");
            destination.click();
            logger.info("Destination selection completed successfully");
        } catch (Exception e) {
            logger.error("Error during destination selection", e);
            throw e;
        }
    }
    
    public void dateselect() {
        logger.info("Starting date selection process");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            logger.info("Clicking date picker");
            js.executeScript("arguments[0].click();", date);
            logger.info("Date picker opened successfully");
            
            logger.info("Selecting start date");
            js.executeScript("arguments[0].click();", startDate);
            logger.info("Start date selected successfully");
            
            logger.info("Selecting end date");
            js.executeScript("arguments[0].click();", endDate);
            logger.info("End date selected successfully");
            
            logger.info("Clicking continue button for dates");
            continueDate.click();
            logger.info("Date selection process completed successfully");
        } catch (Exception e) {
            logger.error("Error during date selection", e);
            throw e;
        }
    }
    
    public void selectTravellerTwo() {
        logger.info("Starting traveller count selection process");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            logger.info("Waiting for traveller count element to be visible");
            wait.until(ExpectedConditions.visibilityOf(numberOfTraveller));
            wait.until(ExpectedConditions.elementToBeClickable(numberOfTraveller));
            logger.info("Traveller count element is now visible and clickable");

            logger.info("Scrolling traveller count element into view");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", numberOfTraveller);

            try {
                logger.info("Attempting native click on traveller count");
                numberOfTraveller.click();
                logger.info("Native click successful");
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                logger.warn("Native click intercepted, falling back to JavaScript click");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", numberOfTraveller);
                logger.info("JavaScript click successful");
            }
            logger.info("Traveller count selection completed successfully");
        } catch (Exception e) {
            logger.error("Error during traveller count selection", e);
            throw e;
        }
    }

    public void selectTraveller() {
        logger.info("Starting individual traveller details selection");
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            logger.info("Selecting first traveller");
            logger.info("Scrolling first traveller option into view");
            js.executeScript("arguments[0].scrollIntoView(true);", traveller1option);
            
            logger.info("Clicking first traveller option");
            traveller1option.click();
            logger.info("First traveller selected successfully");
            
            logger.info("Selecting age for first traveller (21 years)");
            js.executeScript("arguments[0].click();", traveller1age);
            logger.info("First traveller age selected successfully");
            
            logger.info("Selecting second traveller");
            logger.info("Scrolling second traveller option into view");
            js.executeScript("arguments[0].scrollIntoView(true);", traveller2option);
            
            logger.info("Clicking second traveller option");
            traveller2option.click();
            logger.info("Second traveller selected successfully");
            
            logger.info("Selecting age for second traveller (22 years)");
            js.executeScript("arguments[0].click();", traveller2age);
            logger.info("Second traveller age selected successfully");
            
            logger.info("Individual traveller details selection completed successfully");
        } catch (Exception e) {
            logger.error("Error during traveller details selection", e);
            throw e;
        }
    }
    
    public void medicalContitionRadio() {
        logger.info("Starting medical condition selection process");
        try {
            logger.info("Clicking medical condition radio button (No)");
            medicalContitionRadio.click();
            logger.info("Medical condition radio button selected successfully");
            
            logger.info("Clicking traveller done button");
            travellerDoneBtn.click();
            logger.info("Traveller done button clicked successfully");
            
            logger.info("Medical condition selection process completed successfully");
        } catch (Exception e) {
            logger.error("Error during medical condition selection", e);
            throw e;
        }
    }
    
    public void explorePlanBtn() {
        logger.info("Starting explore plans button click process");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            logger.info("Waiting for explore plans button to be visible and clickable");
            wait.until(ExpectedConditions.visibilityOf(explorePlanBtn));
            wait.until(ExpectedConditions.elementToBeClickable(explorePlanBtn));
            logger.info("Explore plans button is now visible and clickable");

            logger.info("Scrolling explore plans button into view");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", explorePlanBtn);
            
            String resultsUrl = "https://travel.policybazaar.com/quotes?encp=dU1MS2g3Q05zNXFqd20wYnFHUWNxZz09&family=0&isPlanCTAExp=1&isRepeatMember=0&newpq=1&profiletypeid=1&sum_insured=d60&token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3NTQ4OTM1ODUsImp0aSI6ImEyMGI3NTYyLWE0ZjItNDVmMy04MGVjLTlkM2ZmNTNjMzZlYyIsIlByb3Bvc2VySUQiOiI0ODQ2NTc1IiwibmJmIjoxNzU0ODkzNTg1LCJleHAiOjE3NTc1MjE1ODUsImlzcyI6InRyYXZlbC5wb2xpY3liYXphYXIuY29tIiwiYXVkIjoidHJhdmVsIn0.TFRxPLBl0BjVQ9mh2tAvTIyzvTKfAfZiTmtSONL2DPrH1__37GRJnDCea_aodl_Da5kvnElMsxXaAWxP6UhftlSRUX2VDOThRUSvwZCvRHTgQfI3BVrwVz46JGIyJcno6F9MAuAxKvXUyS5SQK2JMXXo9vVMJB8w6EswbSRgxZs7IXTRL4BaJ_iga9K4CJJt_eLzS7a5Owh1ggOzbM3snzYmQ6dKFjIwXptGsgWH0ovr4W7mejeroV2dEffW18AaT-xq_ptiJq0fHUq8cQruYjqHPGno503K3znoHlxqp0CqJg6EtFA8eACIIUKZotAodEIe-rtzBUdEcPRj2mZIVA&utm_content=newpq&utm_term=newjourney&visa_type=d32";
            
            driver.navigate().to(resultsUrl);
          
            
            logger.info("Explore plans process completed successfully");
        } catch (Exception e) {
            logger.error("Error during explore plans button click", e);
            throw e;
        }
    }
}

    
    

    
    
