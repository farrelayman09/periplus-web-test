package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.logging.Level;

/**
 * Sign-in page object for Periplus website
 */
public class SignInPage extends BasePage {

    // Locators
    private final By emailField = By.name("email");
    private final By passwordField = By.name("password");
    private final By loginButton = By.cssSelector("input[type='submit'].buton#button-login");

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public HomePage login(String email, String password) {
        try {
            WebElement emailFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            emailFieldElement.clear();
            emailFieldElement.sendKeys(email);
            logger.log(Level.INFO, "Entered email into email field.");


            WebElement passwordFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
            passwordFieldElement.clear();
            passwordFieldElement.sendKeys(password);
            logger.log(Level.INFO, "Entered password into password field.");

            WebElement loginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButtonElement.click();
            logger.log(Level.INFO, "Clicked login button.");

            return new HomePage(driver);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during login for email {0}: {1}", new Object[]{email, e.getMessage()});
            throw e;
        }
    }
}