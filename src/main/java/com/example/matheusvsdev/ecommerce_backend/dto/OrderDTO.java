package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long orderId;

    private LocalDateTime moment;

    private OrderStatus status;

    private List<OrderItemDTO> items = new ArrayList<>();

    private PaymentDTO payment;

    private Long addressId;

    private Shipping shipping;

    private Double subTotal;

    private Double freightCost;

    private Double total;

    private String user;

    public OrderDTO(Long orderId, PaymentDTO payment, String user, Long addressId) {
        this.orderId = orderId;
        this.payment = payment;
        this.user = user;
        this.addressId = addressId;
    }

    public OrderDTO(Order entity) {
        orderId = entity.getId();
        user = entity.getUser().getFirstName() + " " + entity.getUser().getLastName();
        moment = entity.getMoment();
        status = entity.getStatus();
        payment = new PaymentDTO(entity.getPayment());
        addressId = entity.getAddress().getId();
        shipping = entity.getDelivery();
        subTotal = entity.getSubTotal();
        freightCost = entity.getFreightCost();
        total = entity.getTotal();

        for (OrderItem item : entity.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO(item);
            items.add(itemDTO);
        }
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

    public PaymentDTO getPayment() {
        return payment;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public Double getFreightCost() {
        return freightCost;
    }

    public Double getTotal() {
        return total;
    }

    public String getUser() {
        return user;
    }
}
