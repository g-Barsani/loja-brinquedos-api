package edu.meialua.kidsgrace.adapters.in.repositories;

import edu.meialua.kidsgrace.adapters.in.Toy;
import edu.meialua.kidsgrace.adapters.in.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        List<User> findAll();

        Optional<User> findById(Long id);

        List<User> findByName(String name);
        Optional<User> findAllByEmail(String email);

        // Método para buscar usuário por email e senha
        @Query ("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
        Optional<User> findByEmailAndPassword(String email, String password);

        @Override
        void deleteById(Long id);

}
