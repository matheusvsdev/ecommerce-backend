package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long orderId;

    private LocalDateTime moment;

    private OrderStatus statusPedido;

    private List<OrderItemDTO> items = new ArrayList<>();

    private PaymentDTO pagamento;

    private Long enderecoId;

    private Delivery rastreio;

    private Double frete;

    private Double valorItems;

    private Double total;

    private String user;

    public OrderDTO(Long orderId, LocalDateTime moment, OrderStatus statusPedido, PaymentDTO pagamento, Long enderecoId, Delivery rastreio, Double frete, Double valorItems, Double total, String user) {
        this.orderId = orderId;
        this.moment = moment;
        this.statusPedido = statusPedido;
        this.pagamento = pagamento;
        this.enderecoId = enderecoId;
        this.rastreio = rastreio;
        this.frete = frete;
        this.valorItems = valorItems;
        this.total = total;
        this.user = user;
    }

    public OrderDTO(Order entity) {
        orderId = entity.getId();
        user = entity.getUser().getFirstName() + " " + entity.getUser().getLastName();
        moment = entity.getMoment();
        statusPedido = entity.getOrderStatus();
        pagamento = new PaymentDTO(entity.getPayment().getPaymentMethod());
        enderecoId = entity.getAddress().getId();
        rastreio = entity.getDelivery();
        frete = entity.getFreightCost();
        valorItems = entity.getOrderedAmount();
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

    public OrderStatus getStatusPedido() {
        return statusPedido;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public PaymentDTO getPagamento() {
        return pagamento;
    }


    public Long getEnderecoId() {
        return enderecoId;
    }

    public Delivery getRastreio() {
        return rastreio;
    }

    public Double getFrete() {
        return frete;
    }

    public Double getValorItems() {
        return valorItems;
    }

    public Double getTotal() {
        return total;
    }

    public String getUser() {
        return user;
    }
}
