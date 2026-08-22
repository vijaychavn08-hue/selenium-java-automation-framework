package com.vijaychavan.pages;

import com.vijaychavan.framework.base.BasePage;
import com.vijaychavan.framework.javascript.JavaScriptUtil;
import com.vijaychavan.framework.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CheckoutPage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By firstNameInput = By.cssSelector("#first-name, input[data-test='firstName']");
    private final By lastNameInput = By.cssSelector("#last-name, input[data-test='lastName']");
    private final By postalCodeInput = By.cssSelector("#postal-code, input[data-test='postalCode']");
    private final By continueButton = By.cssSelector("#continue, input[data-test='continue'], input[name='continue']");
    private final By cancelButton = By.cssSelector("#cancel, button[data-test='cancel']");
    private final By errorMessage = By.cssSelector("[data-test='error'], .error-message-container h3, .error-message-container");

    public CheckoutPage() {
        super();
    }

    public CheckoutPage fillInformation(String firstName, String lastName, String postalCode) {
        if (firstName != null && !firstName.isEmpty()) {
            type(firstNameInput, firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            type(lastNameInput, lastName);
        }
        if (postalCode != null && !postalCode.isEmpty()) {
            type(postalCodeInput, postalCode);
        }
        return this;
    }

    private void clickContinueSubmit() {
        try {
            WebElement btn = driver.findElement(continueButton);
            btn.click();
        } catch (Exception e) {
            JavaScriptUtil.executeScript(driver, "var b = document.querySelector(\"input[name='continue'], #continue\"); if (b) b.click();");
        }
    }

    public CheckoutCompletePage clickContinue() {
        clickContinueSubmit();
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("checkout-step-two.html"));
        return new CheckoutCompletePage();
    }

    public CheckoutPage clickContinueInvalid() {
        clickContinueSubmit();
        try {
            WaitUtil.getWait(driver, 3).until(d -> driver.findElements(errorMessage).size() > 0);
        } catch (Exception ignored) {}
        return this;
    }

    public CartPage clickCancel() {
        click(cancelButton);
        WaitUtil.getWait(driver).until(d -> d.getCurrentUrl().contains("cart.html"));
        return new CartPage();
    }

    public String getErrorMessage() {
        try {
            List<WebElement> errors = driver.findElements(errorMessage);
            if (!errors.isEmpty()) {
                String txt = errors.get(0).getText().trim();
                if (txt.isEmpty()) {
                    txt = errors.get(0).getAttribute("innerText").trim();
                }
                return txt;
            }
        } catch (Exception ignored) {}
        return text(errorMessage);
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
