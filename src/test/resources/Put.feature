Feature: Verify the API for put method.

  Scenario: Verify the functionality of the Reqres api for put method.
    Given I set the request structure for put api.
    |name | William |
    | job | Project Manager |
    When I hit a put api for user 2.
    Then I verify the response for the put api.
