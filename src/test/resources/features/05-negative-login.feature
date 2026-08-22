@login @negative
Feature: Negative Authentication Scenarios
  As a QA engineer
  I want invalid login attempts to be rejected with meaningful error messages
  So that unauthorized access is prevented and users receive clear feedback

  Background:
    Given I open the SauceDemo login page

  @smoke @regression
  Scenario Outline: Failed login with invalid or missing credentials
    When I attempt to login with username "<username>" and password "<password>"
    Then the login error message should contain "<expectedError>"

    Examples:
      | username        | password         | expectedError                                                             |
      | locked_out_user | secret_sauce     | Epic sadface: Sorry, this user has been locked out.                       |
      | invalid_user    | wrong_password   | Epic sadface: Username and password do not match any user in this service |
      |                 | secret_sauce     | Epic sadface: Username is required                                        |
      | standard_user   |                  | Epic sadface: Password is required                                        |
