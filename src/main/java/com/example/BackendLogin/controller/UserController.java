package com.example.BackendLogin.controller;
import com.example.BackendLogin.model.User;
import com.example.BackendLogin.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController

@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Save user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
//    @GetMapping("/auth/google")
//    public void redirectToGoogle(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/oauth2/authorization/google");
//    }
@GetMapping("/me")
public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
    if (principal == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
    }
    // Get email from OAuth2User attributes
    String email = (String) principal.getAttributes().get("email");
    String name = (String) principal.getAttributes().get("name");
    return ResponseEntity.ok(Map.of("email", email, "name", name));
}


    // Login - check email and password
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean authenticated = userService.authenticate(user.getEmail(), user.getPassword());
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}