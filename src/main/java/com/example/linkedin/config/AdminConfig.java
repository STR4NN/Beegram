package com.example.linkedin.config;

import com.example.linkedin.dto.CreateUserDTO;
import com.example.linkedin.model.RoleModel;
import com.example.linkedin.model.UserModel;
import com.example.linkedin.repository.RoleRepository;
import com.example.linkedin.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.Role;
import java.util.Set;

@Configuration
public class AdminConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminConfig(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByUsername("ADMIN");
        var roleAdmin = roleRepository.findByName(RoleModel.Values.ADMIN.name());

        if(roleAdmin == null){
            RoleModel newRoleAdmin = new RoleModel();
            newRoleAdmin.setName(RoleModel.Values.ADMIN.name());
            roleAdmin = roleRepository.save(newRoleAdmin);
        }

        RoleModel finalRoleAdmin = roleAdmin;

        userAdmin.ifPresentOrElse((user) -> System.out.println("ADMIN JA EXISTE"), () -> {
            var user = new UserModel();
            user.setUsername("ADMIN");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRoles(Set.of(finalRoleAdmin));
            userRepository.save(user);
           }
        );
    }
}
