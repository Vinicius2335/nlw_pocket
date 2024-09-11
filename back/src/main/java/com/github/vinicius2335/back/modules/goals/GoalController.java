package com.github.vinicius2335.back.modules.goals;

import com.github.vinicius2335.back.modules.goals.completions.CreateGoalCompletionService;
import com.github.vinicius2335.back.modules.goals.completions.GoalAlreadyCompletedException;
import com.github.vinicius2335.back.modules.goals.completions.dto.request.CreateGoalCompletionRequest;
import com.github.vinicius2335.back.modules.goals.dto.request.CreateGoalRequest;
import com.github.vinicius2335.back.modules.goals.dto.response.GoalsSummaryListResponse;
import com.github.vinicius2335.back.modules.goals.service.CreateGoalService;
import com.github.vinicius2335.back.modules.goals.service.GetWeekPendingGoalsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600, origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/goals")
public class GoalController {
    private final CreateGoalService createGoalService;
    private final GetWeekPendingGoalsService getWeekPendingGoalsService;
    private final CreateGoalCompletionService createGoalCompletionService;

    /**
     * Endpoint responsável por criar uma nova meta
     * @param request representa uma meta a ser adicionada
     * @return meta criada
     */
    @PostMapping
    public ResponseEntity<Goals> createGoalHandler(@RequestBody @Valid CreateGoalRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createGoalService.execute(request));
    }

    /**
     * Endpoint responsável por retorna uma lista com todas as metas da semana
     * @return uma lista de metas resumida da semana
     */
    @GetMapping("/pending-goals")
    public ResponseEntity<GoalsSummaryListResponse> pendingGoalsHandler(){
        return ResponseEntity.ok(getWeekPendingGoalsService.execute());
    }

    /**
     * Endpoint responsavel por registrar que uma meta foi completada
     * @param request representa os campos necessários para completar uma meta
     * @throws GoalNotFoundException se o identificador da meta passada não existir
     * @throws GoalAlreadyCompletedException se a meta já foi completada
     */
    @PostMapping("/completions")
    public ResponseEntity<Void> completionGoalHandler(
            @RequestBody @Valid CreateGoalCompletionRequest request
            ) throws GoalNotFoundException, GoalAlreadyCompletedException {
        createGoalCompletionService.execute(request);

        return ResponseEntity.ok().build();
    }
}
