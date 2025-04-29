package com.example.linkedin.services;

import com.example.linkedin.dto.CommentsRequest;
import com.example.linkedin.model.CommentsModel;
import com.example.linkedin.repository.CommentsRepository;
import com.example.linkedin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<Void> createComments(CommentsRequest commentsRequest, Long id){

        var post = postRepository.findById(id);

        CommentsModel comentario = new CommentsModel();

        comentario.setContent(commentsRequest.content());
        comentario.setPostModel(post.get());

        commentsRepository.save(comentario);
        return ResponseEntity.ok().build();
    }

}
