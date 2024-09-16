package com.github.vinicius2335.back.api.exceptionhandler;

import com.github.vinicius2335.back.modules.goals.GoalNotFoundException;
import com.github.vinicius2335.back.modules.goals.completions.GoalAlreadyCompletedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("One or more fields are invalid");

        Map<String, String> fields = ex.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        DefaultMessageSourceResolvable::getDefaultMessage
                ));

        problemDetail.setProperty("fields", fields);

        return super.handleExceptionInternal(
                ex,
                problemDetail,
                headers,
                status,
                request
        );
    }

    @ExceptionHandler(GoalAlreadyCompletedException.class)
    public ProblemDetail handleGoalAlreadyCompleted(
            GoalAlreadyCompletedException ex
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Goal Already Completed");
        problemDetail.setProperty("message", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(GoalNotFoundException.class)
    public ProblemDetail handleGoalNotFound(
            GoalNotFoundException ex
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Goal Not Found");
        problemDetail.setProperty("message", ex.getMessage());
        return problemDetail;
    }
}
