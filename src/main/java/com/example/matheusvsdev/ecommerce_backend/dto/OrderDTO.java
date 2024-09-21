package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;

    private LocalDateTime moment;

    private Double orderedAmount;

    private Double total;

    private ClientDTO client;

    private Delivery delivery;

    private PaymentDTO payment;

    private List<OrderItemDTO> items = new ArrayList<>();

    private Long addressId;

    private Double freightCost;

    public OrderDTO(Long id, LocalDateTime moment, PaymentDTO payment, Double orderedAmount, Double total, ClientDTO client, Delivery delivery, Long addressId) {
        this.id = id;
        this.moment = moment;
        this.payment = payment;
        this.orderedAmount = orderedAmount;
        this.total = total;
        this.client = client;
        this.delivery = delivery;
        this.addressId = addressId;
    }

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        orderedAmount = entity.getOrderedAmount();
        total = entity.getTotal();
        addressId = entity.getAddress().getId();
        client = new ClientDTO(entity.getClient());
        delivery = entity.getDelivery();
        freightCost = entity.getFreightCost();
        for (OrderItem item : entity.getItems()) {
            OrderItemDTO itemDTO = new OrderItemDTO(item);
            items.add(itemDTO);
        }
        if (entity.getPayment() != null) {
            payment = new PaymentDTO();
            payment.setPaymentMethod(entity.getPayment().getPaymentMethod());
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public Double getOrderedAmount() {
        return orderedAmount;
    }

    public Double getTotal() {
        return total;
    }

    public ClientDTO getClient() {
        return client;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Double getFreightCost() {
        return freightCost;
    }
}
