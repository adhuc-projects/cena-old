Feature: Create a recipe in the system
  Recipes can be created only by an authenticated user

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

Scenario: Create a recipe as anonymous user
  Given an anonymous user
  When he creates the "Tomato, cucumber and mozzarella salad" recipe
  Then an error notifies that user is not authenticated
    And the recipe cannot be found in the list
