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
import java.util.ArrayList;
import java.util.Set;

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

    /////////////////////////////////// all users and feed and follow //////////////////////////////
    @GetMapping("/allUsers")
    public String getAllUsers(Principal p,Model model){
        try{
            // for the header
            model.addAttribute("userInfo",p.getName());
            // for the body
            model.addAttribute("Allusers",applicationUserRepository.findAll());
            // to hide the follow button
            ApplicationUser me = applicationUserRepository.findByUsername(p.getName());
            model.addAttribute("whoIFollow",me.getFollowers());
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
        }
        return "allUsers.html";
    }

    @PostMapping("/follow")
    public RedirectView addFollow(Principal p,@RequestParam int id){
        ApplicationUser me = applicationUserRepository.findByUsername(p.getName());
        ApplicationUser toFollow = applicationUserRepository.findById(id).get();
        me.getFollowers().add(toFollow);

        applicationUserRepository.save(me);
        return new RedirectView("/feed");
    }

    @GetMapping("/feed")
    public String getFollowingInfo(Principal p, Model model){
        try{
            // for the header
            model.addAttribute("userInfo",p.getName());
            // for the body
            ApplicationUser me = applicationUserRepository.findByUsername(p.getName());
            Set<ApplicationUser> whoIFollow = me.getFollowers();
            model.addAttribute("Allfollowing",whoIFollow);
        }catch (NullPointerException e){
            model.addAttribute("userInfo","");
        }
        return "feed.html";
    }
}
