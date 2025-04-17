package com.example.linkedin.services;

import com.example.linkedin.dto.LoginRequest;
import com.example.linkedin.dto.LoginResponse;
import com.example.linkedin.model.RoleModel;
import com.example.linkedin.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    public TokenService(UserRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        JwtEncoder jwtEncoder
    ) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<LoginResponse> generateToken(LoginRequest loginRequest){

        var user = userRepository.findByUsername(loginRequest.username());

        if( user.isEmpty() || !user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder )){
            throw new BadCredentialsException("user or password is invalid!");
        }


        Instant now = Instant.now();
        long expiresIn = 300L;

        var scope = user.get().getRoles()
                .stream()
                .map(RoleModel::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("linkedin")
                .subject(user.get().getId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .claim("scope", scope)
                .build();


        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }
//    public  validateToken(){
//
//    }
}
