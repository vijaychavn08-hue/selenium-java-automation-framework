@checkout
Feature: Checkout Workflow
  As a shopper with items in my cart
  I want to complete the checkout process
  So that I can purchase my selected products

  Background:
    Given I am logged in to SauceDemo as "standard_user"

  @smoke @regression
  Scenario: Complete full end-to-end checkout purchase
    Given I add product "Sauce Labs Backpack" to the cart
    When I open the shopping cart
    And I proceed to checkout
    And I fill checkout information with first name "Vijay", last name "Chavan", postal code "411001"
    And I continue to checkout overview
    And I finish the order
    Then the order confirmation message should be "Thank you for your order!"

  @regression
  Scenario Outline: Validate required customer information fields during checkout
    Given I add product "Sauce Labs Backpack" to the cart
    When I open the shopping cart
    And I proceed to checkout
    And I fill checkout information with first name "<firstName>", last name "<lastName>", postal code "<postalCode>"
    And I attempt to continue with invalid information
    Then the checkout error message should contain "<expectedError>"

    Examples:
      | firstName | lastName | postalCode | expectedError                  |
      |           | Chavan   | 411001     | Error: First Name is required  |
      | Vijay     |          | 411001     | Error: Last Name is required   |
      | Vijay     | Chavan   |            | Error: Postal Code is required |
