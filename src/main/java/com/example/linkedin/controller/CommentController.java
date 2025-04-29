package com.example.linkedin.controller;

import com.example.linkedin.dto.CommentsRequest;
import com.example.linkedin.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    private CommentsService service;

    @PostMapping("/posts/comentar/{id}")
    public ResponseEntity<Void> comentarNoPost(CommentsRequest commentsRequest,@PathVariable Long id){
        return service.createComments(commentsRequest, id);
    }
}
