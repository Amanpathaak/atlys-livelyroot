package com.atlys.ui.locators;

import org.openqa.selenium.By;

public class SearchResultsLocators {
    public static final By RESULTS_TITLE = By.cssSelector("h1, h2");
    public static final By PRODUCT_LINKS = By.cssSelector("a[href*='/products/']");
    public static final By NO_RESULTS = By.xpath("//span[contains(normalize-space(.),\"didn't match any results\")]");
    public static final By RESULTS_CONTAINER = By.cssSelector("[class*='product-grid'], [data-results], .grid");
}
