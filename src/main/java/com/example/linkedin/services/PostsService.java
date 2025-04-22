package com.example.linkedin.services;

import com.example.linkedin.dto.PostsDTO;
import com.example.linkedin.model.PostModel;
import com.example.linkedin.model.UserModel;
import com.example.linkedin.repository.PostRepository;
import com.example.linkedin.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostsService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public ResponseEntity<PostModel> createPosts(PostsDTO dto,
                                                 @AuthenticationPrincipal Jwt jwt) {
      UUID userId = UUID.fromString(jwt.getSubject());
      var user = userRepository.findById(userId).orElseThrow
              (() -> new RuntimeException("Usuario não encontrado"));

        PostModel post = new PostModel();
        post.setConteudo(dto.conteudo());
        post.setUserUsername(user.getUsername());

        postRepository.save(post);
        // Persist == Save
        return ResponseEntity.ok(post);
    }

}
