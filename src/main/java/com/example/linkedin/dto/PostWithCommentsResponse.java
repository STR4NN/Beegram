package com.example.linkedin.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PostWithCommentsResponse {
    private Long id;
    private String conteudo;
    private LocalDateTime horarioDoPost;
    private String username;
    private List<String> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getHorarioDoPost() {
        return horarioDoPost;
    }

    public void setHorarioDoPost(LocalDateTime horarioDoPost) {
        this.horarioDoPost = horarioDoPost;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getters e Setters
}