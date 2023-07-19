package ru.nsu.sberlab.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.sberlab.services.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/pet/")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("{id}")
    public String petEdit(
            @PathVariable("id") Long petId,
            Model model
    ) {
//        TODO: добавить метод getPetById(Long id)
//        model.addAttribute("pet", petService.getPetById(petId));
        return "pet-edit";
    }

    @GetMapping("add-new-pet")
    public String petCreationPage() {
        return "pet-creation";
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
        model.addAttribute("pet", petService.getPetByChipId(chipId));
        return "pet-info";
    }
}
