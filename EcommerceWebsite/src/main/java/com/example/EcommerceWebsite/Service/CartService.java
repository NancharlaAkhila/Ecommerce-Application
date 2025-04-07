package com.example.EcommerceWebsite.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EcommerceWebsite.Entities.CartItem;
import com.example.EcommerceWebsite.Entities.Product;
import com.example.EcommerceWebsite.Entities.User;
import com.example.EcommerceWebsite.Repository.CartItemRepository;
import com.example.EcommerceWebsite.Repository.ProductRepository;
import com.example.EcommerceWebsite.Repository.UserRepository;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;

    public void addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItems(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return cartItemRepository.findByUser(user);
    }
    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        
		CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));
        item.setQuantity(quantity);
        cartItemRepository.save(item);
    }

    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
