package ru.nsu.sberlab.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.nsu.sberlab.exception.FailedPetSearchException;
import ru.nsu.sberlab.exception.FailedUserCreationException;
import ru.nsu.sberlab.exception.ReCaptchaException;

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

    @ExceptionHandler(value = {ReCaptchaException.class})
    @GetMapping
    public String handleReCaptchaException(
            RedirectAttributes redirectAttributes,
            ReCaptchaException exception
    ) {
        redirectAttributes.addFlashAttribute("reCaptchaErrors", exception.getErrors());
        return "redirect:" + exception.getRedirectURI();
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
