package com.rozgarsetu.controller;

import com.rozgarsetu.entity.Admin;
import com.rozgarsetu.service.AdminService;
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
}
