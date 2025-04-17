package com.example.linkedin.dto;

import java.time.Instant;
import java.util.UUID;

public record PostsDTO(String conteudo, UUID user_id) {
}
