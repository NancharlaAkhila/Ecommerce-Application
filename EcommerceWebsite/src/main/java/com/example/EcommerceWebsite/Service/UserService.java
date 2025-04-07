package com.example.EcommerceWebsite.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcommerceWebsite.Entities.User;
import com.example.EcommerceWebsite.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Add new user
    public void addUser(User user) {
        userRepository.save(user);
    }

    // Authenticate user by username and password
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .stream()
                .findFirst()
                .orElse(null); // Return null if not found
    }
}
