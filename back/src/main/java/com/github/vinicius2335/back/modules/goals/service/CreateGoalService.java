package com.github.vinicius2335.back.modules.goals.service;

import com.github.vinicius2335.back.modules.goals.dto.request.CreateGoalRequest;
import com.github.vinicius2335.back.modules.goals.Goals;
import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateGoalService {
    private final GoalsRepository goalsRepository;

    /**
     * Cria uma nova meta
     * @param request representa a meta a ser criado
     * @return retorna a meta criada
     */
    @Transactional
    public Goals execute(CreateGoalRequest request){
        Goals goalsToSave = new Goals(request);
        return goalsRepository.save(goalsToSave);
    }
}
