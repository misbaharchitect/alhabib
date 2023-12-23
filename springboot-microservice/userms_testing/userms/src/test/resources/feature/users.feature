Feature: users api calls

  Background: Clean up database
    Given I have no data in users table
    And I add a new user with name NewName

  Scenario: client wants to fetch all users
    When client calls /users GET api
    Then client receives following users
      | userName |
      | NewName  |
