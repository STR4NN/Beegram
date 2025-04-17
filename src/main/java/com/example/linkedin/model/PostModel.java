package com.example.linkedin.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_posts")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conteudo;

    @CreationTimestamp
    private Instant horarioDoPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

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

    public Instant getHorarioDoPost() {
        return horarioDoPost;
    }

    public void setHorarioDoPost(Instant horarioDoPost) {
        this.horarioDoPost = horarioDoPost;
    }
}
