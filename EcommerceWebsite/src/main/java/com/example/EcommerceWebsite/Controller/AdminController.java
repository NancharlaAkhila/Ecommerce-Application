package com.example.EcommerceWebsite.Controller;

import com.example.EcommerceWebsite.Entities.Admin;
import com.example.EcommerceWebsite.Repository.AdminRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AdminRegisterRequest request) {
        System.out.println("Checking for username: " + request.getUsername());
        boolean exists = adminRepository.existsByUsername(request.getUsername());
        System.out.println("Exists? " + exists);

        if (exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        Admin admin = new Admin();
        admin.setUsername(request.getUsername());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        adminRepository.save(admin);

        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request, HttpSession session) {
        Admin admin = adminRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        if (admin != null) {
            session.setAttribute("admin", true); // Set session attribute
            session.setAttribute("userId", admin.getId()); // Optional
            Map<String, Object> response = new HashMap<>();
            response.put("adminId", admin.getId());
            response.put("username", admin.getUsername());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession(HttpSession session) {
        boolean isLoggedIn = session.getAttribute("userId") != null || session.getAttribute("admin") != null;
        boolean isAdmin = session.getAttribute("admin") != null;

        Map<String, Object> response = new HashMap<>();
        response.put("loggedIn", isLoggedIn);
        response.put("isAdmin", isAdmin);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully.");
    }


    // Request classes
    static class AdminRegisterRequest {
        private String username;
        private String email;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    static class AdminLoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
