package com.example.EcommerceWebsite.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcommerceWebsite.Entities.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByUsername(String username);
    Admin findByUsernameAndPassword(String username, String password);
}



