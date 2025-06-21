package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void savePost(String text, MultipartFile imageFile) throws IOException {
        Post post = new Post();
        post.setText(text);
        post.setImage(imageFile.getBytes());
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        // Для каждого поста конвертируем изображение в Base64 и сохраняем в новое поле
        posts.forEach(post -> {
            if (post.getImage() != null) {
                post.setImageBase64(Base64.getEncoder().encodeToString(post.getImage()));
            }
        });
        return posts;
    }
}