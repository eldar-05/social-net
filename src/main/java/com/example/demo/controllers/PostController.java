package com.example.demo.controllers;

import com.example.demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }

    @PostMapping("/post")
    public String createPost(@RequestParam("text") String text,
                             @RequestParam("image") MultipartFile imageFile) throws IOException {
        postService.savePost(text, imageFile);
        return "redirect:/";
    }
}