package com.github.vinicius2335.back.modules.goals.dto.response;

import java.util.List;

public record GoalsSummaryListResponse(
        List<GoalsSummary> pendingGoals
) {
}
