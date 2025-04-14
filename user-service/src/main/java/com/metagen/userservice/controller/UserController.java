package com.metagen.userservice.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metagen.userservice.model.User;
import com.metagen.userservice.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/users/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User saved = userService.registerUser(user);
        return ResponseEntity.ok(Map.of("user_id", saved.getId(), "status", "success"));
    }

    @PostMapping("/auth/token")
    public ResponseEntity<?> getToken(@RequestBody Map<String, String> body) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.get("username"), body.get("password")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(Map.of("access_token", userService.generateToken(auth), "expires_in", 3600));
    }

    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello USER!";
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello ADMIN!";
    }
}

