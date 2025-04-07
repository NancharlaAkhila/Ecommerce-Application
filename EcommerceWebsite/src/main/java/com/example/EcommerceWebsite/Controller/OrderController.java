package com.example.EcommerceWebsite.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EcommerceWebsite.Entities.Orders;
import com.example.EcommerceWebsite.Service.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000") // Adjust as needed
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestParam Long userId) {
        orderService.placeOrder(userId);
        return ResponseEntity.ok("Order placed successfully");
    }

    // ✅ Changed to use @RequestParam instead of @PathVariable
    @GetMapping("/history")
    public ResponseEntity<List<Orders>> getOrderHistory(@RequestParam Long userId) {
        List<Orders> orders = orderService.getOrderHistory(userId);
        return ResponseEntity.ok(orders);
    }
}
