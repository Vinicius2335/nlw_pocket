package com.github.vinicius2335.back.modules.goals.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateGoalRequest(
        @NotBlank(message = "Title cannot be null or empty")
        String title,

        @Min(value = 1, message = "desiredWeeklyFrequency cannot be less than 1")
        @Max(value = 7, message = "desiredWeeklyFrequency cannot be greater than 7")
        int desiredWeeklyFrequency
) {
}
