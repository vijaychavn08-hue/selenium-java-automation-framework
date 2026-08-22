package com.vijaychavan.tests;

import com.vijaychavan.framework.base.BaseTest;
import com.vijaychavan.framework.driver.DriverManager;
import com.vijaychavan.framework.factories.PageFactory;
import com.vijaychavan.framework.utils.WaitUtil;
import com.vijaychavan.pages.CartPage;
import com.vijaychavan.pages.CheckoutCompletePage;
import com.vijaychavan.pages.CheckoutPage;
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

@Epic("E-Commerce Operations")
@Feature("Cart & Checkout Workflows")
public class CartCheckoutTest extends BaseTest {

    @Test(groups = {"smoke", "regression"}, priority = 1)
    @Story("Manage cart items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify adding items, checking cart badge, and removing items.")
    public void testAddAndRemoveFromCart() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        inventoryPage.addProductToCart("Sauce Labs Backpack");
        try {
            WaitUtil.getWait(DriverManager.get(), 3).until(d -> inventoryPage.getCartBadgeCount() == 1);
        } catch (Exception ignored) {}
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 1);

        inventoryPage.addProductToCart("Sauce Labs Bike Light");
        try {
            WaitUtil.getWait(DriverManager.get(), 3).until(d -> inventoryPage.getCartBadgeCount() == 2);
        } catch (Exception ignored) {}
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), 2);

        CartPage cartPage = inventoryPage.openCart();
        Assert.assertTrue(cartPage.isAt());
        Assert.assertEquals(cartPage.getItemCount(), 2);
        Assert.assertTrue(cartPage.hasItemNamed("Sauce Labs Backpack"));

        cartPage.removeItem("Sauce Labs Backpack");
        try {
            WaitUtil.getWait(DriverManager.get(), 3).until(d -> cartPage.getItemCount() == 1);
        } catch (Exception ignored) {}
        Assert.assertEquals(cartPage.getItemCount(), 1);
    }

    @Test(groups = {"smoke", "regression"}, priority = 2)
    @Story("Complete checkout order")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify end-to-end checkout purchase completion.")
    public void testEndToEndCheckout() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        inventoryPage.addProductToCart("Sauce Labs Onesie");
        CartPage cartPage = inventoryPage.openCart();
        CheckoutPage checkoutPage = cartPage.checkout();

        checkoutPage.fillInformation("Vijay", "Chavan", "411001");
        CheckoutCompletePage completePage = checkoutPage.clickContinue();

        Assert.assertTrue(completePage.isAt());
        completePage.finishOrder();

        Assert.assertEquals(completePage.getCompleteHeader(), "Thank you for your order!");
    }

    @Test(groups = {"regression"}, priority = 3)
    @Story("Validate checkout required fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify checkout error validation when customer fields are missing.")
    public void testCheckoutValidationRequiredFields() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");

        inventoryPage.addProductToCart("Sauce Labs Backpack");
        CartPage cartPage = inventoryPage.openCart();
        CheckoutPage checkoutPage = cartPage.checkout();

        checkoutPage.fillInformation("", "Chavan", "411001");
        checkoutPage.clickContinueInvalid();
        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed());
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name is required"));
    }
}
