package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.logging.Level;

/**
 * Home page object for Periplus website
 */
public class HomePage extends BasePage {

    // Locators
    private final By signInLink = By.id("nav-signin-text");
    private final By searchField = By.id("filter_name");
    private final By searchButton = By.cssSelector("button.btnn[type='submit']");
    private final By searchResults = By.cssSelector(".single-product");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String url) {
        logger.log(Level.INFO, "Navigating to URL: {0}", url);
        driver.get(url);
    }

    public SignInPage clickSignIn() {
        WebElement signInSpan = wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        signInSpan.click();
        logger.log(Level.INFO, "Clicked Sign In link.");
        return new SignInPage(driver);
    }

    public SearchResultsPage searchForProduct(String searchTerm) {
        try {
            WebElement searchFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
            searchFieldElement.clear();
            searchFieldElement.sendKeys(searchTerm);
            logger.log(Level.INFO, "Search term entered: {0}", searchTerm);

            waitForPreloaderToDisappear();

            WebElement searchButtonElement = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
            searchButtonElement.click();
            logger.log(Level.INFO, "Clicked search button.");

            wait.until(ExpectedConditions.visibilityOfElementLocated(searchResults));

            waitForPreloaderToDisappear();

            return new SearchResultsPage(driver);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error searching for product: {0}", e.getMessage());
            throw e;
        }
    }
}