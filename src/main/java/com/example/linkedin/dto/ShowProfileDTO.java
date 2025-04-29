package com.example.linkedin.dto;

import java.util.List;

public record ShowProfileDTO(String username,
                             String bio,
                             String urlPhoto,
                             Integer followers,
                             Integer following,
                             List<PostsDTO> posts) {
}
