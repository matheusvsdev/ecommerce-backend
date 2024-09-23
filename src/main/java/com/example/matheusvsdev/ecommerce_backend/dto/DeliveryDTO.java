package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Delivery;
import com.example.matheusvsdev.ecommerce_backend.entities.DeliveryStatus;
import java.time.LocalDateTime;

public class DeliveryDTO {

    private Long id;

    private DeliveryStatus deliveryStatus;

    private LocalDateTime orderUpdateDate, estimatedDeliveryDate;

    private Long orderId;

    public DeliveryDTO() {
    }

    public DeliveryDTO(Long id,
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

    public DeliveryDTO(Delivery entity) {
        id = entity.getId();
        deliveryStatus = entity.getDeliveryStatus();
        orderUpdateDate = entity.getOrderUpdateDate();
        estimatedDeliveryDate = entity.getEstimatedDeliveryDate();
    }

    public Long getId() {
        return id;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public LocalDateTime getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public LocalDateTime getOrderUpdateDate() {
        return orderUpdateDate;
    }
}
