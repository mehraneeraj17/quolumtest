Feature: Test

  @API
  Scenario: Retrieve the user using the Id
    Given the base URL is "https://reqres.in"
    And the end point is "/api/users/2"
    And the request type is "GET"
    When the request is sent
    Then the response should be 200
    And response should be as expected

  @UI @Sanity
  Scenario: List of the contacts should be retrieved
    Given the user is in sign in page
    And user enters the username
    And user clicks on the Next Button
    And user enters the password
    When user clicks on the Next Button in Password page
    Then user should be in the Inbox
    Given user opens the contacts
    Then contacts should contain list of users
      | QuolumContact Test         |
      | QuolumContactTest2 Testing |

