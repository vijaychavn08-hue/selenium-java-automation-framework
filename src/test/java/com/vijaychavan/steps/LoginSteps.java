package com.vijaychavan.steps;

import com.vijaychavan.pages.InventoryPage;
import com.vijaychavan.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {
    private LoginPage loginPage = new LoginPage();
    private InventoryPage inventoryPage;

    @Given("I open the SauceDemo login page")
    public void openLoginPage() {
        loginPage.open();
        Assert.assertTrue(loginPage.isAt(), "Login page should be displayed.");
    }

    @When("I login with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        inventoryPage = loginPage.loginAs(username, password);
    }

    @When("I attempt to login with username {string} and password {string}")
    public void attemptLogin(String username, String password) {
        loginPage.loginInvalid(username, password);
    }

    @Given("I am logged in to SauceDemo as {string}")
    public void loggedInAs(String username) {
        loginPage.open();
        inventoryPage = loginPage.loginAs(username, "secret_sauce");
        Assert.assertTrue(inventoryPage.isAt(), "User should be logged in to inventory page.");
    }

    @Then("the login error message should contain {string}")
    public void verifyLoginErrorMessage(String expectedError) {
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed.");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedError),
                "Error message mismatch. Expected to contain: " + expectedError + " but got: " + loginPage.getErrorMessage());
    }

    @Then("the login page should be displayed")
    public void verifyLoginPageDisplayed() {
        Assert.assertTrue(loginPage.isAt(), "Login page should be displayed.");
    }
}
