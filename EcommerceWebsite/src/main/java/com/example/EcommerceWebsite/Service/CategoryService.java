package com.example.EcommerceWebsite.Service;

import com.example.EcommerceWebsite.Entities.Category;
import com.example.EcommerceWebsite.Entities.Product;
import com.example.EcommerceWebsite.Entities.ProductRequest;
import com.example.EcommerceWebsite.Repository.CartItemRepository;
import com.example.EcommerceWebsite.Repository.CategoryRepository;
import com.example.EcommerceWebsite.Repository.ProductRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    // Add a new category
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    // Check if category exists by name
    public boolean doesCategoryExist(String categoryName) {
        return categoryRepository.existsByCategoryNameIgnoreCase(categoryName);
    }

    // Add a product to a category
    public void addProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findByCategoryName(productRequest.getCategoryName());
        Product product = new Product();
        product.setProductName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);

        productRepository.save(product);
    }

    // Update product by name
    public String updateProductByName(ProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findByProductName(request.getName());
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setPrice(request.getPrice());

            Category category = categoryRepository.findByCategoryName(request.getCategoryName());
            product.setCategory(category);

            productRepository.save(product);
            return "Product updated successfully.";
        } else {
            throw new RuntimeException("Product not found with name: " + request.getName());
        }
    }

    // Delete product by name
    @Transactional
    public String deleteProductByName(String productName) {
        Optional<Product> optionalProduct = productRepository.findByProductName(productName);
        
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.getOrders().forEach(order -> order.getProducts().remove(product));
            // Delete all cart items containing this product
            cartItemRepository.deleteByProduct(product);
            
            // Now delete the product itself
            productRepository.delete(product);
            
            return "Product deleted successfully.";
        } else {
            throw new RuntimeException("Product not found with name: " + productName);
        }
    }


    // Get products by category name
    public List<Product> getProductsByCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        return category.getProducts(); // assuming @OneToMany in Category.java
    }
}
