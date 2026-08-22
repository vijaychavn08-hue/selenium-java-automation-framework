package com.vijaychavan.components;

import com.vijaychavan.framework.base.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartItemComponent extends BaseComponent {
    private final By nameLocator = By.cssSelector(".inventory_item_name, [data-test='inventory-item-name']");
    private final By priceLocator = By.cssSelector(".inventory_item_price, [data-test='inventory-item-price']");
    private final By qtyLocator = By.cssSelector(".cart_quantity, [data-test='item-quantity']");
    private final By removeButton = By.cssSelector("button[id^='remove'], button[data-test^='remove'], button.btn_secondary");

    public CartItemComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getName() {
        return text(nameLocator);
    }

    public String getPrice() {
        return text(priceLocator);
    }

    public int getQuantity() {
        try {
            return Integer.parseInt(text(qtyLocator));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public void remove() {
        click(removeButton);
    }
}
