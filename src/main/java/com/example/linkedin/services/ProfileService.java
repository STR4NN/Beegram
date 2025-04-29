package com.example.linkedin.services;

import com.example.linkedin.model.UserModel;
import com.example.linkedin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.naming.InvalidNameException;
import java.util.UUID;

@Service
public class ProfileService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<String> followProfile(String username,
                                                @AuthenticationPrincipal Jwt jwt) throws Exception {
        UUID userID = UUID.fromString(jwt.getSubject());
        var user = userRepository.findById(userID); // Usuario que vai fazer a requisição

        var userFollowed = userRepository.findByUsername(username);

        if (userFollowed.isEmpty()) {
            throw new Exception("O Usuario não existe");
        }

        System.out.println(userFollowed.get().getFollowers().size());
        var follower = user.get();
        var followed = userFollowed.get();

        if (user.get().getId() == userFollowed.get().getId()) {
            throw new InvalidNameException("Você não pode seguir a si mesmo!");
        }

        follower.getFollowers().add(user.get());
        follower.getFollowing().add(followed);


        userRepository.save(followed);
        userRepository.save(follower);

        return ResponseEntity.ok().body("Você está seguindo: " + username);

    }
    public ResponseEntity<String> deixarDeSeguir(String username,
                                                 @AuthenticationPrincipal Jwt jwt){

        UUID userId = UUID.fromString(jwt.getSubject());
        var user = userRepository.findById(userId);

        var userFollowed = userRepository.findByUsername(username);

        if(userFollowed.isEmpty()){
            throw new UsernameNotFoundException("Não existe usuario com esse nome");
        }

        // user -> userFollowed ( url )
        var follower = user.get();
        var followed = userFollowed.get();
        if(follower.getFollowing().contains(userFollowed.get())){
            follower.getFollowers().remove(user.get());
            follower.getFollowing().remove(followed);

            userRepository.save(followed);
            userRepository.save(follower);

            return ResponseEntity.ok().body("Você deixou de seguir: " + followed.getUsername());
        }else{
            return ResponseEntity.badRequest().body("Você não segue esse usuario.");
        }




    }


}
