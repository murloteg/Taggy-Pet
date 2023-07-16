package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.sberlab.models.dto.PetCreationDto;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.services.UserService;

@RequestMapping("/user/")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("registration")
    public String createUser(UserRegistrationDto user) {
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("personal-cabinet")
    public String personalCabinetPage(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("user", principal);
        model.addAttribute("hasPrivilegedAccess", principal
                .getAuthorities()
                .contains(Role.ROLE_PRIVILEGED_ACCESS)
        );
        return "personal-cabinet";
    }

    @PostMapping("create-pet")
    public String createPet(
            PetCreationDto pet,
            @AuthenticationPrincipal User principal
    ) {
        userService.createPet(principal, pet);
        return "redirect:/";
    }

    @GetMapping("pets-list")
    public String petsListPage(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("pets", userService.petsListByUserId(principal.getUserId()));
        return "pets-list";
    }

    @GetMapping("delete-account")
    public String accountDeletionPage() {
        return "delete-account";
    }

    @DeleteMapping("delete")
    public String deleteAccount(@AuthenticationPrincipal User principal) {
        userService.deleteUser(principal.getEmail());
        return "main-page";
    }
}
