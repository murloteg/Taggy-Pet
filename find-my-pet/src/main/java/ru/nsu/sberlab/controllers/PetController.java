package ru.nsu.sberlab.controllers;

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
    public String dogs(Model model) {
        //model.addAttribute("pets", petService.getPets());
        return "pets";
    }


}
