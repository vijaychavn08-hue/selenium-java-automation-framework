@login
Feature: User Authentication
  As a registered user of SauceDemo
  I want to login with valid credentials
  So that I can access the product catalog and perform shopping activities

  @smoke @regression
  Scenario: Login successfully with standard user
    Given I open the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then the inventory page should be displayed
    And the header title should be "Swag Labs"

  @regression
  Scenario: Successful login and logout workflow
    Given I open the SauceDemo login page
    When I login with username "standard_user" and password "secret_sauce"
    Then the inventory page should be displayed
    When I open the navigation menu and click logout
    Then the login page should be displayed
