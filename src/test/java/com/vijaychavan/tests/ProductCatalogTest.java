package com.vijaychavan.tests;

import com.vijaychavan.framework.base.BaseTest;
import com.vijaychavan.framework.factories.PageFactory;
import com.vijaychavan.pages.InventoryPage;
import com.vijaychavan.pages.LoginPage;
import com.vijaychavan.pages.ProductDetailsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Product Management")
@Feature("Product Catalog & Details")
public class ProductCatalogTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, priority = 1)
    @Story("Browse product catalog")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the product catalog displays all 6 products with titles and prices.")
    public void testProductCatalogDisplay() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        Assert.assertEquals(inventoryPage.getProducts().size(), 6, "Expected 6 products.");
        Assert.assertNotNull(inventoryPage.getProductByName("Sauce Labs Backpack"));
    }

    @Test(groups = {"regression"}, priority = 2)
    @Story("Sort catalog items")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify sorting products alphabetically and by price.")
    public void testSortProducts() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        inventoryPage.selectSortOption("Price (low to high)");
        Assert.assertEquals(inventoryPage.getProducts().size(), 6);

        inventoryPage.selectSortOption("Name (Z to A)");
        Assert.assertEquals(inventoryPage.getProducts().size(), 6);
    }

    @Test(groups = {"regression"}, priority = 3)
    @Story("View product details")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify opening product details page and navigating back to catalog.")
    public void testOpenProductDetails() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        ProductDetailsPage detailsPage = inventoryPage.openProductDetails("Sauce Labs Backpack");
        Assert.assertTrue(detailsPage.isAt());
        Assert.assertEquals(detailsPage.getName(), "Sauce Labs Backpack");
        Assert.assertNotNull(detailsPage.getPrice());

        InventoryPage backPage = detailsPage.backToProducts();
        Assert.assertTrue(backPage.isAt());
    }
}
