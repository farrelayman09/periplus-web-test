package org.example;

import org.example.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple test for Periplus adding to shopping cart functionality
 * Refactored to use Page Object Model pattern
 */
public class AppTest {

    private static final Logger logger = Logger.getLogger(AppTest.class.getName()); // Initialize Logger


    // WebDriver instance
    private WebDriver driver;

    // Page objects
    private HomePage homePage;
    private SignInPage signInPage;
    private MyAccountPage myAccountPage;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;

    private static final String PERIPLUS_URL = "https://www.periplus.com/";

    // Test data (replace with your actual test account credentials)
    private static final String EMAIL = "<email>";
    private static final String PASSWORD = "<password>";

    @BeforeMethod
    public void setUp() {
        // Set up Chrome driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // Start maximized

        driver = new ChromeDriver(options);

        // Initialize page objects
        homePage = new HomePage(driver);
        signInPage = new SignInPage(driver);
        myAccountPage = new MyAccountPage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    public void testAddProductToCart() {
        logger.log(Level.INFO, "Starting test: Add product to cart in Periplus");

        homePage.navigateTo(PERIPLUS_URL);
        logger.log(Level.INFO, "Successfully navigated to Periplus website: {0}", PERIPLUS_URL);

        signInPage = homePage.clickSignIn();
        homePage = signInPage.login(EMAIL, PASSWORD);
        Assert.assertTrue(myAccountPage.isUserLoggedIn(), "User should be logged in after login attempt.");
        logger.log(Level.INFO, "Successfully logged in with email: {0}", EMAIL);

        String searchTerm = "to kill a mockingbird";
        searchResultsPage = homePage.searchForProduct(searchTerm);
        logger.log(Level.INFO, "Successfully searched for product: {0}", searchTerm);

        productPage = searchResultsPage.clickFirstProduct();
        logger.log(Level.INFO, "Succesfully clicked on the first product in search results.");

        String productName = productPage.getProductName();
        productPage.addToCart();
        logger.log(Level.INFO, "Succesfully added product to cart: {0}", productName);

        cartPage = productPage.goToCart();
        boolean isProductInCart = cartPage.isProductInCart(productName);
        logger.log(Level.INFO, "Navigated to cart. Verifying if product ''{0}'' is in cart, along " +
                "with correct quantity and visible subtotal.", productName);

        // Final assertion
        Assert.assertTrue(isProductInCart, "Product should be added to cart successfully");
        logger.log(Level.INFO, "Successfully verified product ''{0}'' is in cart with correct " +
                "quantity and visible subtotal. Test completed successfully.", productName);
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}