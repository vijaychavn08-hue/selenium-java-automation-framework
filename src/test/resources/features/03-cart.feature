@cart
Feature: Shopping Cart Management
  As a shopper
  I want to add and remove items from my shopping cart
  So that I can manage the products I intend to buy

  Background:
    Given I am logged in to SauceDemo as "standard_user"

  @smoke @regression
  Scenario: Add product to cart and verify badge count
    When I add product "Sauce Labs Backpack" to the cart
    Then the shopping cart badge count should be 1
    When I add product "Sauce Labs Bike Light" to the cart
    Then the shopping cart badge count should be 2

  @regression
  Scenario: Remove product from cart on inventory page
    Given I add product "Sauce Labs Backpack" to the cart
    When I remove product "Sauce Labs Backpack" from the cart
    Then the shopping cart badge should not be displayed

  @regression
  Scenario: Verify items inside shopping cart page and remove item
    Given I add product "Sauce Labs Bolt T-Shirt" to the cart
    When I open the shopping cart
    Then the cart page should display item "Sauce Labs Bolt T-Shirt"
    When I remove item "Sauce Labs Bolt T-Shirt" from the cart page
    Then the cart page should contain 0 items
