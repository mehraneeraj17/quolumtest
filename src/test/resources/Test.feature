Feature: Test

  Scenario: Retrieve the user using the Id
    Given the base URL is "https://reqres.in"
    And the end point is "/api/users/2"
    And the request type is "GET"
    When the request is sent
    Then the response should be 200
    And response should be as expected