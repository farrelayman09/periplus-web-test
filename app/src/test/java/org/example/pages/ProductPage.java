package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.logging.Level;

/**
 * Product page object for Periplus website
 */
public class ProductPage extends BasePage {

    // Locators
    private final By productNameElement = By.cssSelector("h2");
    private final By addToCartButton = By.cssSelector(".btn.btn-add-to-cart");
    private final By successModal = By.cssSelector(".modal-text");
    private final By notificationModal = By.cssSelector("#Notification-Modal.show");
    private final By checkoutButton = By.cssSelector("#show-your-cart a[href*='checkout/cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameElement));
        return nameElement.getText().trim();
    }

    public void addToCart() {
        try {
            waitForPreloaderToDisappear();

            WebElement addToCartButtonElement = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            addToCartButtonElement.click();

            wait.until(ExpectedConditions.textToBePresentInElementLocated(successModal, "Success add to cart"));
            logger.log(Level.INFO, "Success modal is visible.");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding product to cart: {0}", e.getMessage());
            throw e;
        }
    }

    public CartPage goToCart() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(notificationModal));
            logger.log(Level.INFO, "Notification modal has disappeared.");

            WebElement checkoutButtonElement = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
            checkoutButtonElement.click();
            logger.log(Level.INFO, "Checkout button is clicked.");

            return new CartPage(driver);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error navigating to cart: {0}", e.getMessage());
            throw e;
        }
    }
}