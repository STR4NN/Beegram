package com.example.linkedin.controller;

import com.example.linkedin.repository.UserRepository;
import com.example.linkedin.services.ProfileService;
import com.example.linkedin.services.TokenService;
import com.example.linkedin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileContrller {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @PostMapping("/follow/{username}")
    public ResponseEntity<String> followProfile(@PathVariable  String username,
                                                @AuthenticationPrincipal Jwt jwt) throws Exception {

      return profileService.followProfile(username, jwt);
    }
    @DeleteMapping("/unfollow/{username}")
    public ResponseEntity<String> unfollowProfile(@PathVariable  String username,
                                                @AuthenticationPrincipal Jwt jwt) throws Exception {

        return profileService.deixarDeSeguir(username, jwt);
    }

}
