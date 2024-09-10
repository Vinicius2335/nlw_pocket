package com.github.vinicius2335.back.modules.goals.completions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoalCompletionsRepository extends JpaRepository<GoalCompletions, UUID> {
}