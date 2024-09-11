package com.github.vinicius2335.back.modules.goals;

import com.github.vinicius2335.back.modules.goals.completions.dto.response.GoalCompletionsSummary;
import com.github.vinicius2335.back.modules.goals.dto.response.GoalsSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GoalsRepository extends JpaRepository<Goals, UUID> {

    // pending goals / metas pendentes
    @Query(value = """
             WITH goals_created_up_to_week AS (
                         SELECT *
                         FROM goals
                         WHERE EXTRACT(DAY FROM goals.created_at) <= :lastDayOfWeek
                     ), goals_completion_counts AS (
                         SELECT
                             goal_id,
                             COUNT(gc.id) AS completion_count
                         FROM goal_completions AS gc
                         WHERE (
                            EXTRACT(DAY FROM gc.created_at) >= :firstDayOfWeek AND
                            EXTRACT(DAY FROM gc.created_at) <= :lastDayOfWeek
                         )
                         GROUP BY gc.goal_id
                     ) SELECT
                        goals_created_up_to_week.id AS id,
                        goals_created_up_to_week.title AS title,
                        goals_created_up_to_week.desired_weekly_frequency AS desired_weekly_frequency,
                        COALESCE(goals_completion_counts.completion_count, 0) AS completion_count
                     FROM goals_created_up_to_week
                     LEFT JOIN goals_completion_counts ON goals_completion_counts.goal_id = goals_created_up_to_week.id;
            """, nativeQuery = true)
    List<GoalsSummary> pendingGoals(
            @Param("firstDayOfWeek") int firstDayOfWeek,
            @Param("lastDayOfWeek") int lastDayOfWeek
    );

    @Query(
            value = """
                    WITH goals_completion_counts AS (
                         SELECT
                             goal_id,
                             COUNT(gc.id) AS completion_count
                         FROM goal_completions AS gc
                         WHERE (
                            EXTRACT(DAY FROM gc.created_at) >= :firstDayOfWeek AND
                            EXTRACT(DAY FROM gc.created_at) <= :lastDayOfWeek AND
                            gc.goal_id = :goalId
                         )
                         GROUP BY gc.goal_id
                    ) SELECT
                        goals.desired_weekly_frequency AS desired_weekly_frequency,
                        COALESCE(goals_completion_counts.completion_count, 0) AS completion_count
                    FROM goals
                    LEFT JOIN goals_completion_counts ON goals_completion_counts.goal_id = goals.id
                    WHERE goals.id = :goalId
                    LIMIT 1
                    """,
            nativeQuery = true
    )
    List<GoalCompletionsSummary> goalCompletionCounts(
            @Param("firstDayOfWeek") int firstDayOfWeek,
            @Param("lastDayOfWeek") int lastDayOfWeek,
            @Param("goalId") UUID goalId
    );
}