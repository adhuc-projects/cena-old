Feature: Create an ingredient in the system
  Ingredients can be created only by an ingredient manager
  An ingredient name is unique in the system

Scenario: Create an ingredient successfully
  Given an authenticated ingredient manager
    And a non-existent "Tomato" ingredient
  When he creates the ingredient
  Then the ingredient is created
    And the ingredient can be found in the list

Scenario: Create an ingredient without name
  Given an authenticated ingredient manager
  When he creates an ingredient without name
  Then an error notifies that ingredient must have a name
    And the ingredient cannot be found in the list

Scenario: Create an ingredient with already used name
  Given an authenticated ingredient manager
    And an existing "Cucumber" ingredient
  When he creates the ingredient
  Then an error notifies that ingredient already exists
    And the ingredient can be found in the list

Scenario: Create an ingredient as anonymous user
  Given an anonymous user
    And a non-existent "Potato" ingredient
  When he creates the ingredient
  Then an error notifies that user is not authenticated

Scenario: Create an ingredient without ingredient manager rights
  Given an authenticated user that is not ingredient manager
    And a non-existent "Potato" ingredient
  When he creates the ingredient
  Then an error notifies that user does not have sufficient rights
