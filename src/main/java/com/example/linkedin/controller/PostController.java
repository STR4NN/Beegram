package com.example.linkedin.controller;

import com.example.linkedin.dto.PostsDTO;
import com.example.linkedin.model.PostModel;
import com.example.linkedin.model.UserModel;
import com.example.linkedin.repository.PostRepository;
import com.example.linkedin.services.PostsService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostsService postsService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Void> createPosts(@RequestBody PostsDTO dto, @AuthenticationPrincipal Jwt jwt){
//       var user = new UserModel();
//       dto.username(user.getUsername());
        postsService.createPosts(dto, jwt);
       return  ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PostModel>> listUsers(){
        var posts =  postRepository.findAll();
        return ResponseEntity.ok(posts);
    }
}
