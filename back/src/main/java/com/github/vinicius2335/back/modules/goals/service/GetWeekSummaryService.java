package com.github.vinicius2335.back.modules.goals.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.vinicius2335.back.modules.goals.GoalsRepository;
import com.github.vinicius2335.back.modules.goals.dto.response.Completion;
import com.github.vinicius2335.back.modules.goals.dto.response.WeekSummary;
import com.github.vinicius2335.back.modules.goals.dto.response.WeekSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@RequiredArgsConstructor
@Service
public class GetWeekSummaryService {
    private final GoalsRepository goalsRepository;

    public Map<String, Object> execute() {
        LocalDate now = LocalDate.now();

        LocalDate firstDayOfWeek = now.with(previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastDayOfWeek = now.with(nextOrSame(DayOfWeek.SATURDAY));

        List<WeekSummary> weekSummaries = goalsRepository.goalsCompletedByWeekDay(
                firstDayOfWeek.getDayOfMonth(),
                lastDayOfWeek.getDayOfMonth()
        );
        Map<String, Object> response = new HashMap<>();

        List<WeekSummaryResponse> summary = weekSummaries.stream()
                .map(weekSummary -> {
                    ObjectMapper objectMapper = JsonMapper.builder()
                            .addModule(new JavaTimeModule())
                            .build();

                    List<String> jsons = new ArrayList<>();
                    List<Completion> completions = new ArrayList<>();

                    try {
                        JsonNode jsonNode = objectMapper.readTree(weekSummary.getCompletions());
                        if (jsonNode.isArray()) {
                            for (int i = 0; i < jsonNode.size(); i++) {
                                jsons.add(jsonNode.get(i).toString());
                            }
                        }

                        jsons.forEach(
                                json -> {
                                    try {
                                        Completion completionConvertedFromJson = objectMapper.readValue(json, Completion.class);
                                        completions.add(completionConvertedFromJson);
                                    } catch (JsonProcessingException e) {
                                        // TODO - criar uma Exception
                                        e.printStackTrace();
                                    }
                                }
                        );

                    } catch (JsonProcessingException e) {
                        // TODO - Criar uma Exception
                        e.printStackTrace();
                    }

                    return new WeekSummaryResponse(weekSummary.getCompletedAtDate(), completions);
                }).toList();

        response.put("summary", summary);
        return response;
    }
}