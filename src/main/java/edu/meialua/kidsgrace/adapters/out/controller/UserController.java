package edu.meialua.kidsgrace.adapters.out.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.meialua.kidsgrace.adapters.in.User;
import edu.meialua.kidsgrace.adapters.in.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

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

    @PostMapping("/insert")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("USUÁRIO CADASTRADO COM SUCESSO");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERRO AO CADASTRAR USUÁRIO: " + e.getMessage());
        }
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

    @GetMapping("/authenticate")
//    @PostMapping("/authenticate"
    public ResponseEntity<String> authenticateUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.map(u -> ResponseEntity.ok("AUTENTICAÇÃO BEM-SUCEDIDA"))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CREDENCIAIS INVÁLIDAS"));
    }
}
