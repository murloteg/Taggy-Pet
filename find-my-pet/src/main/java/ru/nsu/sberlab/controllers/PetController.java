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

@Controller
@RequestMapping("/pet/")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("add-new-pet")
    public String petCreationPage() {
        return "pet-creation";
    }

    @GetMapping("my-pets/{id}")
    public String myPetInfo(
            @PathVariable("id") String chipId,
            Model model
    ) {
        model.addAttribute("pet", petService.getPetEditByChipId(chipId));
        return "edit-pet"; // TODO: make another page for this feature
    }

    @PutMapping("edit") // FIXME
    public String editPet(
            PetEditDto petEditDto,
            @AuthenticationPrincipal User principal
    ) {
        petService.updatePetInfo(principal, petEditDto);
        return "pesonal-cabinet";
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
