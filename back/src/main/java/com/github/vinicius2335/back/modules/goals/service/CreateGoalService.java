package com.github.vinicius2335.back.modules.goals.service;

import com.github.vinicius2335.back.modules.goals.CreateGoalRequest;
import com.github.vinicius2335.back.modules.goals.Goals;
import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateGoalService {
    private final GoalsRepository goalsRepository;

    @Transactional
    public Goals execute(CreateGoalRequest request){
        Goals goalsToSave = new Goals(request);
        return goalsRepository.save(goalsToSave);
    }
}
