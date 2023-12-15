package com.tekup.LibraryApp.controller.Home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class welcomeController {
    @GetMapping("")
    private String welcome (Model model){
        model.addAttribute("message","  WELCOME ");
        return "welcome";
    }
    @GetMapping("/index")
    private String index(){
        return "index";
    }

}
