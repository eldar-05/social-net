package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Base64;
import java.util.Comparator;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile/{login}")
    public String userProfile(@PathVariable String login, Model model) {
        User user = userService.findByLogin(login);
        if (user == null) {
            return "redirect:/";
        }

        if (user.getProfilePicture() != null) {
            model.addAttribute("profilePic", Base64.getEncoder().encodeToString(user.getProfilePicture()));
        }
        user.getPosts().forEach(post -> {
            if (post.getImage() != null) {
                post.setImageBase64(Base64.getEncoder().encodeToString(post.getImage()));
            }
        });
        
        user.getPosts().sort(Comparator.comparing(Post::getCreationDate).reversed());
        
        model.addAttribute("user", user);
        return "profile";
    }
}