package com.github.vinicius2335.back.core.utils;

import com.github.vinicius2335.back.modules.goals.Goals;
import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import com.github.vinicius2335.back.modules.goals.completions.GoalCompletions;
import com.github.vinicius2335.back.modules.goals.completions.GoalCompletionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SeedDatabase {
    private final GoalsRepository goalsRepository;
    private final GoalCompletionsRepository goalCompletionsRepository;

    public void execute(){
        goalCompletionsRepository.deleteAll();
        goalsRepository.deleteAll();

        Goals goal1 = Goals.builder()
                .title("Acordar Cedo")
                .desiredWeeklyFrequency(5)
                .build();

        Goals goal2 = Goals.builder()
                .title("Me Exercitar")
                .desiredWeeklyFrequency(3)
                .build();

        Goals goal3 = Goals.builder()
                .title("Meditar")
                .desiredWeeklyFrequency(1)
                .build();

        Goals goal4 = Goals.builder()
                .title("Teste Meta Futura")
                .desiredWeeklyFrequency(2)
                .build();

        Goals goal5 = Goals.builder()
                .title("Teste Meta Semana Passa")
                .desiredWeeklyFrequency(4)
                .build();

        goalsRepository.saveAll(List.of(goal1, goal2, goal3, goal4, goal5));
        goal4.setCreatedAt(OffsetDateTime.now().plusDays(20));
        goal5.setCreatedAt(OffsetDateTime.now().minusDays(8));
        goalsRepository.saveAll(List.of(goal4, goal5));

        GoalCompletions goalCompletions1 = GoalCompletions.builder()
                .goals(goal1)
                .createdAt(OffsetDateTime.now())
                .build();

        GoalCompletions goalCompletions2 = GoalCompletions.builder()
                .goals(goal2)
                .createdAt(OffsetDateTime.now().plusDays(1))
                .build();

        goalCompletionsRepository.saveAll(List.of(goalCompletions1, goalCompletions2));
    }
}
