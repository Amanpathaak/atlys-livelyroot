package com.atlys.ui.locators;

import org.openqa.selenium.By;

public class HomePageLocators {
    // Search input & button (Shopify theme patterns, adjust if needed)
    public static final By SEARCH_ICON_BUTTON = By.xpath("//div[contains(@class,'header-item--search') and contains(@class,'small--hide')]//form[@role='search']//button[@type='submit']"); //avoiding indexing else can use: (//button[@class='btn--search'])[1]
    public static final By SEARCH_INPUT = By.xpath("//div[contains(@class,'header-item--search') and contains(@class,'small--hide')]//form[@role='search']//input[@type='search']");
    public static final By SUBMIT_SEARCH = By.xpath("//div[contains(@class,'header-item--search') and contains(@class,'small--hide')]//form[@role='search']//button[@type='submit']");
}

