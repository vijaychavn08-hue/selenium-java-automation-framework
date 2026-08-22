package com.vijaychavan.steps;

import com.vijaychavan.framework.driver.DriverManager;
import com.vijaychavan.framework.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger log = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        log.info("Starting Cucumber Scenario: '{}'", scenario.getName());
        DriverManager.start();
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.get();
        if (scenario.isFailed()) {
            log.warn("Scenario '{}' FAILED. Capturing failure screenshot...", scenario.getName());
            ScreenshotUtil.captureScreenshotFile(driver, scenario.getName());
            ScreenshotUtil.attachScreenshotToAllure(driver, "Failure Screenshot - " + scenario.getName());
        }
        DriverManager.stop();
        log.info("Finished Cucumber Scenario: '{}' (Status: {})", scenario.getName(), scenario.getStatus());
    }
}
