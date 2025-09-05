package com.atlys.common;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;
    protected TestConfig cfg;

    @BeforeClass(alwaysRun = true)
    public void setUpBase() {
        cfg = new TestConfig();
        ChromeOptions options = new ChromeOptions();
        if (cfg.isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1366,768");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options); 
    }

    @AfterClass(alwaysRun = true)
    public void tearDownBase() {
        if (driver != null) {
            driver.quit();
        }
    }
}
