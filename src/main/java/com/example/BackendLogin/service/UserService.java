package com.example.BackendLogin.service;

import com.example.BackendLogin.UserValidator;
import com.example.BackendLogin.model.User;
import com.example.BackendLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // Validate before saving
        if (!UserValidator.isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email. Must end with .com or .in");
        }
        if (!UserValidator.isValidPassword(user.getPassword())) {
            throw new IllegalArgumentException("Invalid password. Must be at least 8 characters and include a special character");
        }

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}
