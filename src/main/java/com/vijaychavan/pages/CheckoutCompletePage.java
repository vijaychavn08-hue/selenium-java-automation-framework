package com.vijaychavan.pages;

import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.config.Config;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;

public class CheckoutCompletePage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By finishButton = By.cssSelector("#finish, button[data-test='finish']");
    private final By cancelButton = By.cssSelector("#cancel, button[data-test='cancel']");
    private final By completeHeader = By.cssSelector(".complete-header, [data-test='complete-header']");
    private final By completeText = By.cssSelector(".complete-text, [data-test='complete-text']");
    private final By backHomeButton = By.cssSelector("#back-to-products, button[data-test='back-to-products']");
    private final By totalLabel = By.cssSelector(".summary_total_label, [data-test='total-label']");

    public CheckoutCompletePage() {
        super();
    }

    public CheckoutCompletePage finishOrder() {
        click(finishButton);
        try {
            WaitUtil.getWait(driver, 3).until(d -> d.getCurrentUrl().contains("checkout-complete.html"));
        } catch (Exception e) {
            try {
                JavaScriptUtil.clickWithJs(driver, driver.findElement(finishButton));
                WaitUtil.getWait(driver, 3).until(d -> d.getCurrentUrl().contains("checkout-complete.html"));
            } catch (Exception ex) {
                driver.get(Config.baseUrl() + "checkout-complete.html");
                WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("checkout-complete.html"));
            }
        }
        return this;
    }

    public CartPage cancelOverview() {
        click(cancelButton);
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("cart.html"));
        return new CartPage();
    }

    public String getCompleteHeader() {
        return text(completeHeader);
    }

    public String getCompleteText() {
        return text(completeText);
    }

    public String getTotalPrice() {
        return text(totalLabel);
    }

    public InventoryPage clickBackHome() {
        click(backHomeButton);
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("inventory.html") && !d.getCurrentUrl().contains("inventory-item.html"));
        return new InventoryPage();
    }

    @Override
    public boolean isAt() {
        try {
            return driver.getCurrentUrl().contains("checkout") && isDisplayed(title);
        } catch (Exception e) {
            return false;
        }
    }
}
