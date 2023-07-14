package ru.nsu.sberlab.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.sberlab.models.dto.PetCreationDto;
import ru.nsu.sberlab.models.entities.User;
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
            PetCreationDto pet,
            @AuthenticationPrincipal User principal
    ) {
        petService.createPet(principal, pet);
        return "redirect:/";
    }

    @GetMapping("/pet/list")
    public String petsList(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("pets", petService.petsListByUserId(principal.getId()));
        model.addAttribute("user", principal);
        return "pets-list";
    }

    @GetMapping("/pet/privileged-list")
    public String allPetsList(
            Model model,
            @AuthenticationPrincipal User principal,
            @PageableDefault Pageable pageable
    ) {
        model.addAttribute("pets", petService.petsList(pageable));
        model.addAttribute("user", principal);
        return "pets-privileged-list";
    }

    @GetMapping("/pet/find")
    public String findPet(
            Model model,
            @RequestParam(name = "chipId", required = false) String chipId
    ) {
        model.addAttribute("pet", petService.getPetByChipId(chipId));
        return "pet-info";
    }

    @GetMapping("/pet")
    public String returnToMainPage() {
        return "main-page";
    }
}