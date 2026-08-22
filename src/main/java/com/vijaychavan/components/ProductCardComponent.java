package com.vijaychavan.components;

import com.vijaychavan.framework.base.BaseComponent;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ProductCardComponent extends BaseComponent {
    private final By nameLocator = By.cssSelector(".inventory_item_name, [data-test='inventory-item-name']");
    private final By descLocator = By.cssSelector(".inventory_item_desc, [data-test='inventory-item-desc']");
    private final By priceLocator = By.cssSelector(".inventory_item_price, [data-test='inventory-item-price']");

    public ProductCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getName() {
        return text(nameLocator);
    }

    public String getDescription() {
        return text(descLocator);
    }

    public String getPrice() {
        return text(priceLocator);
    }

    public void clickTitle() {
        WebElement nameEl = find(nameLocator);
        try {
            WebElement parentLink = nameEl.findElement(By.xpath("./ancestor::a[1]"));
            new Actions(driver).moveToElement(parentLink).click().perform();
        } catch (Exception e) {
            try {
                nameEl.click();
            } catch (Exception ex) {
                JavaScriptUtil.clickWithJs(driver, nameEl);
            }
        }
    }

    public void addToCart() {
        WebElement btn = find(By.cssSelector("button.btn_inventory, button[id*='add-to-cart'], button[data-test*='add-to-cart']"));
        try {
            btn.click();
        } catch (Exception e) {
            JavaScriptUtil.clickWithJs(driver, btn);
        }
        try {
            WaitUtil.getWait(driver, 3).until(d -> isRemoveDisplayed());
        } catch (Exception ignored) {}
    }

    public void removeFromCart() {
        WebElement btn = find(By.cssSelector("button.btn_inventory, button[id*='remove'], button[data-test*='remove']"));
        try {
            btn.click();
        } catch (Exception e) {
            JavaScriptUtil.clickWithJs(driver, btn);
        }
        try {
            WaitUtil.getWait(driver, 3).until(d -> isAddToCartDisplayed());
        } catch (Exception ignored) {}
    }

    public boolean isAddToCartDisplayed() {
        try {
            return find(By.cssSelector("button[id*='add-to-cart'], button[data-test*='add-to-cart']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRemoveDisplayed() {
        try {
            return find(By.cssSelector("button[id*='remove'], button[data-test*='remove']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
