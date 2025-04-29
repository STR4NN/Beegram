package com.example.linkedin.repository;

import com.example.linkedin.model.CommentsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<CommentsModel, Long> {
}
