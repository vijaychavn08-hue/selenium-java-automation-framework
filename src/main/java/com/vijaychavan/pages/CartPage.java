package com.vijaychavan.pages;

import com.vijaychavan.components.CartItemComponent;
import com.vijaychavan.components.HeaderComponent;
import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By cartItemsLocator = By.cssSelector(".cart_item");
    private final By continueShoppingButton = By.cssSelector("#continue-shopping, button[data-test='continue-shopping']");
    private final By checkoutButton = By.cssSelector("#checkout, button[data-test='checkout'], button[name='checkout']");

    private HeaderComponent header;

    public CartPage() {
        super();
    }

    public HeaderComponent getHeader() {
        if (header == null) {
            header = new HeaderComponent(driver);
        }
        return header;
    }

    public List<CartItemComponent> getCartItems() {
        List<WebElement> elements = driver.findElements(cartItemsLocator);
        List<CartItemComponent> items = new ArrayList<>();
        for (WebElement el : elements) {
            items.add(new CartItemComponent(driver, el));
        }
        return items;
    }

    public int getItemCount() {
        try {
            return driver.findElements(cartItemsLocator).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean hasItemNamed(String productName) {
        for (CartItemComponent item : getCartItems()) {
            if (item.getName().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }

    public CartPage removeItem(String productName) {
        log.info("Removing item '{}' from cart.", productName);
        String formatted = productName.toLowerCase().replace(" ", "-").replace("(", "").replace(")", "").replace(".", "");
        By btnLocator = By.cssSelector("button[id*='remove-" + formatted + "'], button[data-test*='remove-" + formatted + "'], button[id^='remove']");
        try {
            WebElement btn = driver.findElement(btnLocator);
            btn.click();
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(btnLocator);
                JavaScriptUtil.clickWithJs(driver, btn);
            } catch (Exception ex) {
                for (CartItemComponent item : getCartItems()) {
                    if (item.getName().equalsIgnoreCase(productName)) {
                        item.remove();
                        break;
                    }
                }
            }
        }
        return this;
    }

    public InventoryPage continueShopping() {
        try {
            WebElement btn = driver.findElement(continueShoppingButton);
            btn.click();
        } catch (Exception e) {
            WebElement btn = driver.findElement(continueShoppingButton);
            JavaScriptUtil.clickWithJs(driver, btn);
        }
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("inventory.html") && !d.getCurrentUrl().contains("inventory-item.html"));
        return new InventoryPage();
    }

    public CheckoutPage checkout() {
        try {
            WebElement btn = WaitUtil.waitForClickable(driver, checkoutButton);
            btn.click();
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(checkoutButton);
                JavaScriptUtil.clickWithJs(driver, btn);
            } catch (Exception ex) {
                JavaScriptUtil.executeScript(driver, "var b = document.querySelector('#checkout, button[data-test=\"checkout\"]'); if(b) b.click();");
            }
        }
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("checkout-step-one.html"));
        return new CheckoutPage();
    }

    @Override
    public boolean isAt() {
        try {
            return driver.getCurrentUrl().contains("cart.html") && isDisplayed(title) && text(title).equalsIgnoreCase("Your Cart");
        } catch (Exception e) {
            return false;
        }
    }
}
