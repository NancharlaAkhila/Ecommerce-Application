package com.example.EcommerceWebsite.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    private String productName;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_Id", referencedColumnName = "categoryId")
    @JsonIgnoreProperties("products") 
    private Category category;

    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties("products") // avoid infinite JSON recursion
    private java.util.List<Orders> orders;

    public Product() {}

    public Product(Long productId, String productName, Double price, Category category) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    // Getters and setters for all fields including orders

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public java.util.List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(java.util.List<Orders> orders) {
        this.orders = orders;
    }
}
