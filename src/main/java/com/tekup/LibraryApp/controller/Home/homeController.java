package com.tekup.LibraryApp.controller.Home;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class homeController {
    @GetMapping
    private String home(Model model, @CookieValue(value = "token", defaultValue = "token") String token,HttpServletRequest request){
        model.addAttribute("message","  Home Page");
        model.addAttribute("jwt",token);

        return "home";
    }
}
