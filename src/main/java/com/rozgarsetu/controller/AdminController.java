package com.rozgarsetu.controller;

import com.rozgarsetu.dto.AdminLoginRequest;
import com.rozgarsetu.entity.Admin;
import com.rozgarsetu.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/{adminId}")
    public ResponseEntity<AdminResponse> getAdminById(@PathVariable Integer adminId) {
        Optional<Admin> adminOptional = adminService.findAdminById(adminId);
        if (adminOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Admin admin = adminOptional.get();
        AdminResponse response = new AdminResponse(admin.getAdminId(), admin.getName(), admin.getEmail()
        );
        return ResponseEntity.ok(response);
    }

    // Safe response DTO (does not expose password)
    public record AdminResponse(Integer adminId, String name, String email){

    }

    @PostMapping("/login")
    public ResponseEntity<AdminResponse> loginAdmin(@Valid @RequestBody AdminLoginRequest request) {
        Optional<Admin> adminOptional = adminService.authenticateAdmin(request.getEmail(), request.getPassword());
        if (adminOptional.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        Admin admin = adminOptional.get();
        AdminResponse response = new AdminResponse(admin.getAdminId(), admin.getName(), admin.getEmail());
        return ResponseEntity.ok(response);
    }
}
