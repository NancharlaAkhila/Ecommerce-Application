package com.example.EcommerceWebsite.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcommerceWebsite.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameAndPassword(String username, String password);
}

