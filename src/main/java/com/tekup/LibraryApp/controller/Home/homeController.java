package com.tekup.LibraryApp.controller.Home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class homeController {
    @GetMapping
    private String home(Model model){
        model.addAttribute("message","  Home Page");
        return "home";
    }
}
