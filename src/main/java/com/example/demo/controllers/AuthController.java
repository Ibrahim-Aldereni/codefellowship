package com.example.demo.controllers;

import com.example.demo.modals.ApplicationUser;
import com.example.demo.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class AuthController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/signup")
    public String getSignUpPage(Principal p, Model model) {
        try{
            model.addAttribute("user", new ApplicationUser());
            model.addAttribute("userInfo",p.getName());
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
        }
        return "signup.html";
    }

    @PostMapping("/signup")
    public RedirectView addUser(@ModelAttribute ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        return new RedirectView("/login");
    }

    @GetMapping("/login")
    public String getLoginPage(Principal p, Model model){
        try{
            model.addAttribute("userInfo",p.getName());
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
        }
        return "login.html";
    }

}
