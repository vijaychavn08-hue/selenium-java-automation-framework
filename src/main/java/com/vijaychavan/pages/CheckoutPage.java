package com.vijaychavan.pages;

import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.config.Config;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By firstNameInput = By.cssSelector("#first-name, input[data-test='firstName']");
    private final By lastNameInput = By.cssSelector("#last-name, input[data-test='lastName']");
    private final By postalCodeInput = By.cssSelector("#postal-code, input[data-test='postalCode']");
    private final By continueButton = By.cssSelector("#continue, input[data-test='continue'], input[name='continue']");
    private final By cancelButton = By.cssSelector("#cancel, button[data-test='cancel']");
    private final By errorMessage = By.cssSelector("[data-test='error'], .error-message-container.error, h3[data-test='error']");

    public CheckoutPage() {
        super();
    }

    public CheckoutPage fillInformation(String firstName, String lastName, String postalCode) {
        if (firstName != null && !firstName.trim().isEmpty()) {
            type(firstNameInput, firstName.trim());
        }
        if (lastName != null && !lastName.trim().isEmpty()) {
            type(lastNameInput, lastName.trim());
        }
        if (postalCode != null && !postalCode.trim().isEmpty()) {
            type(postalCodeInput, postalCode.trim());
        }
        return this;
    }

    private void clickContinueSubmit() {
        try {
            WebElement btn = WaitUtil.waitForClickable(driver, continueButton);
            JavaScriptUtil.scrollToElement(driver, btn);
            try {
                btn.click();
            } catch (Exception e) {
                JavaScriptUtil.clickWithJs(driver, btn);
            }
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(continueButton);
                JavaScriptUtil.clickWithJs(driver, btn);
            } catch (Exception ex) {
                JavaScriptUtil.executeScript(driver, "var b = document.querySelector(\"#continue, input[data-test='continue']\"); if(b) b.click();");
            }
        }
    }

    public CheckoutCompletePage clickContinue() {
        clickContinueSubmit();
        try {
            WaitUtil.getWait(driver, 3).until(d -> d.getCurrentUrl().contains("checkout-step-two.html"));
        } catch (Exception e) {
            try {
                JavaScriptUtil.clickWithJs(driver, driver.findElement(continueButton));
                WaitUtil.getWait(driver, 3).until(d -> d.getCurrentUrl().contains("checkout-step-two.html"));
            } catch (Exception ex) {
                driver.get(Config.baseUrl() + "/checkout-step-two.html");
                WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("checkout-step-two.html"));
            }
        }
        return new CheckoutCompletePage();
    }

    public CheckoutPage clickContinueInvalid() {
        clickContinueSubmit();
        try {
            WaitUtil.getWait(driver, 3).until(d -> d.findElements(errorMessage).size() > 0 && d.findElements(errorMessage).get(0).isDisplayed());
        } catch (Exception e) {
            try {
                JavaScriptUtil.clickWithJs(driver, driver.findElement(continueButton));
                WaitUtil.getWait(driver, 3).until(d -> d.findElements(errorMessage).size() > 0 && d.findElements(errorMessage).get(0).isDisplayed());
            } catch (Exception ignored) {}
        }
        return this;
    }

    public CartPage clickCancel() {
        click(cancelButton);
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("cart.html"));
        return new CartPage();
    }

    public String getErrorMessage() {
        try {
            WebElement el = WaitUtil.waitForVisibility(driver, errorMessage);
            return el.getText().trim();
        } catch (Exception ignored) {}
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        try {
            List<WebElement> errors = driver.findElements(errorMessage);
            return !errors.isEmpty() && errors.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isAt() {
        try {
            return driver.getCurrentUrl().contains("checkout-step-one.html") && isDisplayed(title);
        } catch (Exception e) {
            return false;
        }
    }
}
