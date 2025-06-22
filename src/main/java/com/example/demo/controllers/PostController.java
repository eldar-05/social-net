package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.PostService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("currentUserLogin", userDetails.getUsername());
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }

    @PostMapping("/post/create")
    public String createPost(@RequestParam String text,
                             @RequestParam("image") MultipartFile imageFile,
                             @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        User currentUser = userService.findByLogin(userDetails.getUsername());
        postService.savePost(text, imageFile, currentUser);
        return "redirect:/";
    }

    @PostMapping("/post/delete/{id}")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByLogin(userDetails.getUsername());
        postService.deletePost(id, currentUser);
        return "redirect:/";
    }
}