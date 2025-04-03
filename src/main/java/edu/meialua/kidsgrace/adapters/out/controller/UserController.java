package edu.meialua.kidsgrace.adapters.out.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.meialua.kidsgrace.adapters.in.Role;
import edu.meialua.kidsgrace.adapters.in.User;
import edu.meialua.kidsgrace.adapters.in.repositories.RoleRepository;
import edu.meialua.kidsgrace.adapters.in.repositories.UserRepository;
import edu.meialua.kidsgrace.model.RegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/findAll")
    public ResponseEntity<String> getAllUsers() throws JsonProcessingException {
        List<User> users = userRepository.findAll();
//        return users.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(users);

        if(!users.isEmpty()) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(users));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USUÁRIOS NÃO ENCONTRADOS");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<String> getUserById(@PathVariable("id") Long id) throws JsonProcessingException{
        Optional<User> user = userRepository.findById(id);

        if (!user.isEmpty()){
            return ResponseEntity.ok(objectMapper.writeValueAsString(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USUÁRIO NÃO ENCONTRADO");

    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<String> getUserByName(@PathVariable("name") String name) throws JsonProcessingException{
        List<User> users = userRepository.findByName(name);

        if (!users.isEmpty()){
            return ResponseEntity.ok(objectMapper.writeValueAsString(users));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("USUÁRIOS NÃO ENCONTRADOS");
    }

    @PostMapping("/register")
    public ResponseEntity<String>createUser(@RequestBody RegisterDto registerDto) {

        if (userRepository.existsByUserName(registerDto.getUsername())){
            return new ResponseEntity<>("Username já existe!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(registerDto.getUsername());
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());

        Role roles = roleRepository.findByName("USER").get();

        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registrado com sucesso", HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("USUÁRIO DELETADO COM SUCESSO");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USUÁRIO NÃO ENCONTRADO");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
            return ResponseEntity.ok("USUÁRIO ATUALIZADO COM SUCESSO");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("USUÁRIO NÃO ENCONTRADO");
    }

//    @GetMapping("/authenticate")
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User logado com sucesso", HttpStatus.OK);
    }
}
