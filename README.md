# PolicyBazaar Automation

End-to-end Selenium automation for PolicyBazaar Travel Insurance with results extraction and Excel export.

## Tech Stack
- Java 11
- Maven
- Selenium 4
- TestNG (optional)
- WebDriverManager
- Apache POI (Excel)
- Log4j2

## Project Structure
```
policybazaar/
  src/main/java/
    com/policybazaar/main/Main.java
    com/policybazaar/pages/...
    com/policybazaar/utils/...
    com/policybazaar/models/...
  src/main/resources/
    log4j2.xml
```

## Setup
1. Java 11+ and Maven installed
2. Clone the repository
3. Import as Maven project in IDE
4. Maven update (download deps)

## Run
- From IDE: run `com.policybazaar.main.Main`
- From Maven: `mvn -q -DskipTests exec:java` (with suitable exec plugin) or run via IDE

## What it Does
- Opens PolicyBazaar Travel flow
- Fills the form via page objects
- Navigates to results (quotes) page
- Extracts first 3 plans (Provider::Price)
- Writes to Excel: `travel_insurance_plans.xlsx` (sheet: `Top 3 Plans`)
- Logs to console and `logs/automation.log`

## Logs
- Config: `src/main/resources/log4j2.xml`
- Output: `logs/automation.log`

## Excel Output
- Utility: `ExcelUtil.writeToExcel(map, fileName, sheetName)`
- Default example: `travel_insurance_plans.xlsx`, sheet `Top 3 Plans`

## Contributing
- Create a feature branch: `feature/<short-desc>`
- Follow clean commit messages
- Open a PR to `main`

## .gitignore
- Includes target/, logs/, test-output/, IDE files, and local artifacts

## Notes
- If ChromeDriver fails, ensure Chrome is installed and up to date
- For GitHub Actions integration, add a workflow under `.github/workflows/` 