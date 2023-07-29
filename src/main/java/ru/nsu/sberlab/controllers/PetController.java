package ru.nsu.sberlab.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sberlab.models.dto.PetInitializationDto;
import ru.nsu.sberlab.models.entities.User;
import ru.nsu.sberlab.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.nsu.sberlab.services.FeaturePropertiesService;

@Controller
@RequestMapping("/pet/")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;
    private final FeaturePropertiesService featurePropertiesService;

    @GetMapping("add-new-pet")
    public String petCreationPage(Model model) {
        model.addAttribute("properties", featurePropertiesService.properties());
        return "pet-creation";
    }

    @GetMapping("my-pets/{id}")
    public String myPetInfo(
            @PathVariable(value = "id") String chipId,
            Model model
    ) {
        model.addAttribute("properties", featurePropertiesService.properties());
        model.addAttribute("pet", petService.getPetInitializationDtoByChipId(chipId));
        return "edit-pet"; // TODO: make another page for this feature
    }

    @PutMapping("edit")
    public String editPet(
            PetInitializationDto petInitializationDto,
            @AuthenticationPrincipal User principal
    ) {
        petService.updatePetInfo(petInitializationDto, principal);
        return "redirect:/user/personal-cabinet";
    }

    @GetMapping("delete/{id}")
    public String deletePetPage(
            @PathVariable(value = "id") String chipId,
            Model model
    ) {
        model.addAttribute("pet", petService.getPetInfoByChipId(chipId));
        return "delete-pet";
    }

    @DeleteMapping("delete/{id}")
    public String deletePet(
            @PathVariable(value = "id") String chipId,
            @AuthenticationPrincipal User principal
    ) {
        petService.deletePet(chipId, principal);
        return "redirect:/user/personal-cabinet";
    }

    @GetMapping("privileged-list")
    public String privilegedPetsList(
            Model model,
            @PageableDefault Pageable pageable
    ) {
        model.addAttribute("pets", petService.petsList(pageable));
        return "pets-privileged-list";
    }

    @GetMapping("find")
    public String findPetInfo(
            Model model,
            @RequestParam(name = "chipId", required = false) String chipId
    ) {
        model.addAttribute("pet", petService.getPetInfoByChipId(chipId));
        return "pet-info";
    }
}