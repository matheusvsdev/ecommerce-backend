package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
