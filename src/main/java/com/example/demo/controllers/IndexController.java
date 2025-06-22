package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Post> posts = postService.getUserPosts(user);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("newPost", new Post());
        return "index"; // вернёт index.html
    }

    @PostMapping("/add-post")
    public String addPost(@ModelAttribute("newPost") Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        post.setUser(user);
        postService.savePost(post);
        return "redirect:/";
    }
}
