package com.example.demo.controllers;
import com.example.demo.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getHome(Principal p, Model model){
        try{
            model.addAttribute("userInfo",p.getName());
            model.addAttribute("hideWhenLogin","display: none");
            model.addAttribute("hideWhenLogout","display: auto");

            // get user id
            model.addAttribute("userId",applicationUserRepository.findByUsername(p.getName()).getId());
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
            model.addAttribute("hideWhenLogin","display: auto");
            model.addAttribute("hideWhenLogout","display: none");
        }
        return "home.html";
    }
}
