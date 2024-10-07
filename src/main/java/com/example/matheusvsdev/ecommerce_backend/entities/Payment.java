package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_payment")
public class Payment {

    @Id
    private Long id;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    private LocalDateTime paymentDate;

    private String transactionId;

    private String token;

    private Integer amount;

    public Payment() {
    }

    public Payment(Long id, PaymentMethod paymentMethod, PaymentStatus status, Order order, LocalDateTime paymentDate, String transactionId, String token, Integer amount) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.order = order;
        this.paymentDate = paymentDate;
        this.transactionId = transactionId;
        this.token = token;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
