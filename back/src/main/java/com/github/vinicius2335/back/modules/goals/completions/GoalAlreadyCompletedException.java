package com.github.vinicius2335.back.modules.goals.completions;

public class GoalAlreadyCompletedException extends Exception{
    public GoalAlreadyCompletedException(String message) {
        super(message);
    }
}
