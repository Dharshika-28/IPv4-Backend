package com.backend.IPv4.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.IPv4.entity.FinalQuizResult;
import com.backend.IPv4.entity.UserEntity;

@Repository
public interface FinalQuizRepository extends JpaRepository<FinalQuizResult, Long> {
    Optional<FinalQuizResult> findByUser(UserEntity user);
}