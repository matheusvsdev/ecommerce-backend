package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private LocalDateTime orderUpdateDate, estimatedDeliveryDate;

    private Long orderId;

    public Delivery() {
    }

    public Delivery(Long id,
                    DeliveryStatus deliveryStatus,
                    LocalDateTime orderUpdateDate,
                    LocalDateTime estimatedDeliveryDate,
                    Long orderId) {
        this.id = id;
        this.deliveryStatus = deliveryStatus;
        this.orderUpdateDate = orderUpdateDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.orderId = orderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getOrderUpdateDate() {
        return orderUpdateDate;
    }

    public void setOrderUpdateDate(LocalDateTime orderUpdateDate) {
        this.orderUpdateDate = orderUpdateDate;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(LocalDateTime estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
