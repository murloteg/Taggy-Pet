package ru.nsu.sberlab.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.sberlab.models.dto.PetDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.models.enums.Role;
import ru.nsu.sberlab.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/")
    public String mainPage(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("user", principal);
        return "main-page";
    }

    @GetMapping("/pet/add-new-pet")
    public String petCreation() {
        return "pet-creation";
    }

    @PostMapping("/pet/create")
    public String createPet(
            PetDto pet,
            @AuthenticationPrincipal User principal
    ) {
        petService.createPet(principal, pet);
        return "redirect:/";
    }

    @GetMapping("/pet/list")
    public String petsList(
            Model model,
            @AuthenticationPrincipal User principal,
            @PageableDefault Pageable pageable
    ) {
        if (principal.getRoles().contains(Role.ROLE_PRIVILEGED_ACCESS)) {
            model.addAttribute("pets", petService.petsList(pageable));
        } else {
            Long userId = principal.getId();
            model.addAttribute("pets", petService.petsListByUserId(userId));
        }
        model.addAttribute("user", principal);
        return "pets-list";
    }

    @GetMapping("/pet/find")
    public String findPet(
            Model model,
            @RequestParam(name = "chipId", required = false) String chipId
    ) {
        PetDto pet = petService.getPetByChipId(chipId);
        if (pet != null) {
            model.addAttribute("pet", pet);
            return "pet-info";
        }
        model.addAttribute("chipId", chipId);
        return "pet-not-found";
    }

    @GetMapping("/pet")
    public String returnToMainPage() {
        return "main-page";
    }
}