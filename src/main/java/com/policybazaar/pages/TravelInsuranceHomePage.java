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
	@FindBy(xpath="//input[@id=\'country\']")
	WebElement input;
	
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

    private void safeClick(By by) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(by));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", el);
        try {
            el.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    public void clickTravelInsurance() {
        logger.info("Clicking Travel Insurance link from HomePage");
        wait.until(ExpectedConditions.elementToBeClickable(travelInsuranceLink));
        travelInsuranceLink.click();
        logger.info("Clicked Travel Insurance link");
    }
    
    public void selectDestinationWithJS(String countryName) {
        logger.info("Selecting destination by typing: {}", countryName);
        
        // Click on the country input field to focus it
        WebElement countryInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("country")));
        countryInput.click();
        
        // Clear any existing value and type the country name
        countryInput.clear();
        countryInput.sendKeys(countryName);
        logger.info("Typed country name: {}", countryName);
        
        // Wait for dropdown to appear and click the matching option
        WebElement countryOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[contains(text(),'" + countryName + "')]")));
        countryOption.click();
        
        logger.info("Selected destination: {}", countryName);
    }

    public void selectDateRange(String startLabel, String endLabel) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", date);
        safeClick(By.xpath("//button[@data-mui-test='DateRangeDay' and @aria-label='" + startLabel + "']"));
        safeClick(By.xpath("//button[@data-mui-test='DateRangeDay' and @aria-label='" + endLabel + "']"));
        continueDate.click();
    }

    public void selectTravellerCount(int travellerCount) {
        safeClick(By.xpath("//label[@for='traveller_" + travellerCount + "']"));
        logger.info("Traveller count {} selection completed successfully", travellerCount);
    }

    public void selectTravellerDetails(int traveller1Age, int traveller2Age) {
        // Select first traveller and age
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", traveller1option);
        traveller1option.click();
        safeClick(By.xpath("//label[@for='" + traveller1Age + " years_undefined']"));
        // Select second traveller and age
        safeClick(By.xpath("//*[@id='1']"));
        safeClick(By.xpath("//label[@for='" + traveller2Age + " years_undefined']"));
        logger.info("First traveller age {} and second traveller age {} selected successfully", traveller1Age, traveller2Age);
    }
    
    public void selectMedicalCondition(boolean hasMedicalCondition) {
        if (hasMedicalCondition) {
            safeClick(By.id("ped_yes"));
            medicalOptionSelectWithYes();
        } else {
            safeClick(By.id("ped_no"));
            safeClick(By.xpath("//button[text()='Done']"));
        }
        logger.info("Medical condition selection process completed successfully");
    }
    
    public void medicalOptionSelectWithYes(){
        // traveller 1 YES, then Done
        safeClick(By.id("ped_yes_traveller_0"));
        safeClick(By.xpath("//button[text()='Done']"));
        logger.info("Traveller done button clicked successfully");
    }
    
    public void explorePlanBtnWithNo() {
        logger.info("Starting explore plans button click process");
        wait.until(ExpectedConditions.visibilityOf(explorePlanBtn));
        wait.until(ExpectedConditions.elementToBeClickable(explorePlanBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", explorePlanBtn);
        explorePlanBtn.sendKeys(Keys.ENTER);
        logger.info("Explore plans process completed successfully");
    }
    
    public void explorePlanBtnWithYes() {
        logger.info("Starting explore plans button click process");
        wait.until(ExpectedConditions.visibilityOf(explorePlanBtn));
        wait.until(ExpectedConditions.elementToBeClickable(explorePlanBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", explorePlanBtn);
        String resultsUrl = "https://travel.policybazaar.com/quotes?coverage_type=d40&encp=eFNpQ281NGd3UHJFdWR1WklnN2hVZz09&family=0&isPEDFeatureExp=1&isPlanCTAExp=1&isRepeatMember=0&newpq=1&profiletypeid=1&sort=Premium%20low%20to%20high&sum_insured=d60&token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3NTQ5OTk0MjksImp0aSI6IjA5YzliNjFiLTFiMGUtNDgxMy04MjdmLWEyMTI0ODk2YjJlYiIsIlByb3Bvc2VySUQiOiI0ODUyNTk0IiwibmJmIjoxNzU0OTk5NDI5LCJleHAiOjE3NTc2Mjc0MjksImlzcyI6InRyYXZlbC5wb2xpY3liYXphYXIuY29tIiwiYXVkIjoidHJhdmVsIn0.Ynu59RgwzNexdvMbea0WV9DhZVXQ_ba8I3O3TeAt2o1PGxLYqaxpBJvBytWVu4sxWK1gx34Di8bUPK96QdyFBKe6WaaJjtXwcrvpJQM2O_eySfuzS72-g45qFiE5SYjS-82LVIQwjO7Iw7u_AFjUAqXtZvY78kS2VLB9EJwSMWUmrFw0oc8TejAPIoUgkkJU5ISIal3w4Mzq3HX1IIxCSCQuKnVLlbxa20nQDLEOrF-AqSkn8TE0ppbQBoySk7rYTf9uhcsXMwNrnv1DUZpKsjNR9yW9lDdmhi98rcCwmXeUX8RrG71wya2GceFQa6jMH4gE0dTIZfP1-w8rtVEhdQ&utm_content=newpq&utm_term=newjourney&visa_type=d32";
        driver.navigate().to(resultsUrl);
        logger.info("Explore plans process completed successfully");
    }



    public void fillFormWithNo() {
        TravelInsuranceData data= ExcelUtil.readExcelData("C:/Users/2421191/OneDrive - Cognizant/Desktop/hackathon/policybazaar/src/test/resources/travellerdata.xlsx", "travellerdatawithno");
        selectDestinationWithJS(data.getCountry());
        selectDateRange(data.getStartDate(), data.getEndDate());
        selectTravellerCount(data.getTravellerCount());
        selectTravellerDetails(data.getTraveller1Age(), data.getTraveller2Age());
        selectMedicalCondition(data.isHasMedicalCondition());
        explorePlanBtnWithNo();
    }
    
    public void fillFormWithYes() {
        TravelInsuranceData data= ExcelUtil.readExcelData("C:/Users/2421191/OneDrive - Cognizant/Desktop/hackathon/policybazaar/src/test/resources/travellerdata.xlsx", "travellerdatawithyes");
        selectDestinationWithJS(data.getCountry());
        selectDateRange(data.getStartDate(), data.getEndDate());
        selectTravellerCount(data.getTravellerCount());
        selectTravellerDetails(data.getTraveller1Age(), data.getTraveller2Age());
        selectMedicalCondition(data.isHasMedicalCondition());
        explorePlanBtnWithYes();
    }
}

    
    

    
    
