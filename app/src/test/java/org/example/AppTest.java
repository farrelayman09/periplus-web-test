package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

/**
 * Simple test for Periplus adding to shopping cart functionality
 */
public class AppTest {
    
    // WebDriver instance
    private WebDriver driver;
    
    // WebDriverWait for handling dynamic elements
    private WebDriverWait wait;

    // Test data (replace with your actual test account)
    private static final String EMAIL = "<your_email>";
    private static final String PASSWORD = "<your_password>";

    @BeforeMethod
    public void setUp() {
        // Set up Chrome driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Start maximized
        
        driver = new ChromeDriver(options);
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @Test
    public void testAddProductToCart() {
        System.out.println("Starting test: Add product to cart in Periplus");
        
        driver.get("https://www.periplus.com/");
        System.out.println("Navigated to Periplus website");
        
        login(EMAIL, PASSWORD);
        System.out.println("Logged in successfully");

        String searchTerm = "harimau harimau";
        findProduct(searchTerm);
        System.out.println("Found product: " + searchTerm);
        
        String productName = addToCart();
        System.out.println("Added product to cart: " + productName);
        
        boolean isProductInCart = verifyProductInCart(productName);
        
        // Final assertion
        Assert.assertTrue(isProductInCart, "Product should be added to cart successfully");
        System.out.println("Test completed successfully");
    }
    
    /**
     * Login to Periplus website
     */
    private void login(String email, String password) {
        try {
            WebElement signInSpan = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("nav-signin-text")));
            signInSpan.click();
            
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("email")));
            emailField.clear();
            emailField.sendKeys(email);
            
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.name("password")));
            passwordField.clear();
            passwordField.sendKeys(password);

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input[type='submit'].buton#button-login")));
            loginButton.click();

        } catch (Exception e) {
            System.err.println("Error during login: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Search for a product
     */
    private void findProduct(String searchTerm) {
        try {

            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("filter_name")));
            searchField.clear();
            searchField.sendKeys(searchTerm);
            System.out.println("Search term entered: " + searchTerm);

            // Wait for the preloader to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));
            System.out.println("Preloader has disappeared.");

            // Click search button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button.btnn[type='submit']")));
            searchButton.click();
            
            // Wait for search results
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".single-product")));

            // Wait for the preloader to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));
            System.out.println("Preloader has disappeared (after search results).");
            
            WebElement firstProductLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".single-product:first-child .product-img a")));
            firstProductLink.click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".quickview-binding, .quickview-author")));
            
        } catch (Exception e) {
            System.err.println("Error finding product: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Add current product to cart
     * @return the name of the added product
     */
    private String addToCart() {
        String productName = "";
        
        try {
            WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("h2")));
            productName = nameElement.getText().trim();

            // Wait for the preloader to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".preloader")));
            System.out.println("Preloader has disappeared (before Add to Cart).");
            
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".btn.btn-add-to-cart")));
            addToCartButton.click();

            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector(".modal-text"), "Success add to cart"));
            System.out.println("Success modal is visible.");
            
        } catch (Exception e) {
            System.err.println("Error adding product to cart: " + e.getMessage());
            throw e;
        }
        
        return productName;
    }
    
    /**
     * Verify product is in cart
     */
    private boolean verifyProductInCart(String productName) {
        try {
            // Wait for the notification modal to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#Notification-Modal.show")));
            System.out.println("Notification modal has disappeared.");

            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("#show-your-cart a[href*='checkout/cart']")));
            checkoutButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".row.row-cart-product")));

            // Check if product is in cart
            try {
                WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(@class,'product-name')]" +
                                "[contains(.,'" + productName + "')]")));

                WebElement quantityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".input-number.text-center")));
                String quantityValue = quantityInput.getAttribute("value");
                assert quantityValue.equals("1") : "Expected quantity to be 1 but found " + quantityValue;

                WebElement subtotal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#sub_total")));

                return cartItem.isDisplayed() && subtotal.isDisplayed();
            } catch (Exception e) {
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Error verifying product in cart: " + e.getMessage());
            throw e;
        }
    }
    
    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}