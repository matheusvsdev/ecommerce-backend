package com.example.matheusvsdev.ecommerce_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private LocalDateTime orderUpdateDate, estimatedDeliveryDate;

    private Double freightCost;

    @OneToOne(mappedBy = "delivery")
    @JsonIgnore
    private Order order;

    public Delivery() {
    }

    public Delivery(Long id,
                    DeliveryStatus deliveryStatus,
                    LocalDateTime orderUpdateDate,
                    LocalDateTime estimatedDeliveryDate, Double freightCost) {
        this.id = id;
        this.deliveryStatus = deliveryStatus;
        this.orderUpdateDate = orderUpdateDate;
        this.estimatedDeliveryDate = estimatedDeliveryDate;
        this.freightCost = freightCost;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(Double freightCost) {
        this.freightCost = freightCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;
        return Objects.equals(id, delivery.id) && deliveryStatus == delivery.deliveryStatus && Objects.equals(orderUpdateDate, delivery.orderUpdateDate) && Objects.equals(estimatedDeliveryDate, delivery.estimatedDeliveryDate) && Objects.equals(freightCost, delivery.freightCost) && Objects.equals(order, delivery.order);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
