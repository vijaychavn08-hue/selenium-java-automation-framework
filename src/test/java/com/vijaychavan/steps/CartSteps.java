package com.vijaychavan.steps;

import com.vijaychavan.framework.driver.DriverManager;
import com.vijaychavan.framework.utils.WaitUtil;
import com.vijaychavan.pages.CartPage;
import com.vijaychavan.pages.InventoryPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CartSteps {
    private InventoryPage inventoryPage = new InventoryPage();
    private CartPage cartPage = new CartPage();

    @When("I add product {string} to the cart")
    public void addProductToCart(String productName) {
        inventoryPage.addProductToCart(productName);
    }

    @When("I remove product {string} from the cart")
    public void removeProductFromCart(String productName) {
        inventoryPage.removeProductFromCart(productName);
    }

    @Then("the shopping cart badge count should be {int}")
    public void verifyCartBadgeCount(int expectedCount) {
        try {
            WaitUtil.getWait(DriverManager.get(), 3).until(d -> inventoryPage.getCartBadgeCount() == expectedCount);
        } catch (Exception ignored) {}
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), expectedCount);
    }

    @Then("the shopping cart badge should not be displayed")
    public void verifyCartBadgeNotDisplayed() {
        try {
            WaitUtil.getWait(DriverManager.get(), 3).until(d -> inventoryPage.getCartBadgeCount() == 0);
        } catch (Exception ignored) {}
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 0);
    }

    @When("I open the shopping cart")
    public void openCart() {
        cartPage = inventoryPage.openCart();
        Assert.assertTrue(cartPage.isAt(), "Cart page should be displayed.");
    }

    @Then("the cart page should display item {string}")
    public void verifyCartItem(String productName) {
        Assert.assertTrue(cartPage.hasItemNamed(productName), "Expected item '" + productName + "' to be in cart.");
    }

    @When("I remove item {string} from the cart page")
    public void removeItemFromCartPage(String productName) {
        cartPage.removeItem(productName);
    }

    @Then("the cart page should contain {int} items")
    public void verifyCartItemCount(int expectedCount) {
        try {
            WaitUtil.getWait(DriverManager.get(), 3).until(d -> cartPage.getItemCount() == expectedCount);
        } catch (Exception ignored) {}
        Assert.assertEquals(cartPage.getItemCount(), expectedCount);
    }
}
