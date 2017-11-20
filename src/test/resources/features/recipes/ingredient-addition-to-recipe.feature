Feature: Add an ingredient to a recipe
  As a recipe author, I want to add an ingredient to my recipe so that it can be selected based on its ingredients during menus generation

Scenario: Add an ingredient to a recipe successfully
  Given an authenticated user
    And an existing "Tomato, cucumber, olive and feta salad" recipe authored by this user
    And an existing "Cucumber" ingredient
    And the ingredient is not in the recipe's ingredients list
  When he adds the ingredient to the recipe
  Then the ingredient is added to recipe
    And the ingredient can be found in the recipe's ingredients list
    And the ingredient is not a main ingredient of the recipe

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

Scenario: Add an already added ingredient to a recipe successfully
  Given an authenticated user
    And an existing "Tomato and cantal pie" recipe authored by this user
    And an existing "Mustard" ingredient
    And the ingredient is in the recipe's ingredients list
  When he adds the ingredient to the recipe
  Then the ingredient is added to recipe
    And the ingredient can be found in the recipe's ingredients list
    And the ingredient is not a main ingredient of the recipe

Scenario: Add a main ingredient to a recipe successfully
  Given an authenticated user
    And an existing "Tomato and cantal pie" recipe authored by this user
    And an existing "Cantal" ingredient
  When he adds the ingredient to the recipe as a main ingredient
  Then the ingredient is added to recipe
    And the ingredient can be found in the recipe's ingredients list
    And the ingredient is a main ingredient of the recipe
