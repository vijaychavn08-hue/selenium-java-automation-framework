package com.vijaychavan.framework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);
    private int count = 0;
    private static final int MAX_RETRY_COUNT = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (count < MAX_RETRY_COUNT) {
            count++;
            log.warn("Retrying test '{}' with status {} (Attempt {}/{})",
                    result.getName(), result.getStatus(), count, MAX_RETRY_COUNT);
            return true;
        }
        return false;
    }
}
