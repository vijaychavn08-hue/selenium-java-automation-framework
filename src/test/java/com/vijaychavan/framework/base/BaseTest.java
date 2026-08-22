package com.vijaychavan.framework.base;

import com.vijaychavan.framework.driver.DriverManager;
import com.vijaychavan.framework.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("Setting up WebDriver for test method.");
        DriverManager.start();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        WebDriver driver = DriverManager.get();
        if (result.getStatus() == ITestResult.FAILURE) {
            log.warn("Test '{}' failed. Capturing failure screenshot...", result.getName());
            ScreenshotUtil.captureScreenshotFile(driver, result.getName());
            ScreenshotUtil.attachScreenshotToAllure(driver, "Failure Screenshot - " + result.getName());
        }
        DriverManager.stop();
    }
}
