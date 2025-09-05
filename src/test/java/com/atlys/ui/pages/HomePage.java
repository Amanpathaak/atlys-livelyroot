package com.atlys.ui.pages;

import com.atlys.common.TestConfig;
import com.atlys.ui.locators.HomePageLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String baseUrl;

    public HomePage(WebDriver driver, TestConfig cfg) {
        this.driver = driver;
        this.baseUrl = cfg.getBaseUrl();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public HomePage open() {
        driver.get(baseUrl);
        return this;
    }

    public void openSearchIfCollapsed() {
        // Some themes hide search behind an icon
        try {
            WebElement searchBtn = wait.until(ExpectedConditions.presenceOfElementLocated(HomePageLocators.SEARCH_ICON_BUTTON));
            if (searchBtn.isDisplayed()) {
                try { searchBtn.click(); } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
    }

    public void searchFor(String term) {
        // openSearchIfCollapsed();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(HomePageLocators.SEARCH_INPUT));
        input.clear();
        input.sendKeys(term);
        try {
            WebElement submit = driver.findElement(HomePageLocators.SUBMIT_SEARCH);
            submit.click();
        } catch (Exception e) {
            input.submit(); // fallback
        }
    }
}
