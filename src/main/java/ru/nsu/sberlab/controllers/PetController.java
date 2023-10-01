package ru.nsu.sberlab.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nsu.sberlab.models.dto.PetEditDto;
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
        model.addAttribute("pet", petService.getPetEditDtoByChipId(chipId));
        return "edit-pet";
    }

    @PutMapping("edit")
    public String editPet(
            PetEditDto petEditDto,
            @AuthenticationPrincipal User principal
    ) {
        petService.updatePetInfo(petEditDto, principal);
        return "redirect:/user/personal-cabinet";
    }

    @GetMapping("delete/{id}")
    public String deletePetPage(
            @PathVariable(value = "id") String chipId,
            Model model
    ) {
        model.addAttribute("pet", petService.getPetInfoBySearchParameter(chipId));
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
            @RequestParam(name = "searchParameter", required = false) String searchParameter
    ) {
        model.addAttribute("pet", petService.getPetInfoBySearchParameter(searchParameter));
        return "pet-info";
    }
}
