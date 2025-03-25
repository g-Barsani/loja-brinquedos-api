package edu.meialua.kidsgrace.adapters.out.controller;

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

    @GetMapping("/findAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(users);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<User>> getUserByName(@PathVariable("name") String name) {
        List<User> users = userRepository.findByName(name);
        return users.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(users);
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

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        return user.map(u -> ResponseEntity.ok("AUTENTICAÇÃO BEM-SUCEDIDA"))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("CREDENCIAIS INVÁLIDAS"));
    }
}
