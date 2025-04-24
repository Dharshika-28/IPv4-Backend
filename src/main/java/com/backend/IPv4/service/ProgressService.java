package com.backend.IPv4.service;

import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;
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

    public List<ProgressEntity> getProgressByUsername(String username) {
        return progressRepository.findByUserUsername(username);
    }
    
    public ProgressEntity saveOrUpdateProgress(ProgressEntity progress) {
    	if (progress.getUser() == null || progress.getUser().getUsername() == null) {
    	    throw new IllegalArgumentException("Sam get the bug");
    	}

        // 🛠️ Fetch managed UserEntity
        UserEntity user = userRepository.findByUsername(progress.getUser().getUsername());

        if (user == null) {
            throw new IllegalArgumentException("User not found: " + progress.getUser().getUsername());
        }

        progress.setUser(user); // ✅ Attach the fetched (managed) user

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

}
