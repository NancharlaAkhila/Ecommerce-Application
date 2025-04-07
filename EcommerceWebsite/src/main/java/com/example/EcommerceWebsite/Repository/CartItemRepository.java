package com.example.EcommerceWebsite.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EcommerceWebsite.Entities.CartItem;
import com.example.EcommerceWebsite.Entities.Product;
import com.example.EcommerceWebsite.Entities.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByProduct(Product product);

}

