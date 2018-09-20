package com.demoqa.pageObject.onlineStore.productsPage;

import com.demoqa.pageObject.onlineStore.BasePage;
import com.demoqa.util.TestLogger;
import com.demoqa.util.exceptions.NullValueException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * @author amitaarya
 */
public class CheckoutPage extends BasePage {

    private By firstProductQuantity = By.cssSelector(".product_row.product_row_0.alt > .wpsc_product_quantity > .adjustform > [name='quantity']");
    private By firstProductname = By.cssSelector(".product_row_0 .wpsc_product_name > a");

    //Calculate Shipping Price
    private By progressbarInfoComplete = By.cssSelector(".progress_wrapper.top .lines[style='background-position: -119px center;']");
    private By continueBtn = By.className("step2");
    private By shippingCountryForShippingCostsSelect = By.id("current_country");
    private By calculateShippingPriceBtn = By.cssSelector("[name='wpsc_submit_zipcode']");

    private By emailAddressTxt = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billingemail']");

    //Your billing/contact details
    private By firstNameTxt = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billingfirstname']");
    private By lastNameTxt = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billinglastname']");
    private By addressTxt = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billingaddress']");
    private By cityTxt = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billingcity']");
    private By stateTxt = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billingstate']");
    private By countrySelect = By.cssSelector("[title='billingcountry']");
    private By postalCode = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billingpostcode']");
    private By phoneSelect = By.cssSelector(".form-table-wrapper [data-wpsc-meta-key='billingphone']");

    //Shipping address
    private By sameAsBillingCheckbox = By.id("shippingSameBilling");
    private By purchaseBtn = By.className("make_purchase");


    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getProductNameAtCheckout() {
        return waitForElementToAppear(firstProductname).getText();
    }

    public String getProductQuantityAtCheckout() {
        return waitForElementToAppear(firstProductQuantity).getAttribute("value");
    }

    public CheckoutPage clickContinueCheckout() {
        waitForElementToAppear(continueBtn).click();
        return this;
    }

    public CheckoutPage setShippingCountryAndContinue(String country) {

        if (country != null) {
            wait.until(ExpectedConditions.presenceOfElementLocated(progressbarInfoComplete));
            Select selectCountry = new Select(findElement(shippingCountryForShippingCostsSelect));
            TestLogger.log("Number of Countries present in the Country list for Shipping country " + selectCountry.getOptions().size());

            if (selectCountry.getOptions().size() <= 1) {
                selectCountry = new Select(findElement(shippingCountryForShippingCostsSelect));
                TestLogger.log("Recalculating number of Countries present in the Country list for Shipping country " + selectCountry.getOptions().size());

            }
            selectCountry.selectByValue(country);
            waitForElementToAppear(calculateShippingPriceBtn).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(progressbarInfoComplete));
            return this;
        } else {
            throw new NullValueException("The product name provided is null");
        }
    }

    public CheckoutPage setEmailAddress(String emailAddress) {
        if (emailAddress != null) {
            waitForElementToAppear(emailAddressTxt).sendKeys(emailAddress);
            return this;
        } else {
            throw new NullValueException("The first name or last name provided is null");
        }

    }

    public CheckoutPage setBillingContactDetails(String firstname, String lastname) {

        if (firstname != null && lastname != null) {
            findElement(firstNameTxt).sendKeys(firstname);
            findElement(lastNameTxt).sendKeys(lastname);
            findElement(addressTxt).sendKeys("0001, 100 king blvd");
            findElement(cityTxt).sendKeys("London");
            findElement(stateTxt).sendKeys("Ontario");
            Select selectCountryForBilling = new Select(findElement(countrySelect));
            System.out.println(selectCountryForBilling.getOptions().size());

            if (selectCountryForBilling.getOptions().size() <= 1) {
                selectCountryForBilling = new Select(waitForElementToAppear(countrySelect));
            }
            selectCountryForBilling.selectByValue("CA");

            waitForElementToAppear(postalCode).sendKeys("K3GL1K");
            findElement(phoneSelect).sendKeys("64750012118");
            return this;
        } else {
            throw new NullValueException("The first name or last name provided is null");
        }
    }

    public CheckoutPage setShippingAddressSameAsBilling() {
        findElement(sameAsBillingCheckbox).click();
        return this;
    }

    public TransactionResults clickPurchase() {
        findElement(purchaseBtn).click();

        return new TransactionResults(getDriver());
    }
}
