package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.enums.Role;
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
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/personal-cabinet")
    public String personalCabinet(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("user", principal);
        model.addAttribute("hasPrivilegedAccess", principal.getAuthorities().contains(Role.ROLE_PRIVILEGED_ACCESS));
        return "personal-cabinet";
    }

    @GetMapping("/user-delete-account")
    public String accountDeletionPage() {
        return "user-delete-account";
    }

    @PostMapping("/user/delete") // TODO: use DeleteMapping after migration on Thymeleaf
    public String deleteAccount(@AuthenticationPrincipal User principal) {
        userService.deleteUser(principal.getId());
        return "redirect:/login?logout";
    }

    @GetMapping("/user")
    public String returnToMainPage() {
        return "main-page";
    }
}
