package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.logging.Level;

/**
 * Cart page object for Periplus website
 */
public class CartPage extends BasePage {

    // Locators
    private final By cartProductRow = By.cssSelector(".row.row-cart-product");
    private final By quantityInput = By.cssSelector(".input-number.text-center");
    private final By subtotal = By.cssSelector("#sub_total");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProductInCart(String productName) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductRow));

            // Check if product is in cart
            try {
                boolean cartItemIsDisplayed = isCartItemDisplayed(productName);

                String quantityValue = getQuantity();
                assert quantityValue.equals("1") : "Expected quantity to be 1 but found " + quantityValue;

                boolean subtotalIsDisplayed = isSubtotalDisplayed();

                return cartItemIsDisplayed && subtotalIsDisplayed;
            } catch (Exception e) {
                return false;
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error verifying product in cart: {0}", e.getMessage());
            throw e;
        }
    }

    public boolean isCartItemDisplayed(String productName) {
        WebElement cartItemElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(@class,'product-name')]" +
                        "[contains(.,'" + productName + "')]")));
        return cartItemElement.isDisplayed();
    }


    public String getQuantity() {
        WebElement quantityInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        return quantityInputElement.getAttribute("value");
    }

    public boolean isSubtotalDisplayed() {
        try {
            WebElement subtotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(subtotal));
            return subtotalElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}