package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.entities.Cart;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);

    Optional<Cart> findByUser(User user);
}
