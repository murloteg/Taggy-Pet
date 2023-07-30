package ru.nsu.sberlab.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sberlab.models.dto.PetInitializationDto;
import ru.nsu.sberlab.models.dto.UserInfoDto;
import ru.nsu.sberlab.models.dto.UserRegistrationDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.services.SocialNetworkPropertiesService;
import ru.nsu.sberlab.services.UserService;

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
        User user = userService.loadUserByUsername(principal.getEmail());


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
            PetInitializationDto pet,
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
        model.addAttribute("user", userService.loadUserByUsername(principal.getEmail()));
        return "edit-profile";
    }

    @PutMapping("edit-profile")
    public String editProfile(UserInfoDto editedUser) {
        userService.updateUserInfo(editedUser);
        return "redirect:/user/personal-cabinet";
    }
}
