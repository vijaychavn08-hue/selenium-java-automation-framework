package com.vijaychavan.pages;

import com.vijaychavan.components.HeaderComponent;
import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.config.Config;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;

public class ProductDetailsPage extends BasePage {
    private final By name = By.cssSelector(".inventory_details_name, [data-test='inventory-item-name']");
    private final By desc = By.cssSelector(".inventory_details_desc, [data-test='inventory-item-desc']");
    private final By price = By.cssSelector(".inventory_details_price, [data-test='inventory-item-price']");
    private final By addToCartButton = By.cssSelector("button[id^='add-to-cart'], button[data-test^='add-to-cart'], button.btn_primary");
    private final By removeButton = By.cssSelector("button[id^='remove'], button[data-test^='remove'], button.btn_secondary");
    private final By backToProductsButton = By.cssSelector("#back-to-products, button[data-test='back-to-products']");

    private HeaderComponent header;

    public ProductDetailsPage() {
        super();
    }

    public HeaderComponent getHeader() {
        if (header == null) {
            header = new HeaderComponent(driver);
        }
        return header;
    }

    public String getName() {
        return text(name);
    }

    public String getDescription() {
        return text(desc);
    }

    public String getPrice() {
        return text(price);
    }

    public ProductDetailsPage addToCart() {
        click(addToCartButton);
        return this;
    }

    public ProductDetailsPage removeFromCart() {
        click(removeButton);
        return this;
    }

    public InventoryPage backToProducts() {
        click(backToProductsButton);
        try {
            WaitUtil.getWait(driver, 3).until(d -> d.getCurrentUrl().endsWith("/inventory.html") || (d.getCurrentUrl().contains("inventory.html") && !d.getCurrentUrl().contains("inventory-item.html")));
        } catch (Exception e) {
            openUrl(Config.baseUrl() + "inventory.html");
            WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("inventory.html") && !d.getCurrentUrl().contains("inventory-item.html"));
        }
        return new InventoryPage();
    }

    public CartPage openCart() {
        getHeader().clickCart();
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("cart.html"));
        return new CartPage();
    }

    @Override
    public boolean isAt() {
        try {
            return isDisplayed(backToProductsButton) && driver.getCurrentUrl().contains("inventory-item.html");
        } catch (Exception e) {
            return false;
        }
    }
}
