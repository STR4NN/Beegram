package com.example.linkedin.services;

import com.example.linkedin.dto.CreateUserDTO;
import com.example.linkedin.dto.EditProfileDTO;
import com.example.linkedin.dto.PostsDTO;
import com.example.linkedin.dto.ShowProfileDTO;
import com.example.linkedin.model.PostModel;
import com.example.linkedin.model.RoleModel;
import com.example.linkedin.model.UserModel;
import com.example.linkedin.repository.PostRepository;
import com.example.linkedin.repository.RoleRepository;
import com.example.linkedin.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.CredentialException;
import java.net.PasswordAuthentication;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       PostRepository postRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Void> createNewUsers(CreateUserDTO dto) {

        var basicRole = roleRepository.findByName(RoleModel.Values.BASIC.name());
        var userFromDB = userRepository.findByUsername(dto.username());


            if (basicRole == null) {
                RoleModel roleBasicAdd = new RoleModel();
                roleBasicAdd.setName(RoleModel.Values.BASIC.name());
                basicRole = roleRepository.save(roleBasicAdd);
            }

            if (userFromDB.isPresent()) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            var user = new UserModel();
            user.setUsername(dto.username());
            user.setPassword(passwordEncoder.encode(dto.password()));
            user.setRoles(Set.of(basicRole));

            System.out.println("Username : " + dto.username());
            userRepository.save(user);

            return ResponseEntity.ok().build();
    }

    public ResponseEntity<List<UserModel>> listUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Transactional
    public ResponseEntity<Void> deleteUserByUsername(String username) {
        var user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            user.get().getRoles().clear();
            userRepository.save(user.get());
            userRepository.deleteByUsername(username);
        } else {
            throw new UsernameNotFoundException("Username não encontrado");
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> editProfile(String username, EditProfileDTO dto, @AuthenticationPrincipal Jwt jwt) throws CredentialException {
        UUID userId = UUID.fromString(jwt.getSubject());
        var user = userRepository.findById(userId).orElseThrow
                (() -> new RuntimeException("Usuario não encontrado"));

        if (Objects.equals(username, user.getUsername())) {
            user.setBio(dto.bio());
            user.setUrlPhoto(dto.urlPhoto());

            userRepository.save(user);
        } else {
            throw new RuntimeException("Este não é o seu perfil");
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<ShowProfileDTO> showProfile(String username, ShowProfileDTO dto, @AuthenticationPrincipal Jwt jwt) throws CredentialException {
        UUID userId = UUID.fromString(jwt.getSubject());
        var user = userRepository.findById(userId).orElseThrow
                (() -> new RuntimeException("Usuario não encontrado"));

        var userByUsername = userRepository.findByUsername(username);

        List<PostsDTO> posts = userByUsername.get()
                .getPosts().stream().map(post -> new PostsDTO(post.getConteudo(),post.getUserUsername(), post.getHorarioDoPost())).toList();

        int numberOfFollowings = userByUsername.get().getFollowing().size();
        int numberOfFollowers = userByUsername.get().getFollowers().size();

        return ResponseEntity.ok(new ShowProfileDTO(userByUsername.get().getUsername(), userByUsername.get().getBio()
        , userByUsername.get().getUrlPhoto(),numberOfFollowers,numberOfFollowings, posts));
    }

}
