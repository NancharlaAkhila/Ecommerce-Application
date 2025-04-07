package com.example.EcommerceWebsite.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.EcommerceWebsite.Entities.User;
import com.example.EcommerceWebsite.Service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userservice;

    @GetMapping("/getuser")
    public List<User> getUsers() {
        return userservice.getAllUsers();
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user) {
        userservice.addUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
        User existingUser = userservice.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            session.setAttribute("user", existingUser);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", existingUser.getId());
            response.put("username", existingUser.getUsername());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return ResponseEntity.ok("logged out");
    }

    @GetMapping("/checkSession")
    public ResponseEntity<Boolean> checkSession(HttpSession session) {
        return ResponseEntity.ok(session.getAttribute("user") != null);
    }
}
