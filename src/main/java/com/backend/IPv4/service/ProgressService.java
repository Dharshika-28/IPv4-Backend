package com.backend.IPv4.service;

import com.backend.IPv4.entity.FinalQuizRequest;
import com.backend.IPv4.entity.FinalQuizResult;
import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;
import com.backend.IPv4.repository.FinalQuizRepository;
import com.backend.IPv4.repository.ProgressRepository;
import com.backend.IPv4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private  FinalQuizRepository finalQuizRepository;


    public List<ProgressEntity> getProgressByUsername(String username) {
        return progressRepository.findByUserUsername(username);
    }
    
    public ProgressEntity saveOrUpdateProgress(ProgressEntity progress) {
        if (progress == null) {
            throw new IllegalArgumentException("Progress must not be null");
        }
        if (progress.getUser() == null || progress.getUser().getUsername() == null) {
            throw new IllegalArgumentException("Progress or user information is missing");
        }

        UserEntity user = userRepository.findByUsername(progress.getUser().getUsername());
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + progress.getUser().getUsername());
        }

        progress.setUser(user);

        ProgressEntity existing = progressRepository.findByUserAndModuleNameAndSectionName(
            user, progress.getModuleName(), progress.getSectionName());

        if (existing != null) {
            existing.setCompleted(progress.isCompleted());
            existing.setProgressPercentage(progress.getProgressPercentage());
            return progressRepository.save(existing);
        } else {
            return progressRepository.save(progress);
        }
    }

    public void saveFinalQuizResult(FinalQuizRequest request) {
        String username = request.getUsername();
        int score = request.getScore();
        boolean completed = request.isCompleted();

        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found with username: " + username);
        }

        FinalQuizResult finalQuizResult = new FinalQuizResult(score, completed, user);
        finalQuizRepository.save(finalQuizResult);
    }


}
