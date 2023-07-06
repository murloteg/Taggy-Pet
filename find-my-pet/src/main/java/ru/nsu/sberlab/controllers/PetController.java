package ru.nsu.sberlab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sberlab.models.Pet;
import ru.nsu.sberlab.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/")
    public String pets(@RequestParam(name = "chipId", required = false) String chipId, Model model) {
        model.addAttribute("pets", petService.getPets(chipId));
        return "pets";
    }

    @PostMapping("/pet/create")
    public String addPet(Pet pet){
        petService.saveProduct(pet);
        return "redirect:/";
    }

    @GetMapping("/pet/{id}")
    public String petInfo(@PathVariable Long id, Model model) {
        model.addAttribute("pet", petService.getPetById(id));
        return "pet-info";
    }

    @GetMapping("/pet/find")
    public String findPet(@RequestParam(name = "chipId") String chipId, Model model) {
        Pet pet = petService.getPetByChipId(chipId);
        if (pet != null) {
            return petInfo(pet.getId(), model);
        }
        model.addAttribute("chipId", chipId);
        return "pet-not-found";
    }
}
