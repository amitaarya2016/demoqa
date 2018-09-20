package com.demoqa.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import static com.demoqa.test.BaseTest.RETRY_FAILED_TEST;

/**
 * @author amitaarya
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int maxRetryCount = RETRY_FAILED_TEST;

    /**
     * The internal retry count
     */
    private int retryCount = 0;

    /**
     * Returns true if the test method has to be retried, false otherwise.
     *
     * @param result The result of the test method that just ran.
     * @return true if the test method has to be retried, false otherwise.
     */
    @Override
    public boolean retry(ITestResult result) {

        if (retryCount < maxRetryCount) {
            System.out.println("\nChecking for rerun of the test: '" + result.getName() + "' of class "
                    + result.getMethod().getMethodName() + "' for the retry - " + retryCount);
            retryCount++;
            return true;
        }
        return false;
    }
}
