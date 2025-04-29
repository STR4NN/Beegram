package com.example.linkedin.controller;

import com.example.linkedin.dto.*;
import com.example.linkedin.model.UserModel;
import com.example.linkedin.repository.UserRepository;
import com.example.linkedin.services.TokenService;
import com.example.linkedin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/create")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> createUsers(@RequestBody CreateUserDTO dto){
        return userService.createNewUsers(dto);

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUsers(@RequestBody LoginRequest loginRequest){
        return tokenService.generateToken(loginRequest);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserModel>> listUsers(){
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserModel>> deleteUser(@PathVariable String username){
        userService.deleteUserByUsername(username);
      var users = userRepository.findAll();
      return ResponseEntity.ok(users);

    }
    @PostMapping("/profile/edit/{username}")
    public ResponseEntity<Void> editProfile( @PathVariable String username,
                                            @RequestBody EditProfileDTO dto,
                                            @AuthenticationPrincipal Jwt jwt) throws CredentialException {
        userService.editProfile(username, dto, jwt);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/profile/{username}")
    public ResponseEntity<ShowProfileDTO> showProfile(@PathVariable String username,
                                                      ShowProfileDTO dto,
                                                      @AuthenticationPrincipal Jwt jwt) throws CredentialException {
       return userService.showProfile(username, dto, jwt);


    }
}
