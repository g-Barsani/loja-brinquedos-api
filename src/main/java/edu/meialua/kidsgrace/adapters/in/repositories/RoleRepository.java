package edu.meialua.kidsgrace.adapters.in.repositories;

import edu.meialua.kidsgrace.adapters.in.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}
