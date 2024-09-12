package com.github.vinicius2335.back.modules.goals.dto.response;


import java.time.LocalDate;

public interface WeekSummary {
    LocalDate getCompletedAtDate();
    String getCompletions();
}
