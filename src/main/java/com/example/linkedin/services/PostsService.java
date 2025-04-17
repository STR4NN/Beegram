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
import org.springframework.stereotype.Service;

@Service
public class PostsService {

    @PersistenceContext // Serve para injetar uma instancia do EntityManager
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    public ResponseEntity<PostModel> createPosts(PostsDTO dto) {


        if(dto.user_id() == null){
            throw new RuntimeException("Id invalido, ou não existente");
        }

        UserModel userModel = entityManager.find(UserModel.class, dto.user_id());

        if(userModel == null){
            throw  new RuntimeException("Usuario não encontrado");
        }

        PostModel post = new PostModel();
        post.setConteudo(dto.conteudo());
        post.setUser(userModel);

        entityManager.persist(post); // Persist == Save
        return ResponseEntity.ok(post);
    }

}
