package com.demoqa.test;

import com.demoqa.util.RetryAnalyzer;
import com.demoqa.util.TestLogger;
import com.demoqa.util.listeners.TestNgListenerAdapter;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * This class will set all the test parameter
 * e.g. browser (timeouts, listeners)
 * OS
 * base test url
 * retry parameter
 *
 * @author amitaarya
 */
@Listeners(TestNgListenerAdapter.class)
public class BaseTest {

    public static final int RETRY_FAILED_TEST = 0;
    public static final String SCREENSHOT_DEFAULT_FOLDER = "target/screenshot/";
    public static final int DEFAULT_WAIT = 20;
    public static HashMap<String, WebDriver> webDriverHashMap = new HashMap<>();

    //set the time out for fluent waits
    static {
        System.setProperty("wait_timeout", Integer.toString(DEFAULT_WAIT));
//        System.setProperty("webdriver.chrome.driver", "pathofchromedriver\\chromedriver.exe");
    }

    protected int DEFAULT_TIMEOUT = 20;
    private String testUrl = "http://store.demoqa.com/%20";

    public static void setWebDriverForTest(String testname, WebDriver driver) {
        webDriverHashMap.put(testname, driver);
    }

    public static WebDriver getWebDriverForTest(String testname) {
        return webDriverHashMap.get(testname);

    }

    /**
     * Close Browser window for a test
     *
     * @param testname
     */
    public static void quitWebDriverForTest(String testname) {

        WebDriver driver = getWebDriverForTest(testname);
        if (driver != null) {
            driver.quit();
            TestLogger.log("Quit browser for : " + testname);
            webDriverHashMap.remove(testname);
        }
    }

    /**
     * Is Driver present in the hashmap (tests,browsers)
     *
     * @param testName
     * @return boolean - isbrowser window open
     */
    public static boolean isWebDriverForTestInMap(String testName) {
        if (webDriverHashMap.containsKey(testName)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Take screenshot fo the browser window
     *
     * @param testname
     */
    public static void takeScreenshot(String testname) {
        try {
            final String newFileNamePath = SCREENSHOT_DEFAULT_FOLDER + testname + ".png";
            TestLogger.log("Saving the screenshot for '" + testname + "' at  " + newFileNamePath);

            final String base64 = ((TakesScreenshot) getWebDriverForTest(testname)).getScreenshotAs(OutputType.BASE64);

            final File scrFile = OutputType.FILE.convertFromBase64Png(base64);
            FileUtils.copyFile(scrFile, new File(newFileNamePath));

            Allure.addAttachment("Screenshot", "image/png", Files.newInputStream(Paths.get(newFileNamePath)), "png");
        } catch (Exception e) {
            TestLogger.log("Could not take screenshot for the test browser '" + testname + "' due to: " + e);
        }
    }

    /**
     * Run before each test suite for setting the run parameters
     *
     * @param context
     */
    @BeforeSuite
    public void setupTestRun(ITestContext context) {

        for (ITestNGMethod method : context.getAllTestMethods()) {
            method.setRetryAnalyzer(new RetryAnalyzer());
        }

    }

    /**
     * Get default hub url
     */
    public String getTestUrl() {
        return testUrl;
    }

    /**
     * get driver for a test
     * @param testname
     * @return
     */
    protected WebDriver getDriver(String testname) {

        WebDriver driver;
        driver = new ChromeDriver();

        setWebDriverForTest(testname, driver);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1400, 1400));
        return driver;
    }

    /**
     * clean up of the browser after a test
     */

    @AfterMethod(alwaysRun = true)
    public void teardownTest(Method method) {
        if (isWebDriverForTestInMap(method.getName())) {
            quitWebDriverForTest(method.getName());
            TestLogger.log("The test execution is complete. Browser closed for - " + method.getName());
        } else {
            TestLogger.log("The test execution is complete. No browser found for - " + method.getName());
        }
    }
}
