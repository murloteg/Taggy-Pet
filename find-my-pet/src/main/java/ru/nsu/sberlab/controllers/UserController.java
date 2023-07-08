package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.nsu.sberlab.models.dto.UserDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.services.UserService;

import java.security.Principal;

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
    public String createUser(User user) {
        if (!userService.createUser(user)) {
            return "failed-registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/personal-cabinet")
    public String personalCabinet(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "personal-cabinet";
    }

    @GetMapping("/user-delete-account")
    public String accountDeletionPage() {
        return "user-delete-account";
    }

    @PostMapping("/user/delete")
    public String deleteAccount(Principal principal, Model model) {
        UserDto userDto = userService.getUserByPrincipal(principal);
        model.addAttribute("user", userDto);
        userService.deleteUser(userDto.getId());
        return "user-successful-removal";
    }

    @GetMapping("/user")
    public String returnToMainPage() {
        return "main-page";
    }
}
