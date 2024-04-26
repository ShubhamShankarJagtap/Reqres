Feature: GET

  @GetAll
  Scenario: Get all customer
    Given I set the request structure
    |method | endpoint |
    |Get    | users    |
    Then I verify the page number is 1

    @GetPage2
    Scenario: Get all users from a particular page

      Given I set the request structure
      |method| endpoint| queryParameter|
      |Get   | users   |        2      |
      Then I verify the page number is 2

      @GetSingleUser
      Scenario: Get a single user
      Given I set the request structure
        |method| endpoint|   path   |
        |Get   | users   |   2      |
        Then I verify the id is 2

        @Resource

        Scenario: Get a resource

          Given I set up the request structure to fetch the resource
          |method|   path    |
          |Get   | resource  |
          Then I verify the total dataSet is 6.

