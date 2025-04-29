package com.example.linkedin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_posts")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String conteudo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime horarioDoPost = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    @ManyToOne
    @JsonBackReference //
    @JoinColumn(name = "user_id")
    private UserModel user;

    private int likes;

    @OneToMany(mappedBy = "postModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CommentsModel> comentarios = new ArrayList<>();

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserModel user;


    public List<CommentsModel> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<CommentsModel> comentarios) {
        this.comentarios = comentarios;
    }

    private String userUsername;

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

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

    public LocalDateTime getHorarioDoPost() {
        return horarioDoPost;
    }

    public void setHorarioDoPost(LocalDateTime horarioDoPost) {
        this.horarioDoPost = horarioDoPost;
    }
}
