package com.github.vinicius2335.back.modules.goals.dto.response;

public interface GoalsSummary {
    String getId();
    String getTitle();
    Integer getDesiredWeeklyFrequency();
    Integer getCompletionCount();
}
