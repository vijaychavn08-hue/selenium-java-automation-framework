package com.vijaychavan.components;

import com.vijaychavan.framework.base.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationComponent extends BaseComponent {
    private static final By ROOT_LOCATOR = By.cssSelector(".bm-menu-wrap");
    private final By allItemsLink = By.id("inventory_sidebar_link");
    private final By aboutLink = By.id("about_sidebar_link");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By resetLink = By.id("reset_sidebar_link");
    private final By closeButton = By.id("react-burger-cross-btn");

    public NavigationComponent(WebDriver driver) {
        super(driver, ROOT_LOCATOR);
    }

    public void clickAllItems() {
        click(allItemsLink);
    }

    public void clickLogout() {
        click(logoutLink);
    }

    public void clickResetAppState() {
        click(resetLink);
    }

    public void closeMenu() {
        click(closeButton);
    }
}
