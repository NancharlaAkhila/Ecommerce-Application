package com.example.EcommerceWebsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.EcommerceWebsite.Entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	boolean existsByCategoryNameIgnoreCase(String categoryName);

	Category findByCategoryName(String categoryName);
}
