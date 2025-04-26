package com.backend.IPv4.controller;

import com.backend.IPv4.entity.FinalQuizRequest;
import com.backend.IPv4.entity.FinalQuizResult;
import com.backend.IPv4.entity.LoginHistory;
import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;
import com.backend.IPv4.repository.FinalQuizRepository;
import com.backend.IPv4.repository.LoginHistoryRepository;
import com.backend.IPv4.repository.UserRepository;
import com.backend.IPv4.service.ProgressService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")  // Adjust for your frontend URL in production
public class ProgressController {

    @Autowired private ProgressService progressService;    
    @Autowired private UserRepository userRepository;
    @Autowired private LoginHistoryRepository loginHistoryRepository;
    @Autowired private FinalQuizRepository finalQuizRepository;
    

    // Get all progress for a user
    @GetMapping("/All")
    public List<Map<String, Object>> getAllUsersWithProgress() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream().map(user -> {
            Map<String, Object> userData = new HashMap<>();
            userData.put("name", user.getUsername());
            userData.put("email", user.getEmail());
            List<LoginHistory> logins = loginHistoryRepository.findByUsername(user.getUsername());
            if (logins == null) logins = new ArrayList<>();
            userData.put("loginHistory", logins);
            List<ProgressEntity> progress = progressService.getProgressByUsername(user.getUsername());
            if (progress == null) progress = new ArrayList<>();
            userData.put("courseProgress", progress);
            return userData;
        }).collect(Collectors.toList());
    }
    
    
    @GetMapping("/details/{email}")
    public ResponseEntity<Map<String, Object>> getUserProgressByEmail(@PathVariable String email) {
        // Find user by email
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        // Prepare response data map
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getUsername());
        userData.put("email", user.getEmail());

        // Fetch login history by username (not email)
        List<LoginHistory> logins = loginHistoryRepository.findByUsername(user.getUsername());
        if (logins == null) logins = Collections.emptyList();
        userData.put("loginHistory", logins);

        // Fetch course progress by username
        List<ProgressEntity> progress = progressService.getProgressByUsername(user.getUsername());
        if (progress == null) progress = Collections.emptyList();
        userData.put("courseProgress", progress);

        return ResponseEntity.ok(userData);
    }


    
    // Save or update progress (POST body expects JSON of ProgressEntity)
    @PostMapping("/save")
    public ProgressEntity saveProgress(@RequestBody ProgressEntity progress) {
        return progressService.saveOrUpdateProgress(progress);
    }
    
    
    
    public void FinalQuizController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/final-quiz")
    public ResponseEntity<?> saveFinalQuizResult(@RequestBody FinalQuizRequest request) {
        progressService.saveFinalQuizResult(request);
        return ResponseEntity.ok("Final quiz result saved successfully");
    }
    
    @GetMapping("/final-quiz/score/{email}")
    public ResponseEntity<Integer> getFinalQuizScoreByEmail(@PathVariable String email) {
        System.out.println("Fetching final quiz score for email: " + email);
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("User not found with email: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return finalQuizRepository.findByUser(user)
                .map(result -> ResponseEntity.ok(result.getScore()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}
