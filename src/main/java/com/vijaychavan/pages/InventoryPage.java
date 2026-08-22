package com.vijaychavan.pages;

import com.vijaychavan.components.HeaderComponent;
import com.vijaychavan.components.NavigationComponent;
import com.vijaychavan.components.ProductCardComponent;
import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By sortDropdown = By.cssSelector(".product_sort_container");
    private final By productCards = By.cssSelector(".inventory_item");

    private HeaderComponent header;
    private NavigationComponent navigation;

    public InventoryPage() {
        super();
    }

    public HeaderComponent getHeader() {
        if (header == null) {
            header = new HeaderComponent(driver);
        }
        return header;
    }

    public NavigationComponent getNavigation() {
        if (navigation == null) {
            navigation = new NavigationComponent(driver);
        }
        return navigation;
    }

    public String getTitle() {
        return text(title);
    }

    public List<ProductCardComponent> getProducts() {
        List<WebElement> elements = findElements(productCards);
        List<ProductCardComponent> cards = new ArrayList<>();
        for (WebElement el : elements) {
            cards.add(new ProductCardComponent(driver, el));
        }
        return cards;
    }

    public ProductCardComponent getProductByName(String productName) {
        for (ProductCardComponent card : getProducts()) {
            if (card.getName().equalsIgnoreCase(productName)) {
                return card;
            }
        }
        throw new IllegalArgumentException("Product '" + productName + "' not found on inventory page.");
    }

    public InventoryPage addProductToCart(String productName) {
        log.info("Adding product '{}' to cart.", productName);
        String formatted = productName.toLowerCase().replace(" ", "-").replace("(", "").replace(")", "").replace(".", "");
        By btnLocator = By.cssSelector("button[id*='" + formatted + "'], button[data-test*='" + formatted + "']");
        try {
            WebElement btn = driver.findElement(btnLocator);
            btn.click();
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(btnLocator);
                JavaScriptUtil.clickWithJs(driver, btn);
            } catch (Exception ex) {
                getProductByName(productName).addToCart();
            }
        }
        return this;
    }

    public InventoryPage removeProductFromCart(String productName) {
        log.info("Removing product '{}' from cart.", productName);
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
                getProductByName(productName).removeFromCart();
            }
        }
        return this;
    }

    public ProductDetailsPage openProductDetails(String productName) {
        log.info("Opening product details for '{}'", productName);
        getProductByName(productName).clickTitle();
        try {
            WaitUtil.getWait(driver, 3).until(d -> d.getCurrentUrl().contains("inventory-item.html"));
        } catch (Exception e) {
            WebElement titleLink = driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::a"));
            JavaScriptUtil.clickWithJs(driver, titleLink);
            WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("inventory-item.html"));
        }
        return new ProductDetailsPage();
    }

    public InventoryPage selectSortOption(String visibleText) {
        log.info("Selecting sort option: {}", visibleText);
        WebElement selectEl = WaitUtil.waitForVisibility(driver, sortDropdown);
        Select select = new Select(selectEl);
        select.selectByVisibleText(visibleText);
        return this;
    }

    public CartPage openCart() {
        log.info("Navigating to Cart from Inventory Header.");
        getHeader().clickCart();
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("cart.html"));
        return new CartPage();
    }

    public LoginPage logout() {
        log.info("Logging out from application.");
        getHeader().clickMenu();
        getNavigation().clickLogout();
        WaitUtil.getWait(driver).until(d -> !d.getCurrentUrl().contains("inventory"));
        return new LoginPage();
    }

    public int getCartBadgeCount() {
        return getHeader().getCartBadgeCount();
    }

    @Override
    public boolean isAt() {
        try {
            return isDisplayed(title) && text(title).equalsIgnoreCase("Products") && !driver.getCurrentUrl().contains("inventory-item.html");
        } catch (Exception e) {
            return false;
        }
    }
}
