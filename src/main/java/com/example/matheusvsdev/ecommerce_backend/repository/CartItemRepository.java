package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.entities.CartItem;
import com.example.matheusvsdev.ecommerce_backend.entities.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {
}
