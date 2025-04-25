package com.backend.IPv4.controller;

import java.time.LocalDateTime;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.IPv4.dto.LoginRequest;
import com.backend.IPv4.entity.AdminEntity;
import com.backend.IPv4.entity.LoginHistory;
import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;
import com.backend.IPv4.repository.AdminRepository;
import com.backend.IPv4.repository.LoginHistoryRepository;
import com.backend.IPv4.repository.ProgressRepository;
import com.backend.IPv4.repository.UserRepository;
import com.backend.IPv4.service.AdminService;
import com.backend.IPv4.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private AdminService adminService;
    @Autowired private AdminRepository adminRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProgressRepository progressRepository;
    @Autowired private LoginHistoryRepository loginHistoryRepository;

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

    // Login for User or Admin with login history save
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        System.out.println("[Login] Attempt for email: " + loginRequest.getEmail());

        Optional<UserEntity> user = userRepository.findByEmailAndPassword(
            loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            String username = user.get().getUsername();
            String email = user.get().getEmail();
            saveLoginHistory(username, request.getRemoteAddr(), "Unknown");

            Map<String, Object> response = new HashMap<>();
            response.put("role", "user");
            response.put("username", username);
            response.put("email", email);
            return ResponseEntity.ok(response);
        }

        Optional<AdminEntity> admin = adminRepository.findByEmailAndPassword(
            loginRequest.getEmail(), loginRequest.getPassword());

        if (admin.isPresent()) {
            String username = admin.get().getName();
            String email = admin.get().getEmail();
            saveLoginHistory(username, request.getRemoteAddr(), "Unknown");

            Map<String, Object> response = new HashMap<>();
            response.put("role", "admin");
            response.put("username", username);
            response.put("email", email);
            return ResponseEntity.ok(response);
        }

        System.out.println("[Login] Invalid credentials for email: " + loginRequest.getEmail());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // Fetch progress by username
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

    // Helper method to save login history
    private void saveLoginHistory(String username, String ip, String location) {
        LoginHistory history = new LoginHistory();
        history.setUsername(username);
        history.setTime(LocalDateTime.now().toString());
        history.setIp(ip);
        history.setLocation(location);
        loginHistoryRepository.save(history);
    }
}
