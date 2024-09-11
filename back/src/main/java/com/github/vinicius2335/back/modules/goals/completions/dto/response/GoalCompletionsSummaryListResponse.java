package com.github.vinicius2335.back.modules.goals.completions.dto.response;

import java.util.List;

public record GoalCompletionsSummaryListResponse(
        List<GoalCompletionsSummary> goalCompletionsSummaries
) {
}
