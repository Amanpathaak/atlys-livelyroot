package com.atlys.ui.pages;

import com.atlys.ui.locators.SearchResultsLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public String getTitleText() {
        WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(SearchResultsLocators.RESULTS_TITLE));
        return title.getText().trim();
    }

    public int getProductCountHeuristic() {
        wait.until(ExpectedConditions.or(
            ExpectedConditions.presenceOfElementLocated(SearchResultsLocators.PRODUCT_LINKS),
            ExpectedConditions.presenceOfElementLocated(SearchResultsLocators.RESULTS_CONTAINER),
            ExpectedConditions.presenceOfElementLocated(SearchResultsLocators.NO_RESULTS)
        ));
        List<WebElement> links = driver.findElements(SearchResultsLocators.PRODUCT_LINKS);
        int visibleCount = 0;
        for (WebElement link : links) {
            if (link.isDisplayed()) {
                visibleCount++;
            }
        }
        return visibleCount;
    }

    public boolean isNoResultsDisplayed() {
        try {
            WebElement nores = driver.findElement(SearchResultsLocators.NO_RESULTS);
            return nores.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean urlLooksLikeSearch() {
        return driver.getCurrentUrl().toLowerCase().contains("/search") ||
               driver.getCurrentUrl().toLowerCase().contains("q=");
    }
}
