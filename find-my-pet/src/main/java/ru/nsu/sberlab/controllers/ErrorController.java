package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;

@ControllerAdvice
@RequiredArgsConstructor
public class ErrorController {
    @ExceptionHandler(value = {FailedUserCreationException.class})
    @GetMapping
    public String handleFailedCreationException() {
        return "failed-registration";
    }

    @ExceptionHandler(value = Exception.class)
    @GetMapping
    public String handleException() {
        return "error-message";
    }
}
