package com.policybazaar.tests;

import com.policybazaar.pages.TravelInsuranceHomePage;
import com.policybazaar.pages.TravelInsuranceResultsPage;
import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public class TravelInsuranceTest {

    private WebDriver driver;
    private TravelInsuranceHomePage homePage;
    private TravelInsuranceResultsPage resultsPage;

    @BeforeClass
    public void setUp() {
        DriverSetup.initializeDriver(ConfigReader.getProperty("browser"));
        DriverSetup.navigateToApplication();
        driver = DriverSetup.getDriver();

        // Use HomePage to click into Travel Insurance
        homePage = new TravelInsuranceHomePage(driver);
        
        resultsPage = new TravelInsuranceResultsPage(driver);
    }

    @Test(priority = 0, groups = {"medical-no","medical-yes"})
    public void verifyBrowserOpenedAndLandingPageLoaded() {
        Assert.assertNotNull(driver, "WebDriver is null");
        Assert.assertTrue(driver.getWindowHandles().size() > 0, "No browser window opened");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("policybazaar.com"), "Unexpected landing URL: " + currentUrl);
    }

    @Test(priority = 1, dependsOnMethods = "verifyBrowserOpenedAndLandingPageLoaded", groups = {"medical-no"})
    public void fillTravelDetails_ForTwoStudents_Europe() {
    	homePage.clickTravelInsurance();
    	homePage.fillFormWithNo();
    }

    @Test(priority = 2, dependsOnMethods = "fillTravelDetails_ForTwoStudents_Europe", groups = {"medical-no"})
    public void verifyNavigationToResultsPage() {
        resultsPage.waitForResultsToLoad();
        String resultsUrl = driver.getCurrentUrl();
        Assert.assertTrue(resultsUrl.contains("/quotes"), "User is not navigated to results page. Current URL: " + resultsUrl);
        List<WebElement> planCards = driver.findElements(By.xpath("//*[contains(@class,'plan')]"));
        Assert.assertTrue(planCards.size() > 0, "No plan cards present on the results page");
    }

    @Test(priority = 3, dependsOnMethods = "verifyNavigationToResultsPage", groups = {"medical-no"})
    public void verifyResults_SortLowToHigh_ExtractTop3AndWriteExcel() {
        resultsPage.waitForResultsToLoad();

    	resultsPage.sortLowtoHigh();

        LinkedHashMap<String, String> top3Plans = resultsPage.extractFirst3Plans(false); // Medical condition = No
        Assert.assertTrue(top3Plans.size() > 0, "No plans extracted");

        File outFile = new File("travel_insurance_plans.xlsx");
        Assert.assertTrue(outFile.exists(), "Expected Excel file not found: " + outFile.getAbsolutePath());
        Assert.assertTrue(outFile.length() > 0, "Excel file appears to be empty: " + outFile.getAbsolutePath());
    }

    @Test(priority = 4, dependsOnMethods = "verifyBrowserOpenedAndLandingPageLoaded", groups = {"medical-yes"})
    public void fillTravelDetails_WithMedicalConditionYes() {
        // Reopen home and navigate via HomePage each time
        DriverSetup.navigateToApplication();
        homePage = new TravelInsuranceHomePage(driver);
        homePage.clickTravelInsurance();
        resultsPage = new TravelInsuranceResultsPage(driver);
        homePage.fillFormWithYes();
    }

    @Test(priority = 5, dependsOnMethods = "fillTravelDetails_WithMedicalConditionYes", groups = {"medical-yes"})
    public void verifyNavigationToResultsPage_Yes() {
        resultsPage.waitForResultsToLoad();
        String resultsUrl = driver.getCurrentUrl();
        Assert.assertTrue(resultsUrl.contains("/quotes"), "User is not navigated to results page. Current URL: " + resultsUrl);
        List<WebElement> planCards = driver.findElements(By.xpath("//*[contains(@class,'plan')]"));
        Assert.assertTrue(planCards.size() > 0, "No plan cards present on the results page");
    }

    @Test(priority = 6, dependsOnMethods = "verifyNavigationToResultsPage_Yes", groups = {"medical-yes"})
    public void verifyResults_SortLowToHigh_ExtractTop3AndWriteExcel_Yes() {
        resultsPage.waitForResultsToLoad();

    	resultsPage.sortLowtoHigh();

        LinkedHashMap<String, String> top3Plans = resultsPage.extractFirst3Plans(true); // Medical condition = Yes
        Assert.assertTrue(top3Plans.size() > 0, "No plans extracted");

        File outFile = new File("travel_insurance_plans.xlsx");
        Assert.assertTrue(outFile.exists(), "Expected Excel file not found: " + outFile.getAbsolutePath());
        Assert.assertTrue(outFile.length() > 0, "Excel file appears to be empty: " + outFile.getAbsolutePath());
    }

    @Test(priority = 7, groups = {"validation"})
    public void testValidationError_IncompleteAgeSelection() {
        // Navigate to fresh page for validation test
        DriverSetup.navigateToApplication();
        homePage = new TravelInsuranceHomePage(driver);
        homePage.clickTravelInsurance();
        
        // Fill form with incomplete data (missing second traveller age)
        homePage.fillFormWithValidation();
        
        // Check if validation error appears on page
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("Please") || pageSource.contains("required") || pageSource.contains("error"), 
            "Validation error message should appear on page");
    }

    @AfterClass()
    public void tearDown() {
        DriverSetup.tearDown();
    }
} 