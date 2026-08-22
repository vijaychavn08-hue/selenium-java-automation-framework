package com.vijaychavan.pages;

import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.config.Config;
import com.vijaychavan.framework.utils.LocatorRepository;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    private final By usernameInput;
    private final By passwordInput;
    private final By loginButton;
    private final By errorMessage;

    public LoginPage() {
        super();
        this.usernameInput = LocatorRepository.getBy("login", "usernameInput");
        this.passwordInput = LocatorRepository.getBy("login", "passwordInput");
        this.loginButton = LocatorRepository.getBy("login", "loginButton");
        this.errorMessage = LocatorRepository.getBy("login", "errorMessage");
    }

    public LoginPage open() {
        openUrl(Config.baseUrl());
        return this;
    }

    public InventoryPage loginAs(String username, String password) {
        log.info("Logging in with username: {}", username);
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("inventory.html"));
        return new InventoryPage();
    }

    public LoginPage loginInvalid(String username, String password) {
        log.info("Attempting login with invalid credentials: user='{}'", username);
        if (username != null && !username.isEmpty()) {
            type(usernameInput, username);
        }
        if (password != null && !password.isEmpty()) {
            type(passwordInput, password);
        }
        click(loginButton);
        try {
            WaitUtil.getWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        } catch (Exception ignored) {}
        return this;
    }

    public String getErrorMessage() {
        return text(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return WaitUtil.getWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isAt() {
        return isDisplayed(loginButton);
    }
}
