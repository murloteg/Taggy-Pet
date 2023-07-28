package ru.nsu.sberlab.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nsu.sberlab.models.entities.User;

@Controller
@RequestMapping("/")
public class HomePageController {
    @GetMapping()
    public String homePage(
            Model model,
            @AuthenticationPrincipal User principal
    ) {
        model.addAttribute("user", principal);
        return "main-page";
    }
}
