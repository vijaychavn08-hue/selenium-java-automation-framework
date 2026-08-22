package com.vijaychavan.tests;

import com.vijaychavan.framework.base.BaseTest;
import com.vijaychavan.framework.factories.PageFactory;
import com.vijaychavan.pages.InventoryPage;
import com.vijaychavan.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("User Login")
public class LoginTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, priority = 1)
    @Story("Standard user login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that standard_user logs in successfully and lands on Inventory page.")
    public void testValidLogin() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isAt(), "Inventory page should be displayed.");
        Assert.assertEquals(inventoryPage.getHeader().getAppTitle(), "Swag Labs");
        Assert.assertEquals(inventoryPage.getProducts().size(), 6, "Inventory should show 6 items.");
    }

    @Test(groups = {"smoke", "regression"}, priority = 2)
    @Story("Locked out user rejection")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify locked_out_user receives a clear locked out error message.")
    public void testLockedOutUser() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        loginPage.loginInvalid("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should appear.");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out."));
    }

    @Test(groups = {"regression"}, priority = 3)
    @Story("Invalid user credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify invalid credentials trigger username/password mismatch error.")
    public void testInvalidCredentials() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        loginPage.loginInvalid("invalid_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
    }

    @Test(groups = {"regression"}, priority = 4)
    @Story("Empty username validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify submitting empty username triggers 'Username is required' error.")
    public void testEmptyUsername() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        loginPage.loginInvalid("", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test(groups = {"regression"}, priority = 5)
    @Story("Empty password validation")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify submitting empty password triggers 'Password is required' error.")
    public void testEmptyPassword() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        loginPage.loginInvalid("standard_user", "");

        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }
}
