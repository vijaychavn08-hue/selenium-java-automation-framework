package com.vijaychavan.steps;

import com.vijaychavan.pages.CartPage;
import com.vijaychavan.pages.CheckoutCompletePage;
import com.vijaychavan.pages.CheckoutPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CheckoutSteps {
    private CartPage cartPage = new CartPage();
    private CheckoutPage checkoutPage = new CheckoutPage();
    private CheckoutCompletePage completePage = new CheckoutCompletePage();

    @When("I proceed to checkout")
    public void proceedToCheckout() {
        checkoutPage = cartPage.checkout();
        Assert.assertTrue(checkoutPage.isAt(), "Checkout step one should be displayed.");
    }

    @When("I fill checkout information with first name {string}, last name {string}, postal code {string}")
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        checkoutPage.fillInformation(firstName, lastName, postalCode);
    }

    @When("I continue to checkout overview")
    public void continueToOverview() {
        completePage = checkoutPage.clickContinue();
        Assert.assertTrue(completePage.isAt(), "Checkout overview should be displayed.");
    }

    @When("I attempt to continue with invalid information")
    public void attemptContinueInvalid() {
        checkoutPage.clickContinueInvalid();
    }

    @When("I finish the order")
    public void finishOrder() {
        completePage.finishOrder();
    }

    @Then("the order confirmation message should be {string}")
    public void verifyOrderConfirmation(String expectedMessage) {
        Assert.assertEquals(completePage.getCompleteHeader(), expectedMessage);
    }

    @Then("the checkout error message should contain {string}")
    public void verifyCheckoutErrorMessage(String expectedError) {
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(), "Checkout error message should be displayed.");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains(expectedError),
                "Error message mismatch. Expected to contain: " + expectedError + " but got: " + checkoutPage.getErrorMessage());
    }
}
