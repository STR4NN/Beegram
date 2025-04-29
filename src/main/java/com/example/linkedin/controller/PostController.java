package com.example.linkedin.controller;

import com.example.linkedin.dto.PostWithCommentsResponse;
import com.example.linkedin.dto.PostsDTO;
import com.example.linkedin.model.CommentsModel;
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

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostsService postsService;


    @Autowired
    PostRepository postRepository;

    @PostMapping
    public ResponseEntity<PostModel> createPosts(@RequestBody PostsDTO dto, @AuthenticationPrincipal Jwt jwt, PostModel postModel) {
        postsService.createPosts(dto, jwt, postModel);
        return ResponseEntity.ok(postModel);
    }

    @GetMapping
    public ResponseEntity<List<PostWithCommentsResponse>> listPosts() {

        List<PostModel> posts = postRepository.findAll(); // buscar todos os posts

        List<PostWithCommentsResponse> response = posts.stream().map(post -> {

            PostWithCommentsResponse dto = new PostWithCommentsResponse();
            dto.setId(post.getId());
            dto.setConteudo(post.getConteudo());
            dto.setHorarioDoPost(post.getHorarioDoPost());
            dto.setUsername(post.getUserUsername());
            dto.setComments(
                    post.getComentarios().stream()
                            .map(CommentsModel::getContent)
                            .toList()
            );
            return dto;
        }).toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) throws AuthenticationException {
        return postsService.deletePosts(id, jwt);
    }
}
