package ru.nsu.sberlab.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = {FailedUserCreationException.class})
    @GetMapping
    public String handleFailedCreationException() {
        return "failed-registration";
    }
}
