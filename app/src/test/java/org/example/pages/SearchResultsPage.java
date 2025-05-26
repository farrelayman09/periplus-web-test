package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.logging.Level;

/**
 * Search results page object for Periplus website
 */
public class SearchResultsPage extends BasePage {

    // Locators
    private final By firstProductLink = By.cssSelector(".single-product:first-child .product-img a");
    private final By productDetailsIndicator = By.cssSelector(".quickview-binding, .quickview-author");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public ProductPage clickFirstProduct() {
        try {
            WebElement firstProductLinkElement = wait.until(ExpectedConditions.elementToBeClickable(firstProductLink));
            firstProductLinkElement.click();
            logger.log(Level.INFO, "Clicking first product.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(productDetailsIndicator));

            return new ProductPage(driver);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error clicking first product: {0}", e.getMessage());
            throw e;
        }
    }
}