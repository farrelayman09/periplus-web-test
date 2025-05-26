package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.logging.Level;

/**
 * Sign-in page object for Periplus website
 */
public class MyAccountPage extends BasePage {

    // Locators
    private final By personalInformation = By.cssSelector(".row.row-account");

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isUserLoggedIn() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(personalInformation));
            logger.log(Level.INFO, "User appears to be logged in.");
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING, "User does NOT appear to be logged in. Account Page not found or visible: {0}", e.getMessage());
            return false;
        }
    }
}