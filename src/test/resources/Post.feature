Feature: POST

  Scenario: Verify the create user api.
    Given I set up the request structure to create an user.
    |name | Shubham|
    |job|Test Automation engineer|
    When I hit an api
    Then I verify the name as "Shubham" and job as "Test Automation engineer"

    Scenario: Verify the create user api by passing the data using class object.
      Given I set up the request structure for class object
      When I hit an api
      Then I verify the response

      @UsingObjectMapper
      Scenario: Verify the create user api by using the objectMapper.
      Given I set the request structure using ObjectMapper.
      When I hit an api for an ObjectMapper.
      Then I verify the response