package ru.nsu.sberlab.controllers;

import org.springframework.data.domain.PageRequest;
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
    public String mainPage(@AuthenticationPrincipal User principal, Model model) {
        model.addAttribute("user", principal);
        return "main-page";
    }

    @GetMapping("/pet/add-new-pet")
    public String petCreation() {
        return "pet-creation";
    }

    @PostMapping("/pet/create")
    public String createPet(PetDto pet, @AuthenticationPrincipal User principal){
        petService.createPet(principal, pet);
        return "redirect:/";
    }

    @GetMapping("/pet/list")
    public String petsList(@AuthenticationPrincipal User principal, Model model,
                           @RequestParam(required = false, defaultValue = "0") int page,
                           @RequestParam(required = false, defaultValue = "10") int size) {
        if (principal.getRoles().contains(Role.ROLE_PRIVILEGED_ACCESS)) {
            model.addAttribute("pets", petService.petsList(PageRequest.of(page, size)));
        } else {
            model.addAttribute("pets", petService.petsListByUserId(principal));
        }
        model.addAttribute("user", principal);
        return "pets-list";
    }

    @GetMapping("/pet/find")
    public String findPet(@RequestParam(name = "chipId", required = false) String chipId, Model model) {
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