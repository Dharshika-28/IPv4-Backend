package com.backend.IPv4.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.IPv4.entity.ProgressEntity;
import com.backend.IPv4.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

	UserEntity findByUsername(String username);
	
	Optional<UserEntity> findByEmailAndPassword(String email, String password);


}
