package com.backend.IPv4.repository;

import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<ProgressEntity, Long> {
   
	ProgressEntity findByUserAndModuleNameAndSectionName(UserEntity user, String moduleName, String sectionName);
	List<ProgressEntity> findByUser(UserEntity user);
	List<ProgressEntity> findByUserUsername(String username);
}