@products
Feature: Product Catalog & Details
  As a logged-in shopper
  I want to browse, sort, and inspect products
  So that I can find items I wish to purchase

  Background:
    Given I am logged in to SauceDemo as "standard_user"

  @smoke @regression
  Scenario: Display all catalog items on inventory page
    Then the inventory page should display 6 products

  @regression
  Scenario Outline: Sort products by various criteria
    When I sort products by "<sortOption>"
    Then the products should be sorted accordingly

    Examples:
      | sortOption          |
      | Name (A to Z)       |
      | Name (Z to A)       |
      | Price (low to high) |
      | Price (high to low) |

  @regression
  Scenario: Navigate to product details and return to catalog
    When I click on product "Sauce Labs Backpack"
    Then the product details page should display name "Sauce Labs Backpack"
    When I click back to products
    Then the inventory page should be displayed
