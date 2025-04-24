package com.backend.IPv4.repository;

import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressRepository extends JpaRepository<ProgressEntity, Long> {
    List<ProgressEntity> findByUserUsername(String username);
    ProgressEntity findByUserAndModuleNameAndSectionName(UserEntity user, String moduleName, String sectionName);
}
