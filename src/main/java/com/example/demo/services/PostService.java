package com.example.demo.services;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAllWithUser(); 
        
        posts.forEach(post -> {
            if (post.getImage() != null) {
                post.setImageBase64(Base64.getEncoder().encodeToString(post.getImage()));
            }
        });
        return posts;
    }
    
    public void savePost(String text, MultipartFile imageFile, User user) throws IOException {
        Post post = new Post();
        post.setText(text);
        if (imageFile != null && !imageFile.isEmpty()) {
            post.setImage(imageFile.getBytes());
        }
        post.setUser(user);
        postRepository.save(post);
    }

    public void deletePost(Long postId, User currentUser) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null && post.getUser().getId().equals(currentUser.getId())) {
            postRepository.deleteById(postId);
        } else {
            throw new SecurityException("You do not have permission to delete this post.");
        }
    }
}