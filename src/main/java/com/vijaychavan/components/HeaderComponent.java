package com.vijaychavan.components;

import com.vijaychavan.framework.base.BaseComponent;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class HeaderComponent extends BaseComponent {
    private static final By ROOT_LOCATOR = By.cssSelector(".primary_header");
    private final By title = By.cssSelector(".app_logo");
    private final By cartLink = By.cssSelector("a.shopping_cart_link, .shopping_cart_link, [data-test='shopping-cart-link']");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge, [data-test='shopping-cart-badge']");
    private final By menuButton = By.cssSelector("#react-burger-menu-btn, button[id='react-burger-menu-btn']");

    public HeaderComponent(WebDriver driver) {
        super(driver, ROOT_LOCATOR);
    }

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getAppTitle() {
        return text(title);
    }

    public int getCartBadgeCount() {
        try {
            List<WebElement> badges = driver.findElements(cartBadge);
            if (badges.isEmpty()) {
                return 0;
            }
            String txt = badges.get(0).getText().trim();
            if (txt.isEmpty()) {
                txt = badges.get(0).getAttribute("innerText").trim();
            }
            if (txt.isEmpty()) {
                txt = badges.get(0).getAttribute("textContent").trim();
            }
            if (txt.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(txt);
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickCart() {
        try {
            WebElement link = driver.findElement(cartLink);
            JavaScriptUtil.scrollToElement(driver, link);
            try {
                new Actions(driver).moveToElement(link).click().perform();
            } catch (Exception e) {
                link.click();
            }
        } catch (Exception ex) {
            JavaScriptUtil.clickWithJs(driver, driver.findElement(cartLink));
        }
    }

    public void clickMenu() {
        try {
            WebElement menu = driver.findElement(menuButton);
            try {
                menu.click();
            } catch (Exception e) {
                JavaScriptUtil.clickWithJs(driver, menu);
            }
        } catch (Exception ex) {
            JavaScriptUtil.executeScript(driver, "var m = document.querySelector('#react-burger-menu-btn'); if(m) m.click();");
        }
    }
}
