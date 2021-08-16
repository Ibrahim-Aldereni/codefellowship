package com.example.demo.controllers;

import com.example.demo.modals.ApplicationUser;
import com.example.demo.modals.Post;
import com.example.demo.repositories.ApplicationUserRepository;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class UserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    PostRepository postRepository;

    @GetMapping("/myprofile")
    public String getUserProfile(Principal p, Model model){
        try{
            // for the header
            model.addAttribute("userInfo",p.getName());
            // for the body
            model.addAttribute("allUserInfo",applicationUserRepository.findByUsername(p.getName()));
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
            model.addAttribute("allUserInfo",new ApplicationUser());
        }
        return "user.html";
    }

    @PostMapping("/myprofile")
    public RedirectView addPost(Principal p,@RequestParam String body){
        Post post = new Post(body,applicationUserRepository.findByUsername(p.getName()));
        postRepository.save(post);
        return new RedirectView("/myprofile");
    }
}
