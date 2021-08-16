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
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
        }
        return "home.html";
    }
}
