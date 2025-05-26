package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base page class containing common functionality for all pages
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Logger logger;

    // Common locators
    protected final By preloader = By.cssSelector(".preloader");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Wait for preloader to disappear
     */
    protected void waitForPreloaderToDisappear() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(preloader));
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}