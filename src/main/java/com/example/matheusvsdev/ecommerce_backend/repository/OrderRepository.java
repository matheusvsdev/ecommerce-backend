package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /*SELECT o.*, p.status AS payment_status, p.payment_method
    FROM tb_order o
    JOIN tb_payment p ON o.id = p.order_id
    WHERE CAST(o.moment AS DATE) = '2024-10-02';*/

    List<Order> findByUserId(Long userId);
}
