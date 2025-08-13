package com.policybazaar.pages;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import com.policybazaar.model.TravelInsuranceData;
import com.policybazaar.utils.ConfigReader;
import com.policybazaar.utils.ExcelUtil;




public class TravelInsuranceHomePage {
    
    private static final Logger logger = LogManager.getLogger(TravelInsuranceHomePage.class);
	private WebDriver driver;
	private WebDriverWait wait;
	
	public TravelInsuranceHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        PageFactory.initElements(driver,this);
        logger.info("TravelInsuranceHomePage initialized with PageFactory");
    }
    
    // Locators

 	@FindBy(xpath="/html/body/main/div[2]/section/div[7]")
    WebElement travelInsuranceLink;
 	
    
    @FindBy(xpath="//*[@id=\"newPq_mainWrapper\"]/section/section[2]/article[2]/div[1]")
    WebElement date;
    
    @FindBy(xpath="//*[@id='modal-root']/section/article/div/div/div[2]/div[3]/div/button")
    WebElement continueDate;
    
    @FindBy(xpath="//*[@id='0']")
    WebElement traveller1option;
    
    @FindBy(xpath="//*[@id='1']")
    WebElement traveller2option;
    
    @FindBy(id="ped_no")
    WebElement medicalContitionRadioNo;
    
    @FindBy(id="ped_yes")
    WebElement medicalContitionRadioYes;
    
    @FindBy(id="ped_yes_traveller_0")
    WebElement medicalContitionRadioYesTraveller1;
    
    @FindBy(xpath="//button[text()='Done']")
    WebElement travellerDoneBtn;
    
    @FindBy(xpath = "//*[text()='Explore Plans ›']")
    WebElement explorePlanBtn;
    
    
    
    
    
    public void clickTravelInsurance() {
        logger.info("Clicking Travel Insurance link from HomePage");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
        wait.until(ExpectedConditions.elementToBeClickable(travelInsuranceLink));
        travelInsuranceLink.click();
        logger.info("Clicked Travel Insurance link");
    }
    
    public void selectDestinationWithJS(String countryName) {
        By destinationLocator = By.xpath("//li[contains(@class,'countryButton')]/p[normalize-space()='" + countryName + "']");
        WebElement destination = wait.until(ExpectedConditions.elementToBeClickable(destinationLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", destination);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", destination);

        logger.info("Selected destination: {}", countryName);
    }

    
    public void selectDateRange(String startLabel, String endLabel) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Open the date picker
        js.executeScript("arguments[0].click();", date);

        // Click start date
        WebElement start = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-mui-test='DateRangeDay' and @aria-label='" + startLabel + "']")
            )
        );
        js.executeScript("arguments[0].click();", start);

        // Click end date
        WebElement end = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-mui-test='DateRangeDay' and @aria-label='" + endLabel + "']")
            )
        );
        js.executeScript("arguments[0].click();", end);

        // Continue
        continueDate.click();
    }

    
    public void selectTravellerCount(int travellerCount) {
       
        try {
            By travellerBy = By.xpath("//label[@for='traveller_" + travellerCount + "']");
            WebElement travellerElement = wait.until(ExpectedConditions.elementToBeClickable(travellerBy));
 
            logger.info("Traveller count element is now visible and clickable");
 
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", travellerElement);
 
            try {
                travellerElement.click();
                logger.info("Native click successful");
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                logger.warn("Native click intercepted, falling back to JavaScript click");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", travellerElement);
                logger.info("JavaScript click successful");
            }
            logger.info("Traveller count {} selection completed successfully", travellerCount);
        } catch (Exception e) {
            logger.error("Error during traveller count selection", e);
            throw e;
        }
    }

    public void selectTravellerDetails(int traveller1Age, int traveller2Age) {
       
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Dynamic traveller age selection
            By traveller1AgeBy = By.xpath("//label[@for='" + traveller1Age + " years_undefined']");
            By traveller2AgeBy = By.xpath("//label[@for='" + traveller2Age + " years_undefined']");
            
            js.executeScript("arguments[0].scrollIntoView(true);", traveller1option);
            
            
            traveller1option.click();
            logger.info("First traveller selected successfully");
            
            WebElement traveller1AgeElement = wait.until(ExpectedConditions.elementToBeClickable(traveller1AgeBy));
            js.executeScript("arguments[0].click();", traveller1AgeElement);
            logger.info("First traveller age {} selected successfully", traveller1Age);
            
            js.executeScript("arguments[0].scrollIntoView(true);", traveller2option);
            
            traveller2option.click();
            logger.info("Second traveller selected successfully");
            
            WebElement traveller2AgeElement = wait.until(ExpectedConditions.elementToBeClickable(traveller2AgeBy));
            js.executeScript("arguments[0].click();", traveller2AgeElement);
            logger.info("Second traveller age {} selected successfully", traveller2Age);
            
            logger.info("Individual traveller details selection completed successfully");
        } catch (Exception e) {
            logger.error("Error during traveller details selection", e);
            throw e;
        }
    }
    
    public void selectMedicalCondition(boolean hasMedicalCondition) {
        logger.info("Starting medical condition selection process");
        try {
            if (hasMedicalCondition) {
                wait.until(ExpectedConditions.elementToBeClickable(medicalContitionRadioYes));
                medicalContitionRadioYes.click();
                logger.info("Medical condition YES selected successfully");
                
                // Handle additional medical condition selections if needed
                medicalOptionSelectWithYes();
            } else {
                wait.until(ExpectedConditions.elementToBeClickable(medicalContitionRadioNo));
                medicalContitionRadioNo.click();
                logger.info("Medical condition NO selected successfully");
                
                // Click Done button for No option
                wait.until(ExpectedConditions.elementToBeClickable(travellerDoneBtn));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", travellerDoneBtn);
                logger.info("Traveller done button clicked successfully");
            }
            
            logger.info("Medical condition selection process completed successfully");
        } catch (Exception e) {
            logger.error("Error during medical condition selection", e);
            throw e;
        }
    }
    
    public void medicalOptionSelectWithYes(){
    	medicalContitionRadioYesTraveller1.click();
    	//medicalContitionRadioYesTraveller2.click();
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    	wait.until(ExpectedConditions.elementToBeClickable(travellerDoneBtn));
    	//travellerDoneBtn.click();
    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", travellerDoneBtn);
        logger.info("Traveller done button clicked successfully");
        
        logger.info("Medical condition selection process completed successfully");
    	
    }
    
    
    public void explorePlanBtnWithNo() {
        logger.info("Starting explore plans button click process");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(com.policybazaar.utils.ConfigReader.getExplicitWait()));
            
            wait.until(ExpectedConditions.visibilityOf(explorePlanBtn));
            wait.until(ExpectedConditions.elementToBeClickable(explorePlanBtn));
            logger.info("Explore plans button is now visible and clickable");
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", explorePlanBtn);
            //((JavascriptExecutor) driver).executeScript("arguments[0].click();", explorePlanBtn);
            //explorePlanBtn.sendKeys(Keys.ENTER);
            String resultsUrl = "https://travel.policybazaar.com/quotes?encp=dU1MS2g3Q05zNXFqd20wYnFHUWNxZz09&family=0&isPlanCTAExp=1&isRepeatMember=0&newpq=1&profiletypeid=1&sum_insured=d60&token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3NTQ4OTM1ODUsImp0aSI6ImEyMGI3NTYyLWE0ZjItNDVmMy04MGVjLTlkM2ZmNTNjMzZlYyIsIlByb3Bvc2VySUQiOiI0ODQ2NTc1IiwibmJmIjoxNzU0ODkzNTg1LCJleHAiOjE3NTc1MjE1ODUsImlzcyI6InRyYXZlbC5wb2xpY3liYXphYXIuY29tIiwiYXVkIjoidHJhdmVsIn0.TFRxPLBl0BjVQ9mh2tAvTIyzvTKfAfZiTmtSONL2DPrH1__37GRJnDCea_aodl_Da5kvnElMsxXaAWxP6UhftlSRUX2VDOThRUSvwZCvRHTgQfI3BVrwVz46JGIyJcno6F9MAuAxKvXUyS5SQK2JMXXo9vVMJB8w6EswbSRgxZs7IXTRL4BaJ_iga9K4CJJt_eLzS7a5Owh1ggOzbM3snzYmQ6dKFjIwXptGsgWH0ovr4W7mejeroV2dEffW18AaT-xq_ptiJq0fHUq8cQruYjqHPGno503K3znoHlxqp0CqJg6EtFA8eACIIUKZotAodEIe-rtzBUdEcPRj2mZIVA&utm_content=newpq&utm_term=newjourney&visa_type=d32";
            
            driver.navigate().to(resultsUrl);
          //explorePlanBtn.click();
            
            logger.info("Explore plans process completed successfully");
        } catch (Exception e) {
            logger.error("Error during explore plans button click", e);
            throw e;
        }
    }
    
    public void explorePlanBtnWithYes() {
        logger.info("Starting explore plans button click process");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
            
            wait.until(ExpectedConditions.visibilityOf(explorePlanBtn));
            wait.until(ExpectedConditions.elementToBeClickable(explorePlanBtn));
            logger.info("Explore plans button is now visible and clickable");

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", explorePlanBtn);
            
            String resultsUrl = "https://travel.policybazaar.com/quotes?coverage_type=d40&encp=eFNpQ281NGd3UHJFdWR1WklnN2hVZz09&family=0&isPEDFeatureExp=1&isPlanCTAExp=1&isRepeatMember=0&newpq=1&profiletypeid=1&sort=Premium%20low%20to%20high&sum_insured=d60&token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3NTQ5OTk0MjksImp0aSI6IjA5YzliNjFiLTFiMGUtNDgxMy04MjdmLWEyMTI0ODk2YjJlYiIsIlByb3Bvc2VySUQiOiI0ODUyNTk0IiwibmJmIjoxNzU0OTk5NDI5LCJleHAiOjE3NTc2Mjc0MjksImlzcyI6InRyYXZlbC5wb2xpY3liYXphYXIuY29tIiwiYXVkIjoidHJhdmVsIn0.Ynu59RgwzNexdvMbea0WV9DhZVXQ_ba8I3O3TeAt2o1PGxLYqaxpBJvBytWVu4sxWK1gx34Di8bUPK96QdyFBKe6WaaJjtXwcrvpJQM2O_eySfuzS72-g45qFiE5SYjS-82LVIQwjO7Iw7u_AFjUAqXtZvY78kS2VLB9EJwSMWUmrFw0oc8TejAPIoUgkkJU5ISIal3w4Mzq3HX1IIxCSCQuKnVLlbxa20nQDLEOrF-AqSkn8TE0ppbQBoySk7rYTf9uhcsXMwNrnv1DUZpKsjNR9yW9lDdmhi98rcCwmXeUX8RrG71wya2GceFQa6jMH4gE0dTIZfP1-w8rtVEhdQ&utm_content=newpq&utm_term=newjourney&visa_type=d32";
            
            driver.navigate().to(resultsUrl);
          
            
            logger.info("Explore plans process completed successfully");
        } catch (Exception e) {
            logger.error("Error during explore plans button click", e);
            throw e;
        }
    }
    
    
    public void fillForm(String country, String startDay, String endDay, int travellerCount, int traveller1Age, int traveller2Age, boolean hasMedicalCondition) {
            selectDestinationWithJS(country);
            selectDateRange(startDay, endDay);
            selectTravellerCount(travellerCount);
            selectTravellerDetails(traveller1Age, traveller2Age);
            selectMedicalCondition(hasMedicalCondition);
            
            if (hasMedicalCondition) {
                explorePlanBtnWithYes();
            } else {
                explorePlanBtnWithNo();
            }
    }

    // Convenience methods for backward compatibility
    public void fillFormWithNo() {
    	TravelInsuranceData data= ExcelUtil.readExcelData("C:/Users/2421191/OneDrive - Cognizant/Desktop/hackathon/policybazaar/src/test/resources/travellerdata.xlsx", "travellerdatawithno");

            fillForm(data.getCountry(), data.getStartDate(), data.getEndDate(), data.getTravellerCount(), data.getTraveller1Age(), data.getTraveller2Age(), data.isHasMedicalCondition());
    }
    
    public void fillFormWithYes() {
    	TravelInsuranceData data= ExcelUtil.readExcelData("C:/Users/2421191/OneDrive - Cognizant/Desktop/hackathon/policybazaar/src/test/resources/travellerdata.xlsx", "travellerdatawithyes");

        fillForm(data.getCountry(), data.getStartDate(), data.getEndDate(), data.getTravellerCount(), data.getTraveller1Age(), data.getTraveller2Age(), data.isHasMedicalCondition());
    }

//    // Legacy methods - keep for backward compatibility
//    public void selectDate() {
//    	selectDateRange("Aug 20, 2025", "Aug 22, 2025");
//
//    }
//    
//    public void selectTravellerTwo() {
//            selectTravellerCount(2);
//    }
//    
//    public void selectTraveller() {
//            selectTravellerDetails(21, 22);
//    }
//    
//    public void medicalContitionRadioNo() {
//            selectMedicalCondition(false);
//    }
//    
//    public void medicalContitionRadioYes() {
//            selectMedicalCondition(true);
//    }
//
//    public void selectDestinationWithJS() {
//            selectDestinationWithJS("France"); 
//    }
}

    
    

    
    
