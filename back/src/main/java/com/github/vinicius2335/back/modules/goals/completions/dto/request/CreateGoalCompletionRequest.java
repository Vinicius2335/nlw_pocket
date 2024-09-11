package com.github.vinicius2335.back.modules.goals.completions.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateGoalCompletionRequest(
        @NotBlank(message = "goal_id cannot be null OR empty")
        String goalId
) {
}
