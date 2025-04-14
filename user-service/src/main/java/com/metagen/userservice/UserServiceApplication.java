package com.metagen.userservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.metagen.userservice.model.Role;
import com.metagen.userservice.model.User;
import com.metagen.userservice.repository.UserRepository;
@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ Use interface, not BCrypt directly

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (!repo.findByUsername("admin").isPresent()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setUsername("admin");
            user.setRole(Role.ROLE_ADMIN);
            user.setPassword(passwordEncoder.encode("admin")); // ✅ Encode using injected bean

            repo.save(user);
            System.out.println("Admin user created successfully.");
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}
