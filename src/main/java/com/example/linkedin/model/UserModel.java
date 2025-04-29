package com.example.linkedin.model;

import com.example.linkedin.dto.CreateUserDTO;
import com.example.linkedin.dto.LoginRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "tb_users")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(length = 100)
    private String bio;

    private String urlPhoto;

    @ManyToMany
    @JoinTable(
            name = "user_following_tb",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private Set<UserModel> following;


    @ManyToMany(mappedBy = "following")
    private Set<UserModel> followers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")

    )
    private Set<RoleModel> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PostModel> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CommentsModel> comentario;


    public Set<UserModel> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserModel> followers) {
        this.followers = followers;
    }

    public Set<UserModel> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserModel> following) {
        this.following = following;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public Set<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostModel> posts) {
        this.posts = posts;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);

    }

}
