package com.demoqa.pageObject.onlineStore.productsPage;

import com.demoqa.pageObject.onlineStore.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * @author amitaarya
 */
public class TransactionResults extends BasePage {

    private By firstItemName = By.cssSelector(".wpsc-purchase-log-transaction-results > tbody > tr > td");
    private By firstItemQty = By.cssSelector(".wpsc-purchase-log-transaction-results > tbody > tr > td:nth-child(3)");


    public TransactionResults(WebDriver driver) {
        super(driver);

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("entry-title")));
    }


    public String getPurchaseFirstItemName() {
        return findElement(firstItemName).getText();
    }

    public String getPurchaseFirstItemQuantity() {
        return findElement(firstItemQty).getText();
    }
}
