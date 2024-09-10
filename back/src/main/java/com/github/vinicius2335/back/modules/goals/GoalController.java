package com.github.vinicius2335.back.modules.goals;

import com.github.vinicius2335.back.modules.goals.service.CreateGoalService;
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

    @PostMapping
    public ResponseEntity<Goals> createGoalHandler(@RequestBody @Valid CreateGoalRequest request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createGoalService.execute(request));
    }
}
