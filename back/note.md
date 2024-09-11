# SQL

- WITH (Common Table Expressions)
    - permite escrever querries auxiliares

## Explicação parte a parte da querry de GoalsRepository utilizando WITH do postgres

````sql
-- Retorna todas as metas criadas até essa semana
WITH goals_created_up_to_week AS (
  SELECT * FROM goals
  WHERE EXTRACT(DAY FROM goals.created_at) <= :lastDayOfWeek
)
````  

````sql
-- retorna a contagem de metas concluidas dentro dessa semana
goals_completion_counts AS (
  SELECT
  goal_id,
  (
    SELECT COUNT(completions.id) FROM goal_completions AS completions
    WHERE completions.goal_id = gc.goal_id
  ) AS completionCount
  FROM goal_completions AS gc
  JOIN goals g on gc.goal_id = g.id
  WHERE (EXTRACT(DAY FROM g.created_at) >= :firstDayOfWeek
    AND EXTRACT(DAY FROM g.created_at) <= :lastDayOfWeek)
  GROUP BY gc.goal_id;
````

Para que o WITH funcione precisamos charmar eles
