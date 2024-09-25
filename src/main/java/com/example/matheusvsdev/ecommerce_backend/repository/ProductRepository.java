package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.entities.Product;
import com.example.matheusvsdev.ecommerce_backend.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    @Query(nativeQuery = true, value = """
            SELECT * FROM (
            SELECT DISTINCT *
            FROM products
            INNER JOIN product_category ON products.id = product_category.product_id
            WHERE (:categoryIds IS NULL OR product_category.category_id IN (:categoryIds))
            AND LOWER(products.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ) AS tb_result
            """, countQuery = """
            SELECT COUNT(*) FROM (
            SELECT DISTINCT *
            FROM products
            INNER JOIN product_category ON products.id = product_category.product_id
            WHERE (:categoryIds IS NULL OR product_category.category_id IN (:categoryIds))
            AND LOWER(products.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ) AS tb_result
            """)
    Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);

    @Query("SELECT obj FROM Product obj LEFT JOIN FETCH obj.categories")
    Page<Product> findAllProductsWithCategories(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT products.id, img, name, description, quantity, price " +
            "FROM products " +
            "JOIN product_category ON products.id = product_category.product_id " +
            "WHERE product_category.category_id = :categoryId")
    Page<Product> findProductsByCategoryId(@Param("categoryId")Long categoryId, Pageable pageable);
}
