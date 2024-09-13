package com.github.vinicius2335.back.modules.goals.completions;

import com.github.vinicius2335.back.modules.goals.GoalNotFoundException;
import com.github.vinicius2335.back.modules.goals.Goals;
import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import com.github.vinicius2335.back.modules.goals.completions.dto.request.CreateGoalCompletionRequest;
import com.github.vinicius2335.back.modules.goals.completions.dto.response.GoalCompletionsSummary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@RequiredArgsConstructor
@Service
public class CreateGoalCompletionService {
    private final GoalCompletionsRepository goalCompletionsRepository;
    private final GoalsRepository goalsRepository;

    /**
     * Finaliza a meta do dia
     * @param request apresenta o identificador da meta a ser batida
     * @throws GoalNotFoundException se a meta nao for encontrada pelo identificador
     * @throws GoalAlreadyCompletedException se a meta ja foi concluida
     */
    @Transactional
    public void execute(
            CreateGoalCompletionRequest request
    ) throws GoalNotFoundException, GoalAlreadyCompletedException {
        LocalDate now = LocalDate.now();

        LocalDate firstDayOfWeek = now.with(previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = now.with(nextOrSame(DayOfWeek.SATURDAY));

        Goals goal = goalsRepository.findById(UUID.fromString(request.goalId()))
                .orElseThrow(() -> new GoalNotFoundException("Goal not found by id: " + request.goalId()));

        GoalCompletions newGoalCompletions = GoalCompletions.builder()
                .goals(goal)
                .createdAt(OffsetDateTime.now())
                .build();

        // sempre irá retornar 1 valor já que estamos bustando pelo id de goal
        List<GoalCompletionsSummary> goalCompletionsSummaries = goalsRepository.goalCompletionCounts(
                firstDayOfWeek.getDayOfMonth(),
                lastDayOfWeek.getDayOfMonth(),
                goal.getId()
        );

        GoalCompletionsSummary goalCompletionsSummary = goalCompletionsSummaries.get(0);
        if (goalCompletionsSummary.getCompletionCount() >= goalCompletionsSummary.getDesiredWeeklyFrequency()) {
            throw new GoalAlreadyCompletedException("Goal Already completed this week");
        }

        goalCompletionsRepository.save(newGoalCompletions);
    }
}
