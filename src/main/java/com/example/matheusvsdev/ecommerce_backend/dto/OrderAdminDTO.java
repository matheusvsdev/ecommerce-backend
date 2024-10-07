package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.projection.OrderProjection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderAdminDTO {

    private Long orderId;

    private LocalDateTime moment;

    private OrderStatus status;

    private List<OrderItemDTO> items = new ArrayList<>();

    private Double subTotal;

    private String user;

    private State state;

    private String city;

    private Double freightCost;

    private Double total;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private ShippingStatus shippingStatus;

    private LocalDateTime deliveryDate;

    public OrderAdminDTO() {
    }

    public OrderAdminDTO(Long orderId, LocalDateTime moment, OrderStatus status, Double subTotal, String user, State state, String city, Double freightCost, Double total, PaymentMethod paymentMethod, PaymentStatus paymentStatus, String transactionId, ShippingStatus shippingStatus, LocalDateTime deliveryDate) {
        this.orderId = orderId;
        this.moment = moment;
        this.status = status;
        this.subTotal = subTotal;
        this.user = user;
        this.state = state;
        this.city = city;
        this.freightCost = freightCost;
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.shippingStatus = shippingStatus;
        this.deliveryDate = deliveryDate;
    }

    public OrderAdminDTO(OrderProjection projection) {
        this.orderId = projection.getOrderId();
        this.moment = projection.getOrderMoment();
        this.status = OrderStatus.values()[projection.getOrderStatus()];
        this.user = projection.getUserName();
        this.paymentMethod = PaymentMethod.values()[projection.getPaymentMethod()];
        this.paymentStatus = PaymentStatus.values()[projection.getPaymentStatus()];
        this.transactionId = projection.getTransactionId();
        this.state = State.values()[projection.getState()];
        this.city = projection.getCity();
        this.subTotal = projection.getSubTotal();
        this.freightCost = projection.getFreightCost();
        this.total = projection.getTotal();
        this.shippingStatus = ShippingStatus.valueOf(projection.getShippingStatus());
        this.deliveryDate = projection.getDeliveryTime();

    }

    public Long getOrderId() {
        return orderId;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public State getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public Double getFreightCost() {
        return freightCost;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public Double getTotal() {
        return total;
    }

    public String getUser() {
        return user;
    }
}
