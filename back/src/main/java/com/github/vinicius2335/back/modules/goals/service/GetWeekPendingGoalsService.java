package com.github.vinicius2335.back.modules.goals.service;

import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@RequiredArgsConstructor
@Service
public class GetWeekPendingGoalsService {
    private final GoalsRepository goalsRepository;

    // novas metas não devem influenciar nas metas passadas
    // contar as metas realizadas durante a semana e verificar se foi completada ou não
    public void execute(){
        Calendar now = Calendar.getInstance();

        int currentYear = now.get(Calendar.YEAR);
        int currentWeek = now.get(Calendar.WEEK_OF_YEAR);

        // quais sao todas as metas criadas até a semana atual
        // goalsCreatedUpToWeek =
    }
}
