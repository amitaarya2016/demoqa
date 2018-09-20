package com.demoqa.pageObject.onlineStore.productsPage;

import com.demoqa.pageObject.onlineStore.BasePage;
import com.demoqa.util.exceptions.NullValueException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author amitaarya
 */
public class ProductCategoryPage extends BasePage {

    protected By addToCartAlertMsg = By.cssSelector(".alert.addtocart");
    protected By products = By.className("productcol");
    protected By productNameLink = By.className("wpsc_product_title");
    protected By addToCartBtn = By.className("wpsc_buy_button");


    public ProductCategoryPage(WebDriver driver) {
        super(driver);
    }

    public ProductCategoryPage addProductToCart(String product) {

        if (product != null) {
            List<WebElement> items = findElements(products);
            for (WebElement item : items) {
                String productName = item.findElement(productNameLink).getText();
                if (product.equalsIgnoreCase(productName)) {
                    item.findElement(addToCartBtn).click();
                }
            }
            waitForElementToAppearByAttributeValue(addToCartAlertMsg, "style", "display: block;");

            return this;
        } else {
            throw new NullValueException("The product name provided is null");
        }

    }
}
