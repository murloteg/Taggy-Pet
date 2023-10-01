package ru.nsu.sberlab.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nsu.sberlab.exceptions.FailedPetSearchException;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;

@ControllerAdvice
public class ErrorController { // TODO: add handling of IllegalAccessToPetException
    @ExceptionHandler(value = {FailedPetSearchException.class})
    @GetMapping
    public String handleFailedPetSearchException(
            Model model,
            FailedPetSearchException exception
    ) {
        model.addAttribute("searchParameter", exception.getMessage());
        return "pet-not-found";
    }

    @ExceptionHandler(value = {FailedUserCreationException.class})
    @GetMapping
    public String handleFailedCreationException(
            Model model,
            FailedUserCreationException exception
    ) {
        model.addAttribute("exception", exception.getMessage());
        return "failed-registration";
    }

    @ExceptionHandler(value = Exception.class)
    @GetMapping
    public String handleException(
            Model model,
            Exception exception
    ) {
        model.addAttribute("exception", exception.getMessage());
        return "error-message";
    }
}
