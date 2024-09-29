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
            SELECT tb_product.id, tb_product.img, tb_product.name, tb_product.description, tb_product.price
            FROM tb_product
            INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))
            AND LOWER(tb_product.name) LIKE LOWER(CONCAT('%', :name, '%'))
            """, countQuery = """
            SELECT COUNT(DISTINCT tb_product.id)
            FROM tb_product
            INNER JOIN tb_product_category ON tb_product.id = tb_product_category.product_id
            WHERE (:categoryIds IS NULL OR tb_product_category.category_id IN (:categoryIds))
            AND LOWER(tb_product.name) LIKE LOWER(CONCAT('%', :name, '%'))
            """)
    Page<ProductProjection> searchProducts(List<Long> categoryIds, String name, Pageable pageable);

    @Query("SELECT obj FROM Product obj LEFT JOIN FETCH obj.categories")
    Page<Product> findAllProductsWithCategories(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT tb_product.id, img, name, description, quantity, price " +
            "FROM tb_product " +
            "JOIN tb_product_category ON tb_product.id = tb_product_category.product_id " +
            "WHERE tb_product_category.category_id = :categoryId")
    Page<Product> findProductsByCategoryId(@Param("categoryId")Long categoryId, Pageable pageable);
}
