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

## 1 CAMPO POSSUI UM ARRAY EM JSON VINDO DO BANCO -> UM ARRAY DE UMA CLASSE JAVA

- OBS: essa foi dificil, no final quem salvou foi o chatGPT.
- Ex: dentro de GetWeekSummaryService  

````java

// addModule para evitar erro de nao reconhecer o OffSetDateTime 
ObjectMapper objectMapper = JsonMapper.builder()
                            .addModule(new JavaTimeModule())
                            .build();

// OBS: Logica para o json [{"id": 1, "title": "exemplo"}] que vem de goals_completed_by_week_day completions
// para extrair os objetos dentro da string que representa um array de objetos json
JsonNode jsonNode = objectMapper.readTree(weekSummary.getCompletions());
// converte um String que representa um objeto json para uma classe java
Completion completionConvertedFromJson = objectMapper.readValue(json, Completion.class);

// agora para { \"2024-09-13\" : [{\"id\" : 1, \"title\" : \"Me Exercitar\", \"completedAt\" : \"2024-09-13T17:44:13.826375-03:00\"}
Map<String, List<Completion>> data = objectMapper.readValue(
        jsonString,
        new TypeReference<>() {
        }
````