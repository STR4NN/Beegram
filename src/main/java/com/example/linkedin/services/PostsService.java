package com.example.linkedin.services;

import com.example.linkedin.dto.CommentsRequest;
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

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.UUID;

@Service
public class PostsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public ResponseEntity<PostModel> createPosts(PostsDTO dto,
                                                 @AuthenticationPrincipal Jwt jwt, PostModel post) {
        UUID userId = UUID.fromString(jwt.getSubject());
        var user = userRepository.findById(userId).orElseThrow
                (() -> new RuntimeException("Usuario não encontrado"));


        post.setConteudo(dto.conteudo());
        post.setHorarioDoPost(dto.postadoQuando());
        post.setUserUsername(user.getUsername());
        post.setUser(user);

        postRepository.save(post);
        // Persist == Save
        return ResponseEntity.ok(post);
    }

    public ResponseEntity<String> deletePosts(Long id, @AuthenticationPrincipal Jwt jwt) throws AuthenticationException {

        UUID userId = UUID.fromString(jwt.getSubject());
        var user = userRepository.findById(userId).orElseThrow // Usuario Logado
                (() -> new RuntimeException("Usuario não encontrado"));

        PostModel post = new PostModel();
        var postFeito = postRepository.findById(id);

        System.out.println(postFeito.get().getUserUsername());
        System.out.println(user.getUsername());

        if (user == postFeito.get().getUser()) {
            postRepository.deleteById(id);
            return ResponseEntity.ok().body("Post deletado com sucesso!.");
        }
        return ResponseEntity.badRequest().body("Você não é o dono do post.");


    }

}
