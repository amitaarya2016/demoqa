package com.demoqa.test;

import com.demoqa.pageObject.onlineStore.StoreLandingPage;
import com.demoqa.pageObject.onlineStore.productsPage.CheckoutPage;
import com.demoqa.pageObject.onlineStore.productsPage.TransactionResults;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * @author amitaarya
 */
public class TestClass extends BaseTest {

    private static final String TRANSACTION_RESULTS = "Transaction Results";
    private static final String QUANTITY = "1";
    private static final String EMPTY_QUANTITY = "0";
    String random = randomAlphabetic(4);
    String email = "Bruce" + random + "@test1234.com";
    String firstname = "Bruce" + random;
    String lastname = "Wayne" + random;

    @DataProvider(name = "transactionData")
    public static Object[][] transactionData() {
        return new Object[][]{{"Magic Mouse"}};

    }

    @Test(description = "A Magic Mouse will solve all your problems!\n" +
            "You will be required to buy a Magic Mouse from an e-commerce website.", dataProvider = "transactionData")
    @Severity(SeverityLevel.NORMAL)
    @Description("")
    @Issue("")
    public void testScenario1(String product) {

        WebDriver driver = getDriver((new Object() {
        }.getClass().getEnclosingMethod().getName()));

//        STEP 1: Go to http://store.demoqa.com/
        driver.get(getTestUrl());
        CheckoutPage checkoutPage = new StoreLandingPage(driver)
//        STEP 2: Go to Product category and select Accessories
                .selectProductSubCategory("Accessories")
//        STEP 3: Click on “Add to Cart” for just Magic Mouse
                .addProductToCart(product)
//        STEP 4: Click on “Checkout”
                .clickCart();
//        STEP 4 contd : confirm you have 1 Magic
        Assert.assertEquals(product, checkoutPage.getProductNameAtCheckout());
        Assert.assertEquals(QUANTITY, checkoutPage.getProductQuantityAtCheckout());

//        STEP 5: After confirming, click on Continue
        TransactionResults transactionResults = checkoutPage.clickContinueCheckout()
//        STEP 6: Enter test (dummy) data needed for email, billing/contact details
                .setShippingCountryAndContinue("CA")
                .setEmailAddress(email)
                .setBillingContactDetails(firstname, lastname)
                .setShippingAddressSameAsBilling()
//        STEP 6: click Purchase
                .clickPurchase();

//        Confirm that you have placed the Order in ‘Transaction Results’ page
//        Verify the page, the item in the list and the cart is empty
        Assert.assertEquals(TRANSACTION_RESULTS, transactionResults.getPageTitle());
        Assert.assertEquals(product, transactionResults.getPurchaseFirstItemName());
        Assert.assertEquals(QUANTITY, transactionResults.getPurchaseFirstItemQuantity());
        Assert.assertEquals(EMPTY_QUANTITY, transactionResults.getCartItems());
    }
}
