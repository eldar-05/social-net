package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getUserPosts(User user) {
        return postRepository.findAllByUser(user);
    }

    public void deletePost(User currentUser, Post post) {
        if (currentUser.getRole().equals("ROLE_ADMIN") || post.getUser().getId().equals(currentUser.getId())) {
            postRepository.delete(post);
        }
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
