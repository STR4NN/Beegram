package com.example.linkedin.dto;

import com.example.linkedin.model.CommentsModel;

import java.time.LocalDateTime;
import java.util.List;

public record PostsDTO(String conteudo, String username, LocalDateTime postadoQuando) {
}
