package com.backend.IPv4.controller;

import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")  // Adjust for your frontend URL in production
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    // Get all progress for a user
    @GetMapping("/{username}")
    public List<ProgressEntity> getProgress(@PathVariable String username) {
        return progressService.getProgressByUsername(username);
    }

    // Save or update progress (POST body expects JSON of ProgressEntity)
    @PostMapping("/save")
    public ProgressEntity saveProgress(@RequestBody ProgressEntity progress) {
        return progressService.saveOrUpdateProgress(progress);
    }
}
