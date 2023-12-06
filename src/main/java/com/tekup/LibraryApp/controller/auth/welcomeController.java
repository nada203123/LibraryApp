package com.tekup.LibraryApp.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
@RequiredArgsConstructor
public class welcomeController {
    @GetMapping
    private String welcome (Model model){
        model.addAttribute("message","  WELCOME ");
        return "welcome";
    }
}
