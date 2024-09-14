package com.github.vinicius2335.back.modules.goals.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import com.github.vinicius2335.back.modules.goals.dto.response.Completion;
import com.github.vinicius2335.back.modules.goals.dto.response.WeekSummary;
import com.github.vinicius2335.back.modules.goals.dto.response.WeekSummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@RequiredArgsConstructor
@Service
@Log4j2
public class GetWeekSummaryService {
    private final GoalsRepository goalsRepository;

    /**
     * retorna uma lista onde cada objeto representa um resumo com o total de metas,
     * quantas foram completadas e quais foram finalizadas.
     * @return lista um resumo do progresso do usuário durante a semana
     */
    public Map<String, Object> execute() {
        LocalDate now = LocalDate.now();

        LocalDate firstDayOfWeek = now.with(previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = now.with(nextOrSame(DayOfWeek.SATURDAY));

        List<WeekSummary> weekSummaries = goalsRepository.goalsCompletedByWeekDay(
                firstDayOfWeek.getDayOfMonth(),
                lastDayOfWeek.getDayOfMonth()
        );
        Map<String, Object> response = new HashMap<>();

        // Para cada resumo de um dia da semana que foi adicionado metas
        WeekSummaryResponse summary = weekSummaries.stream()
                .map(weekSummary -> {
                    ObjectMapper objectMapper = JsonMapper.builder()
                            .addModule(new JavaTimeModule())
                            .build();

                    LinkedHashMap<String, List<Completion>> getGoalsPerDay = new LinkedHashMap<>();
                    String jsonString = weekSummary.getGoalsPerDay();

                    try {
                        // transforma a coluna que retorna um json em formato de string para um objeto
                        Map<String, List<Completion>> data = objectMapper.readValue(
                                jsonString,
                                new TypeReference<>() {
                                }
                        );

                        getGoalsPerDay.putAll(data);

                    } catch (JsonProcessingException e) {
                        log.error("Error when trying to transform a string that represents json into Object Java: {}", e.getMessage());
                    }

                    // retorna um resumo com o total de metas, quantas foram completadas e quais foram finalizadas
                    return new WeekSummaryResponse(
                            weekSummary.getCompleted(),
                            weekSummary.getTotal(),
                            getGoalsPerDay
                    );
                }).findFirst().orElse(null);

        response.put("summary", summary);
        return response;
    }
}