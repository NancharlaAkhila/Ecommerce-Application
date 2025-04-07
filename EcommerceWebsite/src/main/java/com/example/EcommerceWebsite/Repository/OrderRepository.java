package com.example.EcommerceWebsite.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcommerceWebsite.Entities.Orders;
import com.example.EcommerceWebsite.Entities.User;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);
}
