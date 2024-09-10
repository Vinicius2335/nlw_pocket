package com.github.vinicius2335.back.modules.goals;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoalsRepository extends JpaRepository<Goals, UUID> {
}