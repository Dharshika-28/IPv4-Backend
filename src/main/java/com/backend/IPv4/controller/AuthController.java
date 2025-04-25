package com.backend.IPv4.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.IPv4.dto.LoginRequest;
import com.backend.IPv4.entity.AdminEntity;
import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;
import com.backend.IPv4.repository.AdminRepository;
import com.backend.IPv4.repository.ProgressRepository;
import com.backend.IPv4.repository.UserRepository;
import com.backend.IPv4.service.AdminService;
import com.backend.IPv4.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired  private UserService userService;
    @Autowired private AdminService adminService;
    @Autowired private AdminRepository adminRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProgressRepository progressRepository;

    // User registration
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserEntity user) {
        System.out.println("[Register] User: " + user);
        return ResponseEntity.ok(userService.register(user));
    }

    // Admin registration
    @PostMapping("/adminsignup")
    public ResponseEntity<?> adminregister(@RequestBody AdminEntity admin) {
        System.out.println("[Register] Admin: " + admin);
        return ResponseEntity.ok(adminService.register(admin));
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("[Login] Attempt for email: " + loginRequest.getEmail());

        Optional<UserEntity> user = userRepository.findByEmailAndPassword(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        );

        if (user.isPresent()) {
            System.out.println("[Login] User found: " + user.get().getUsername());
            Map<String, Object> response = new HashMap<>();
            response.put("role", "user");
            response.put("username", user.get().getUsername());
            response.put("email", user.get().getEmail());
            return ResponseEntity.ok(response);
        }

        Optional<AdminEntity> admin = adminRepository.findByEmailAndPassword(
            loginRequest.getEmail(),
            loginRequest.getPassword()
        );

        if (admin.isPresent()) {
            System.out.println("[Login] Admin found: " + admin.get().getName());
            Map<String, Object> response = new HashMap<>();
            response.put("role", "admin");
            response.put("username", admin.get().getName());
            response.put("email", admin.get().getEmail());
            return ResponseEntity.ok(response);
        }

        System.out.println("[Login] Invalid credentials for email: " + loginRequest.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // Fetch username by email - GET method
    @GetMapping("/login/{username}")
    public ResponseEntity<?> getProgressByUsername(@PathVariable String username) {
        UserEntity user = userRepository.findByUsername(username.trim());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("message", "User not found"));
        }

        List<ProgressEntity> progressList = progressRepository.findByUser(user);

        return ResponseEntity.ok(progressList);
    }




}
