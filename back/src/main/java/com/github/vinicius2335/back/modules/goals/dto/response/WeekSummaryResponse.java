package com.github.vinicius2335.back.modules.goals.dto.response;

import java.time.LocalDate;
import java.util.List;

public record WeekSummaryResponse(
        LocalDate completedAtDate,
        List<Completion> completions
) {
}
