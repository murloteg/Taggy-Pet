package ru.nsu.sberlab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sberlab.model.dto.PetCreationDto;
import ru.nsu.sberlab.model.dto.UserEditDto;
import ru.nsu.sberlab.model.dto.UserRegistrationDto;
import ru.nsu.sberlab.model.entity.User;
import ru.nsu.sberlab.model.enums.Role;
import ru.nsu.sberlab.model.mapper.UserEditDtoMapper;
import ru.nsu.sberlab.service.SocialNetworkPropertiesService;
import ru.nsu.sberlab.service.UserService;

import java.util.Optional;

@RequestMapping("/user/")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SocialNetworkPropertiesService socialNetworkPropertiesService;

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("registration")
    public String registrationPage(Model model) {
        model.addAttribute("properties", socialNetworkPropertiesService.properties());
        return "registration";
    }

    @PostMapping("registration")
    public String createUser(UserRegistrationDto user) {
        userService.createUser(user);
        return "redirect:/user/login";
    }

    @GetMapping("personal-cabinet")
    public String personalCabinetPage(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("user", userService.loadUserByUsername(principal.getEmail()));
        model.addAttribute(
                "hasPrivilegedAccess",
                principal
                        .getAuthorities()
                        .contains(Role.ROLE_PRIVILEGED_ACCESS)
        );
        model.addAttribute("pets", userService.petsListByUserId(principal.getUserId()));
        return "personal-cabinet";
    }

    @PostMapping("create-pet")
    public String createPet(
            PetCreationDto pet,
            @AuthenticationPrincipal User principal
    ) {
        userService.createPet(pet, principal);
        return "redirect:/user/personal-cabinet";
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

    @DeleteMapping("delete-account")
    public String deleteAccount(@AuthenticationPrincipal User principal) {
        userService.deleteUser(principal.getUserId());
        return "redirect:/logout";
    }

    @GetMapping("edit-profile")
    public String editProfilePage(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("user", userService.getUserEditDtoByEmail(principal.getEmail()));
        return "edit-profile";
    }

    @PutMapping("edit-profile")
    public String editProfile(UserEditDto editedUser) {
        userService.updateUserInfo(editedUser);
        return "redirect:/user/personal-cabinet";
    }
}
