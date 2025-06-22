package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    private LocalDateTime creationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") 
    private User user;

    @Transient
    private String imageBase64;
    
    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }


    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
    public String getImageBase64() { return imageBase64; }
    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}