package com.github.vinicius2335.back.modules.goals.service;

import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import com.github.vinicius2335.back.modules.goals.dto.response.GoalsSummary;
import com.github.vinicius2335.back.modules.goals.dto.response.GoalsSummaryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@RequiredArgsConstructor
@Service
public class GetWeekPendingGoalsService {
    private final GoalsRepository goalsRepository;

    // novas metas não devem influenciar nas metas passadas
    // contar as metas realizadas durante a semana e verificar se foi completada ou não

    /**
     * Busca todas as metas até a semana atual. Conta as metas realizadas durante a semana e verificar se foi completada ou não
     * @return lista de metas resumidas
     */
    public GoalsSummaryListResponse execute(){
        LocalDate now = LocalDate.now();

        LocalDate firstDayOfWeek = now.with(previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = now.with(nextOrSame(DayOfWeek.SATURDAY));

        List<GoalsSummary> goalsSummaries = goalsRepository.pendingGoals(
                firstDayOfWeek.getDayOfMonth(),
                lastDayOfWeek.getDayOfMonth()
        );

        return new GoalsSummaryListResponse(goalsSummaries);
    }
}
