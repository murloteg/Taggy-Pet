package ru.nsu.sberlab.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.sberlab.models.dto.PetDto;
import ru.nsu.sberlab.models.entities.Pet;
import ru.nsu.sberlab.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/")
    public String pets(Principal principal, Model model) {
        model.addAttribute("user", petService.getUserByPrincipal(principal));
        return "main-page";
    }

    @GetMapping("/pet/add-new-pet")
    public String petCreation() {
        return "pet-creation";
    }

    @PostMapping("/pet/create")
    public String createPet(Pet pet, Principal principal){
        petService.createPet(principal, pet);
        return "redirect:/";
    }

    @GetMapping("/pet/list")
    public String allPetsList(Principal principal, Model model) {
        model.addAttribute("pets", petService.getPetsByPrincipal(principal));
        model.addAttribute("user", petService.getUserByPrincipal(principal));
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