package com.demoqa.pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author amitaarya
 */
public interface PageObject {

    WebDriverWait getWait();

    WebDriver getDriver();

}
