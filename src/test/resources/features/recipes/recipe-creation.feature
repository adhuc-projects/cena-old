Feature: Create a recipe in the system
  As an authenticated user, I want to create a recipe so that it can be selected during menus generation

Scenario: Create a recipe successfully
  Given an authenticated user
  When he creates the "Tomato, cucumber, olive and feta salad" recipe
  Then the recipe is created
    And the recipe can be found in the list

Scenario: Create a recipe without name
  Given an authenticated user
  When he creates a recipe without name
  Then an error notifies that recipe must have a name
    And the recipe cannot be found in the list

Scenario: Create a recipe without content
  Given an authenticated user
  When he creates the "Tomato, cucumber and mozzarella salad" recipe without content
  Then an error notifies that recipe must have a content
    And the recipe cannot be found in the list

Scenario: Create a recipe as community user
  Given a community user
  When he creates the "Tomato, cucumber and mozzarella salad" recipe
  Then an error notifies that user is not authenticated
    And the recipe cannot be found in the list
