package com.example.EcommerceWebsite.Controller;

import com.example.EcommerceWebsite.Entities.Category;
import com.example.EcommerceWebsite.Entities.Product;
import com.example.EcommerceWebsite.Entities.ProductRequest;
import com.example.EcommerceWebsite.Service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:3000") // adjust if needed
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get all categories
    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new category
    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ResponseEntity.ok("Category added successfully!");
    }

    // Check if category exists by name
    @GetMapping("/check")
    public ResponseEntity<Boolean> checkCategoryExists(@RequestParam(value = "categoryName", required = false) String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean exists = categoryService.doesCategoryExist(categoryName);
        return ResponseEntity.ok(exists);
    }

    // Get all products by category name
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam String categoryName) {
        System.out.println("Fetching products for category: " + categoryName);
        List<Product> products = categoryService.getProductsByCategory(categoryName);
        return ResponseEntity.ok(products);
    }

    // ✅ PRODUCT Operations

    // Add product to category
    @PostMapping("/product/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest productRequest) {
        try {
            categoryService.addProduct(productRequest);
            return ResponseEntity.ok("Product added successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update product by name
    @PutMapping("/update")
    public ResponseEntity<String> updateProductByName(@RequestBody ProductRequest request) {
        String result = categoryService.updateProductByName(request);
        return ResponseEntity.ok(result);
    }

    // Delete product by name
    @DeleteMapping("/delete/{productName}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productName) {
        try {
            String message = categoryService.deleteProductByName(productName);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error: " + e.getMessage());
        }
    }
}
