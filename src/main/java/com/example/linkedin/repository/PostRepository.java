package com.example.linkedin.repository;

import com.example.linkedin.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostModel, Long> {
}
