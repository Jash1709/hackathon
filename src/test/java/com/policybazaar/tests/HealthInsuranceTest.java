package com.policybazaar.tests;

import com.policybazaar.pages.HealthInsurancePage;
import com.policybazaar.utils.DriverSetup;
import com.policybazaar.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class HealthInsuranceTest {

	private WebDriver driver;
	private HealthInsurancePage healthPage;

	@BeforeClass
	public void setUp() {
		DriverSetup.initializeDriver(ConfigReader.getProperty("browser"));
		DriverSetup.navigateToApplication();
		driver = DriverSetup.getDriver();
		healthPage = new HealthInsurancePage(driver);
	}

	@Test(priority = 1)
	public void retrieveAndDisplayHealthInsuranceMenuItems() {
		List<String> items = healthPage.getAllHealthInsuranceMenuItems();
		for (String item : items) {
			System.out.println(item);
		}
		Assert.assertTrue(items != null && !items.isEmpty(), "Health Insurance menu items should not be empty");
	}

	@Test(priority = 2)
	public void verifyNoDuplicatesAndMinimumCount() {
		List<String> items = healthPage.getAllHealthInsuranceMenuItems();
		Set<String> unique = new HashSet<>(items);
		Assert.assertEquals(unique.size(), items.size(), "Duplicate items found in Health Insurance menu");
		Assert.assertTrue(items.size() >= 8, "Expected at least 8 Health Insurance items, found: " + items.size());
	}

	@AfterClass
	public void tearDown() {
		DriverSetup.tearDown();
	}
}