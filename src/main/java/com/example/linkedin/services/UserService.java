package com.example.linkedin.services;

import com.example.linkedin.dto.CreateUserDTO;
import com.example.linkedin.model.RoleModel;
import com.example.linkedin.model.UserModel;
import com.example.linkedin.repository.RoleRepository;
import com.example.linkedin.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
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
    public ResponseEntity<List<UserModel>> listUsers(){
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Transactional
    public ResponseEntity<Void> deleteUserByUsername(String username){
          var user = userRepository.findByUsername(username);

          if(user.isPresent()){
              user.get().getRoles().clear();
              userRepository.save(user.get());
            userRepository.deleteByUsername(username);
          }
          else{
              throw  new UsernameNotFoundException("Username não encontrado");
          }
       return ResponseEntity.ok().build();
    }
}
