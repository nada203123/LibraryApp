package com.tekup.LibraryApp.controller.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/manager")
    public String index(Model model) {
        return "manager/dashboard";
    }
}
