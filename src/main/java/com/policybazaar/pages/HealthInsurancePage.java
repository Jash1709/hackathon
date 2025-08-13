package com.policybazaar.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.policybazaar.utils.ConfigReader;
import com.policybazaar.utils.ExcelUtil;

public class HealthInsurancePage {

	private static final Logger logger = LogManager.getLogger(HealthInsurancePage.class);
	private final WebDriver driver;
	private final WebDriverWait wait;

	@FindBy(xpath = "//a[normalize-space()='Insurance Products']")
	private WebElement insuranceProductsMenu;

	// Strictly anchors under the 'Health Insurance' heading's list (FindBy, not static By)
	@FindBy(xpath = "//h3[contains(normalize-space(),'Health Insurance')]/following-sibling::ul//a[@href and normalize-space()!='']")
	private List<WebElement> healthInsuranceAnchors;

	public HealthInsurancePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
		PageFactory.initElements(driver, this);
		logger.info("HealthInsurancePage initialized");
	}

	private void openMegaMenu() {
		logger.info("Opening Insurance Products mega menu");
		Actions actions = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(insuranceProductsMenu));
		actions.moveToElement(insuranceProductsMenu).pause(Duration.ofMillis(300)).perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ruby-grid")));
	}

	public List<String> getAllHealthInsuranceMenuItems() {
		logger.info("Collecting Health Insurance menu items (Health column only)");
		openMegaMenu();
		wait.until(ExpectedConditions.visibilityOfAllElements(healthInsuranceAnchors));

		List<String> items = new ArrayList<>();
		for (WebElement el : healthInsuranceAnchors) {
			try {
				String text = el.getText().trim();
				if (!text.isEmpty() && !items.contains(text)) {
					items.add(text);
				}
			} catch (Exception ignored) {}
		}
		logger.info("Collected {} health insurance items", items.size());
		return items;
	}

	// Generic method to get items for any heading from the mega menu
	public List<String> getMenuItemsByHeading(String headingText) {
		logger.info("Collecting menu items for heading: {}", headingText);
		openMegaMenu();
		By dynamicBy = By.xpath(String.format("//h3[contains(normalize-space(),'%s')]/following-sibling::ul//a[@href and normalize-space()!='']", headingText));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(dynamicBy));
		List<WebElement> anchors = driver.findElements(dynamicBy);
		List<String> items = new ArrayList<>();
		for (WebElement a : anchors) {
			String text = a.getText().trim();
			if (!text.isEmpty() && !items.contains(text)) items.add(text);
		}
		logger.info("Collected {} items for heading {}", items.size(), headingText);
		return items;
	}

	public void exportHealthInsuranceMenuItemsToExcel(String fileName, String sheetName) {
		List<String> items = getAllHealthInsuranceMenuItems();
		ExcelUtil.writeListToExcel(items, fileName, sheetName);
		logger.info("Exported Health Insurance items to Excel: {} (sheet: {})", fileName, sheetName);
	}

	public void printHealthInsuranceMenuItems() {
		List<String> items = getAllHealthInsuranceMenuItems();
		System.out.println("=== Health Insurance Menu Items ===");
		for (String item : items) {
			System.out.println(item);
		}
	}
}