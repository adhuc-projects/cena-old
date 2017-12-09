Feature: Generate menus composed of recipes
  As a community user, I want to generate menus so that I do not have to think about menus myself

Scenario Outline: Generate a menus list successfully
  Given a community user
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
    | days | recipes_count | start_date | meal_frequency    | meals_count |
    |    1 |             1 | 2017-01-02 | WEEK_WORKING_DAYS |           1 |
    |    1 |             2 | 2017-01-01 | WEEK_WORKING_DAYS |           2 |
    |    5 |             7 | 2017-01-02 | WEEK_WORKING_DAYS |           5 |
    |    7 |            12 | 2017-01-02 | WEEK_WORKING_DAYS |           9 |
    |    1 |             2 | 2017-01-02 | TWICE_A_DAY       |           2 |
    |    1 |             2 | 2017-01-01 | TWICE_A_DAY       |           2 |
    |    7 |            18 | 2017-01-02 | TWICE_A_DAY       |          14 |
