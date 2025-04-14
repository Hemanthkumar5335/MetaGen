package com.metagen.userservice.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metagen.userservice.model.Role;
import com.metagen.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User findByRole(Role role);
}
