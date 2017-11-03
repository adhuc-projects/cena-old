Feature: Add an ingredient to a recipe
  Ingredients can be added to recipes only by the recipe creator

Scenario: Add an ingredient to a recipe successfully
  Given an authenticated user
    And an existing "Tomato, cucumber and mozzarella salad" recipe created by this user
    And an existing "Cucumber" ingredient
  When he adds the ingredient to the recipe
  Then the ingredient can be found in the recipe's ingredients list

Scenario: Add an ingredient to an unknown recipe
  Given an authenticated user
    And a non-existent "Tomato, cucumber, olive and feta salad" recipe
    And an existing "Cucumber" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that recipe does not exist

Scenario: Add an unknown ingredient to a recipe
  Given an authenticated user
    And an existing "Tomato, cucumber and mozzarella salad" recipe created by this user
    And a non-existent "Mozzarella" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that recipe does not exist

Scenario: Add an ingredient to a recipe of which the creator is someone else
  Given an authenticated user
    And an existing "Tomato, cucumber and mozzarella salad" recipe created by another user
    And an existing "Cucumber" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that user does not have sufficient rights
    And the ingredient cannot be found in the recipe's ingredients list

Scenario: Add an ingredient to a recipe as anonymous user
  Given an anonymous user
    And an existing "Tomato, cucumber and mozzarella salad" recipe created by this user
    And an existing "Cucumber" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that user is not authenticated
    And the ingredient cannot be found in the recipe's ingredients list
