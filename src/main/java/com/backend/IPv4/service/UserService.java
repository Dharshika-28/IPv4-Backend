package com.backend.IPv4.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.IPv4.entity.UserEntity;
import com.backend.IPv4.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public UserEntity register(UserEntity user) {
        return userRepository.save(user);
    }

    // User login with email and password
    public Optional<UserEntity> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    // Get user by email (returning user details)
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Get only the username by email (for the new endpoint)
    public Optional<String> getUsernameByEmail(String email) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        return user.map(UserEntity::getUsername);
    }
}
