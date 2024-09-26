package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Shipping;
import com.example.matheusvsdev.ecommerce_backend.entities.ShippingStatus;
import java.time.LocalDateTime;

public class ShippingDTO {

    private Long id;

    private ShippingStatus status;

    private LocalDateTime orderUpdate, deliveryTime;

    private Double freightCost;

    public ShippingDTO() {
    }

    public ShippingDTO(Long id,
                       ShippingStatus status,
                       LocalDateTime orderUpdate,
                       LocalDateTime deliveryTime,
                       Double freightCost) {
        this.id = id;
        this.status = status;
        this.orderUpdate = orderUpdate;
        this.deliveryTime = deliveryTime;
        this.freightCost = freightCost;
    }

    public ShippingDTO(Shipping entity) {
        id = entity.getId();
        status = entity.getStatus();
        orderUpdate = entity.getOrderUpdate();
        deliveryTime = entity.getDeliveryTime();
        freightCost = entity.getFreightCost();
    }

    public Long getId() {
        return id;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public LocalDateTime getOrderUpdate() {
        return orderUpdate;
    }

    public Double getFreightCost() {
        return freightCost;
    }
}
