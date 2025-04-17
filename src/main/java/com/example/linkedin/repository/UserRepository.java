package com.example.linkedin.repository;

import com.example.linkedin.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String username);

    void deleteByUsername(String username);
}
