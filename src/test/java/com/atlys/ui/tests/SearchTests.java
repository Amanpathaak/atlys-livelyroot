
package com.atlys.ui.tests;

import com.atlys.common.BaseTest;
import com.atlys.ui.pages.HomePage;
import com.atlys.ui.pages.SearchResultsPage;
import com.atlys.ui.locators.HomePageLocators;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"ui"})
public class SearchTests extends BaseTest {

    @Test(description = "TC-01: Positive search returns results for a valid keyword",priority = 1)
    public void testPositiveSearchReturnsResults() {
        String keyword = "monstera";
        HomePage home = new HomePage(driver, cfg).open();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(HomePageLocators.SEARCH_INPUT));
        home.searchFor(keyword);

        SearchResultsPage results = new SearchResultsPage(driver);
        Assert.assertTrue(results.urlLooksLikeSearch(), "URL should look like a search results page");
        int count = results.getProductCountHeuristic();
        Assert.assertTrue(count > 0, "Expected at least 1 product for keyword: " + keyword);
    }

    @Test(description = "TC-02: Negative search with nonsense shows 'no results' or zero products",priority = 2)
    public void testNegativeSearchShowsNoResults() {
        String keyword = "zzzxqwyqwy123";
        HomePage home = new HomePage(driver, cfg).open();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(HomePageLocators.SEARCH_INPUT));
        home.searchFor(keyword);

        SearchResultsPage results = new SearchResultsPage(driver);
        Assert.assertTrue(results.urlLooksLikeSearch(), "URL should look like a search results page");
        boolean noResults = results.isNoResultsDisplayed();
        int count = results.getProductCountHeuristic();
        Assert.assertTrue(noResults || count == 0, "Expected no results message or zero products for nonsense term");
    }

    @Test(description = "TC-03: Leading/trailing spaces are trimmed and still return results for a valid keyword",priority = 3)
    public void testSearchTrimsSpaces() {
        String keyword = "  snake plant  ";
        HomePage home = new HomePage(driver, cfg).open();
        home.searchFor(keyword);

        SearchResultsPage results = new SearchResultsPage(driver);
        Assert.assertTrue(results.urlLooksLikeSearch(), "URL should look like a search results page");
        int count = results.getProductCountHeuristic();
        Assert.assertTrue(count > 0, "Expected results even with padded spaces");
    }
}
