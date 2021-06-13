@login
Feature: As a User I should be able to Login
  So that I can use of the functionalities of the app

  Scenario: User is able to Login with correct username and password
    Given I am on the Login Page
    When I login using username as "username" and password as "password"
    Then I am able to login in the app
    