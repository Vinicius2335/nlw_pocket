package com.github.vinicius2335.back.modules.goals.dto.response;

import java.util.List;
import java.util.Map;

public record WeekSummaryResponse(
        Integer completed,
        Integer total,
        Map<String, List<Completion>> goalsPerDay
) {
}
