package com.vijaychavan.framework.utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ScreenshotUtil {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "target/screenshots/";

    private ScreenshotUtil() {}

    public static byte[] captureScreenshotBytes(WebDriver driver) {
        if (driver instanceof TakesScreenshot takesScreenshot) {
            return takesScreenshot.getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    public static File captureScreenshotFile(WebDriver driver, String screenshotName) {
        if (!(driver instanceof TakesScreenshot takesScreenshot)) {
            return null;
        }

        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String sanitizedName = screenshotName.replaceAll("[^a-zA-Z0-9_-]", "_");
        File destFile = new File(SCREENSHOT_DIR + sanitizedName + "_" + timestamp + ".png");

        try {
            FileUtils.copyFile(srcFile, destFile);
            log.info("Saved failure screenshot to: {}", destFile.getAbsolutePath());
            return destFile;
        } catch (IOException e) {
            log.warn("Failed to save screenshot to disk: {}", e.getMessage());
            return null;
        }
    }

    public static void attachScreenshotToAllure(WebDriver driver, String attachmentName) {
        try {
            byte[] bytes = captureScreenshotBytes(driver);
            if (bytes.length > 0) {
                Allure.addAttachment(attachmentName, "image/png", new ByteArrayInputStream(bytes), ".png");
                log.info("Attached screenshot '{}' to Allure report.", attachmentName);
            }
        } catch (Exception e) {
            log.warn("Failed to attach screenshot to Allure: {}", e.getMessage());
        }
    }
}
