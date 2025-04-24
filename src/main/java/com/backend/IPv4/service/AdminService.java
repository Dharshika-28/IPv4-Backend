package com.backend.IPv4.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.IPv4.entity.AdminEntity;
import com.backend.IPv4.entity.UserEntity;
import com.backend.IPv4.repository.AdminRepository;
import com.backend.IPv4.repository.UserRepository;

@Service
public class AdminService {
	@Autowired
    private AdminRepository adminRepository;

    public AdminEntity register(AdminEntity admin) {
        return adminRepository.save(admin);
    }

    public Optional<AdminEntity> login(String email, String password) {
        return adminRepository.findByEmail(email)
                .filter(admin -> admin.getPassword().equals(password));
    }

}
