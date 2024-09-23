package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import java.time.LocalDateTime;

public class OrderResponseDTO {

    private Long orderId;
    private String orderStatus;
    private LocalDateTime orderMoment;
    private LocalDateTime deliveryDate;
    private String clientName;
    private String state;
    private String city;
    private Double freightCost;
    private Double total;
    private String paymentMethod;

    public OrderResponseDTO(Order order) {
        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus().name();
        this.orderMoment = order.getMoment();
        this.deliveryDate = order.getDelivery().getEstimatedDeliveryDate();
        this.clientName = order.getClient().getFirstName() + " " + order.getClient().getLastName();
        this.state = order.getAddress().getState().name();
        this.city = order.getAddress().getCity();
        this.freightCost = order.getFreightCost();
        this.total = order.getTotal();
        this.paymentMethod = order.getPayment().getPaymentMethod().name();
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderMoment() {
        return orderMoment;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public String getClientName() {
        return clientName;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public Double getFreightCost() {
        return freightCost;
    }

    public Double getTotal() {
        return total;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}

