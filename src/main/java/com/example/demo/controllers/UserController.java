package com.example.demo.controllers;

import com.example.demo.modals.ApplicationUser;
import com.example.demo.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/user/{id}")
    public String getUser(Principal p, Model model, @PathVariable Integer id){

        try{
            // for the header
            model.addAttribute("userInfo",p.getName());
            model.addAttribute("hideWhenLogin","display: none");
            model.addAttribute("hideWhenLogout","display: auto");

            // for the body
            model.addAttribute("allUserInfo",applicationUserRepository.findById(id).get());
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
            model.addAttribute("hideWhenLogin","display: auto");
            model.addAttribute("hideWhenLogout","display: none");
            model.addAttribute("allUserInfo",new ApplicationUser());
        }
        return "user.html";
    }


}
