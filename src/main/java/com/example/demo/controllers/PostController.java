package com.example.demo.controllers;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.services.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    // Главная страница (лента всех постов)
    @GetMapping("/")
    public String showHome(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Post> posts = postService.getAllPosts();
        model.addAttribute("user", user); // Чтобы в index.html отобразить имя пользователя, например
        model.addAttribute("posts", posts);
        return "index";
    }

    // Создание поста
    @PostMapping("/post")
    public String addPost(@RequestParam("text") String text,
                          @RequestParam("image") MultipartFile image,
                          HttpSession session) throws IOException {

        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Post post = new Post();
        post.setContent(text);
        post.setUser(user);

        if (!image.isEmpty()) {
            byte[] bytes = image.getBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            post.setImageBase64(base64);
        }

        postService.savePost(post);
        return "redirect:/";
    }
}

