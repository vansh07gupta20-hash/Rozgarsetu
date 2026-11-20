package com.rozgarsetu.service;

import com.rozgarsetu.entity.Admin;
import com.rozgarsetu.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }
    public Optional<Admin> findAdminById(Integer adminId){
        return adminRepository.findById(adminId);
    }
    public Optional<Admin> findAdminByEmail(String email){
        return adminRepository.findByEmail(email);
    }
    public boolean existsAdminById(Integer adminId){
        return adminRepository.existsById(adminId);
    }
    public Optional<Admin> authenticateAdmin(String adminEmail, String rawPassword){
        if (adminEmail == null || rawPassword == null) {
            return Optional.empty();
        }
        Optional<Admin> adminOptional = adminRepository.findByEmail(adminEmail);
        if (adminOptional.isEmpty()) {
            return Optional.empty();
        }
        Admin admin = adminOptional.get();
        if (rawPassword.equals(admin.getPassword())) {
            return Optional.of(admin);
        }
        return Optional.empty();
    }
}