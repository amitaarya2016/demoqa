package com.demoqa.util.listeners;

import com.demoqa.test.BaseTest;
import com.demoqa.util.TestLogger;
import io.qameta.allure.Allure;
import org.testng.*;

/**
 * Testng listener implementation to generate reports
 * @author amitaarya
 */
public class TestNgListenerAdapter implements IInvokedMethodListener, ITestListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

        if (!iTestResult.isSuccess() && iInvokedMethod.isConfigurationMethod()) {
            BaseTest.takeScreenshot(iInvokedMethod.getTestMethod().getMethodName());
        }
        TestLogger.log("/nThe configuration method " + iTestResult.getName() + " has the following result status " + iTestResult.getStatus() + "/n");
        Allure.addAttachment("Scenario Logs", TestLogger.getAllLogsAsString());

    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        TestLogger.log("/nThe Test- " + iTestResult.getName() + " has the following result status " + iTestResult.getStatus() + "/n");
        Allure.addAttachment("Scenario Logs", TestLogger.getAllLogsAsString());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {

        TestLogger.log("/nThe Test- " + iTestResult.getName() + " has the following result status " + iTestResult.getStatus() + "/n");
        BaseTest.takeScreenshot(iTestResult.getName());
        Allure.addAttachment("Scenario Logs", TestLogger.getAllLogsAsString());
        TestLogger.clearLogs();

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        TestLogger.log("/nThe Test- " + iTestResult.getName() + " has the following result status " + iTestResult.getStatus() + "/n");
        Allure.addAttachment("Scenario Logs", TestLogger.getAllLogsAsString());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}