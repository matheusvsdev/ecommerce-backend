package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import com.example.matheusvsdev.ecommerce_backend.entities.OrderItem;
import com.example.matheusvsdev.ecommerce_backend.entities.OrderStatus;
import com.example.matheusvsdev.ecommerce_backend.entities.Shipping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderResponseDTO {

    private Long orderId;

    private LocalDateTime moment;

    private OrderStatus status;

    private List<OrderItemDTO> items = new ArrayList<>();

    private Double subTotal;

    private String user;

    private Long addressId;

    private Shipping shipping;

    private Double total;

    private PaymentResponseDTO payment;

    public OrderResponseDTO() {
    }

    public OrderResponseDTO(Long orderId, PaymentResponseDTO payment, Long addressId, String user) {
        this.orderId = orderId;
        this.payment = payment;
        this.addressId = addressId;
        this.user = user;
    }

    public OrderResponseDTO(Order order) {
        orderId = order.getId();
        user = order.getUser().getFirstName() + " " + order.getUser().getLastName();
        moment = order.getMoment();
        status = order.getStatus();
        subTotal = order.getSubTotal();
        payment = new PaymentResponseDTO(order.getPayment());
        addressId = order.getAddress().getId();
        shipping = order.getDelivery();
        total = order.getTotal();

        for (OrderItem item : order.getItems()) {
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

    public PaymentResponseDTO getPayment() {
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

    public Double getTotal() {
        return total;
    }

    public String getUser() {
        return user;
    }
}
