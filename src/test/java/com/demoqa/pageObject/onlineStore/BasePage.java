package com.demoqa.pageObject.onlineStore;

import com.demoqa.pageObject.PageObjectImplementation;
import com.demoqa.pageObject.onlineStore.productsPage.CheckoutPage;
import com.demoqa.pageObject.onlineStore.productsPage.ProductCategoryPage;
import com.demoqa.util.TestLogger;
import com.demoqa.util.exceptions.NullValueException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @author amitaarya
 */
public class BasePage<T> extends PageObjectImplementation {

    protected By productCategoryMenu = By.linkText("Product Category");
    protected By cartIcon = By.className("cart_icon");
    protected By cartItems = By.cssSelector(".cart_icon .count");
    protected By pageHeader = By.className("entry-title");

    public BasePage(WebDriver driver) {
        super(driver);
    }


    public ProductCategoryPage selectProductSubCategory(String productSubCategory) {

        if (productSubCategory!=null) {
            TestLogger.log("Looking for the product category: " + productSubCategory);
            WebElement category = waitForElementToAppear(productCategoryMenu);

            Actions action = new Actions(driver);
            action.moveToElement(category).build().perform();
            findElementByLinkText(productSubCategory).click();

            TestLogger.log("Selected product category " + productSubCategory);
            return new ProductCategoryPage(getDriver());
        } else {
            throw new NullValueException("The product subcategory name provided is null");
        }
    }


    public CheckoutPage clickCart() {
        waitForElementToAppear(cartIcon).click();
        TestLogger.log("Opened the cart for checkout");
        return new CheckoutPage(getDriver());
    }

    public String getPageTitle() {
        return findElement(pageHeader).getText();
    }

    public String getCartItems() {
        return findElement(cartItems).getText().trim();
    }
}
