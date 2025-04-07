package com.example.EcommerceWebsite.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.EcommerceWebsite.Entities.CartItem;
import com.example.EcommerceWebsite.Entities.Orders;
import com.example.EcommerceWebsite.Entities.Product;
import com.example.EcommerceWebsite.Entities.User;
import com.example.EcommerceWebsite.Repository.CartItemRepository;
import com.example.EcommerceWebsite.Repository.OrderRepository;
import com.example.EcommerceWebsite.Repository.UserRepository;
import jakarta.transaction.Transactional;
@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void placeOrder(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        List<Product> products = cartItems.stream()
            .map(CartItem::getProduct)
            .collect(Collectors.toList());
        
        Orders order = new Orders();
        order.setUser(user);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        
        orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);
    }

    public List<Orders> getOrderHistory(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }
}
