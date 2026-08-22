package com.vijaychavan.tests;

import com.vijaychavan.framework.base.BaseTest;
import com.vijaychavan.framework.factories.PageFactory;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
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

import java.util.List;
import java.util.Map;

@Epic("Advanced Architecture")
@Feature("JavaScript Element Discovery")
public class JavaScriptDiscoveryTest extends BaseTest {

    @Test(groups = {"regression"}, priority = 1)
    @Story("Dynamic DOM elements discovery via JS")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify extracting DOM health and elements overview using external JavaScript file.")
    public void testJavaScriptDynamicElementsDiscovery() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isAt());

        Object result = JavaScriptUtil.executeJsFile(inventoryPage.getDriver(), "js/dynamicElements.js");
        Assert.assertNotNull(result, "JavaScript discovery result should not be null.");

        if (result instanceof Map<?, ?> map) {
            Assert.assertEquals(map.get("appTitle"), "Swag Labs");
            Assert.assertEquals(map.get("cartAvailable"), true);
            Number itemCount = (Number) map.get("itemCount");
            Assert.assertEquals(itemCount.intValue(), 6, "Expected 6 dynamic items discovered.");
        }
    }

    @Test(groups = {"regression"}, priority = 2)
    @Story("Catalog product discovery via JS")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify extracting catalog product titles and prices from DOM using external JavaScript file.")
    public void testJavaScriptProductDiscovery() {
        LoginPage loginPage = PageFactory.getPage(LoginPage.class).open();
        InventoryPage inventoryPage = loginPage.loginAs("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isAt());

        Object result = JavaScriptUtil.executeJsFile(inventoryPage.getDriver(), "js/productDiscovery.js");
        Assert.assertNotNull(result, "JavaScript product discovery result should not be null.");

        if (result instanceof List<?> list) {
            Assert.assertEquals(list.size(), 6, "Expected 6 products discovered via JS.");
            boolean foundBackpack = false;
            for (Object obj : list) {
                if (obj instanceof Map<?, ?> item) {
                    if ("Sauce Labs Backpack".equals(item.get("name"))) {
                        foundBackpack = true;
                        Assert.assertNotNull(item.get("price"));
                    }
                }
            }
            Assert.assertTrue(foundBackpack, "Sauce Labs Backpack should be in JS-discovered catalog.");
        }
    }
}
