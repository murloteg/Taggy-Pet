package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nsu.sberlab.exceptions.FailedUserCreationException;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.services.UserService;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(UserRegistrationDto user) {
        if (!userService.createUser(user)) {
            throw new FailedUserCreationException();
        }
        return "redirect:/login";
    }

    @GetMapping("/personal-cabinet")
    public String personalCabinet(@AuthenticationPrincipal User principal, Model model) {
        model.addAttribute("user", principal);
        return "personal-cabinet";
    }

    @GetMapping("/user-delete-account")
    public String accountDeletionPage() {
        return "user-delete-account";
    }

    @PostMapping("/user/delete")
    public String deleteAccount(@AuthenticationPrincipal User principal, Model model) {
        model.addAttribute("user", principal);
        userService.deleteUser(principal.getId());
        return "user-successful-removal";
    }

    @GetMapping("/user")
    public String returnToMainPage() {
        return "main-page";
    }
}
