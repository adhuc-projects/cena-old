Feature: Generate menus composed of recipes
  As an authenticated user, I want to generate menus so that I do not have to think about menus myself

Scenario Outline: Generate a menus list successfully
  Given an authenticated user
    And a list of existing recipes with at least <recipes_count> elements
  When he specifies a period of time of <days> days starting from <start_date>
    And he specifies the frequency of meals as <meal_frequency>
    And he generates the menus
  Then the menus have been generated
    And the number of meals in the list is <meals_count>
    And the meals distribution corresponds to the specifications
    And no meal has redundant recipe
    And no meal has the same main ingredients as the previous nor next day

  Examples:
    | days | recipes_count | start_date  | meal_frequency    | meals_count |
    |    1 |             1 | next monday | WEEK_WORKING_DAYS |           1 |
    |    1 |             2 | next sunday | WEEK_WORKING_DAYS |           2 |
    |    5 |             7 | next monday | WEEK_WORKING_DAYS |           5 |
    |    7 |            12 | next monday | WEEK_WORKING_DAYS |           9 |
    |    1 |             2 | next monday | TWICE_A_DAY       |           2 |
    |    1 |             2 | next sunday | TWICE_A_DAY       |           2 |
    |    7 |            18 | next monday | TWICE_A_DAY       |          14 |

Scenario: Generate a menus list starting in the past
  Given an authenticated user
    And a list of existing recipes with at least 10 elements
  When he specifies a period of time of 5 days starting from yesterday
    And he specifies the frequency of meals as WEEK_WORKING_DAYS
    And he generates the menus
  Then an error notifies that menu cannot be generated in the past

Scenario: Generate a menus list as community user
  Given a community user
    And a list of existing recipes with at least 2 elements
  When he specifies a period of time of 1 days starting from next monday
    And he specifies the frequency of meals as WEEK_WORKING_DAYS
    And he generates the menus
  Then an error notifies that user is not authenticated

Scenario: Generated menus are accessible only to owner
  Given a list of existing recipes with at least 2 elements
    And generated menus by an authenticated user for a period of time of 2 days starting from next monday
    And another authenticated user
  When he lists the menus for the same period of time starting from the same day
  Then the menus list is empty or different from authenticated user list
