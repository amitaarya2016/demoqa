package com.demoqa.pageObject;

import com.demoqa.util.TestLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author amitaarya
 */
public class PageObjectImplementation implements PageObject {
    //
    public final WebDriver driver;
    public final WebDriverWait wait;

    public PageObjectImplementation(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Long.parseLong(System.getProperty("wait_timeout")));
        TestLogger.log("Opening the browser for the test");
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return this.wait;

    }

    protected WebElement findElement(By selector) {
        try {
            return getDriver().findElement(selector);
        } catch (NoSuchElementException e) {
            throw new ElementNotFoundException("Could not find element " + selector, e);
        } catch (StaleElementReferenceException e) {
            return getDriver().findElement(selector);
        }
    }

    protected List<WebElement> findElements(By selector) {
        return getDriver().findElements(selector);
    }

    protected boolean doesElementExist(By selector) {
        try {
            getDriver().findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected WebElement waitForElementToAppear(By selector) {
        TestLogger.log("Waiting for the element " + selector + " to appear on the page");
        return getWait().until((ExpectedCondition<WebElement>) webDriver -> {
            List<WebElement> elements = findElements(selector);
            for (WebElement element : elements) {
                try {
                    if (element.isDisplayed()) {
                        TestLogger.log("The element " + selector + " appeared on the page");
                        return element;
                    }
                } catch (StaleElementReferenceException e) {
                    // Do nothing -- stale element reference just means the element disappeared from the dom before we could check whether it is displayed.
                }
            }
            return null;
        });
    }

    protected WebElement waitForElementToAppearByAttributeValue(By selector, String attribute, String attributeValue) {

        getWait().until(ExpectedConditions.attributeContains(selector, attribute, attributeValue));
        return getWait().until((ExpectedCondition<WebElement>) webDriver -> {
            List<WebElement> elements = findElements(selector);
            for (WebElement element : elements) {
                try {
                    if (element.getAttribute(attribute).equals(attributeValue)) {
                        TestLogger.log("The element " + selector + " attribute " + selector + " is updated to " + attributeValue);
                        return element;
                    }
                } catch (StaleElementReferenceException | org.openqa.selenium.TimeoutException e) {
                    // Do nothing -- stale element reference just means the element disappeared from the dom before we could check whether it is displayed.
                }
            }
            return null;
        });
    }

    protected WebElement findElementByLinkText(String linkText) {
        By by = By.linkText(linkText);
        return waitForElementToAppear(by);

    }

    final class ElementNotFoundException extends RuntimeException {
        public ElementNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public ElementNotFoundException(String message) {
            super(message);
        }
    }

}
