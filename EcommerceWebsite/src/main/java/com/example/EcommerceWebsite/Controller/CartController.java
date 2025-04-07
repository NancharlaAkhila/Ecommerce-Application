package com.example.EcommerceWebsite.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.EcommerceWebsite.Entities.CartItem;
import com.example.EcommerceWebsite.Entities.QuantityRequest;
import com.example.EcommerceWebsite.Service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000") // Adjust based on your frontend's origin
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam Long userId,
                                            @RequestParam Long productId,
                                            @RequestParam int quantity) {
        cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok("Product added to cart");
    }

    // ✅ Changed to use @RequestParam instead of @PathVariable
    @GetMapping("/view")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestParam Long userId) {
        List<CartItem> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCartItem(@PathVariable Long id,
                                                 @RequestBody QuantityRequest request) {
        cartService.updateCartItemQuantity(id, request.getQuantity());
        return ResponseEntity.ok("Cart item quantity updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        cartService.deleteCartItem(id);
        return ResponseEntity.ok("Cart item deleted");
    }
}
