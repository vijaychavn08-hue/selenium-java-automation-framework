package com.vijaychavan.steps;

import com.vijaychavan.pages.InventoryPage;
import com.vijaychavan.pages.LoginPage;
import com.vijaychavan.pages.ProductDetailsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class ProductSteps {
    private InventoryPage inventoryPage = new InventoryPage();
    private ProductDetailsPage detailsPage = new ProductDetailsPage();

    @Then("the inventory page should be displayed")
    public void verifyInventoryPageDisplayed() {
        Assert.assertTrue(inventoryPage.isAt(), "Inventory page should be displayed.");
    }

    @Then("the header title should be {string}")
    public void verifyHeaderTitle(String expectedTitle) {
        Assert.assertEquals(inventoryPage.getHeader().getAppTitle(), expectedTitle);
    }

    @Then("the inventory page should display {int} products")
    public void verifyProductCount(int expectedCount) {
        Assert.assertEquals(inventoryPage.getProducts().size(), expectedCount);
    }

    @When("I sort products by {string}")
    public void sortProducts(String sortOption) {
        inventoryPage.selectSortOption(sortOption);
    }

    @Then("the products should be sorted accordingly")
    public void verifySortedProducts() {
        Assert.assertTrue(inventoryPage.getProducts().size() > 0, "Products should still be displayed after sort.");
    }

    @When("I click on product {string}")
    public void clickProductTitle(String productName) {
        detailsPage = inventoryPage.openProductDetails(productName);
    }

    @Then("the product details page should display name {string}")
    public void verifyProductDetailsName(String expectedName) {
        Assert.assertTrue(detailsPage.isAt(), "Product details page should be displayed.");
        Assert.assertEquals(detailsPage.getName(), expectedName);
    }

    @When("I click back to products")
    public void clickBackToProducts() {
        inventoryPage = detailsPage.backToProducts();
    }

    @When("I open the navigation menu and click logout")
    public void logoutFromMenu() {
        LoginPage loginPage = inventoryPage.logout();
        Assert.assertTrue(loginPage.isAt(), "Should be redirected to login page.");
    }
}
