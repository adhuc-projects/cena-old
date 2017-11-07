Feature: Add an ingredient to a recipe
  Ingredients can be added to recipes only by the recipe creator

Scenario: Add an ingredient to a recipe successfully
  Given an authenticated user
    And an existing "Tomato, cucumber, olive and feta salad" recipe authored by this user
    And an existing "Cucumber" ingredient
  When he adds the ingredient to the recipe
  Then the ingredient is added to recipe
    And the ingredient can be found in the recipe's ingredients list

Scenario: Add an ingredient to an unknown recipe
  Given an authenticated user
    And a non-existent "Tomato, cucumber and mozzarella salad" recipe
    And an existing "Cucumber" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that recipe to add ingredient to does not exist

Scenario: Add an unknown ingredient to a recipe
  Given an authenticated user
    And an existing "Tomato, cucumber, olive and feta salad" recipe authored by this user
    And a non-existent "Feta" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that ingredient to add does not exist

Scenario: Add an ingredient to a recipe of which the author is someone else
  Given another authenticated user
    And an existing "Tomato, cucumber, olive and feta salad" recipe authored by another user
    And an existing "Olive" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that user does not have sufficient rights
    And the ingredient cannot be found in the recipe's ingredients list

Scenario: Add an ingredient to a recipe as anonymous user
  Given an anonymous user
    And an existing "Tomato, cucumber, olive and feta salad" recipe
    And an existing "Olive" ingredient
  When he adds the ingredient to the recipe
  Then an error notifies that user is not authenticated
    And the ingredient cannot be found in the recipe's ingredients list
