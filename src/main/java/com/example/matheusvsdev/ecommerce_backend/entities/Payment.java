package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PaymentMethod paymentMethod;

    private PaymentStatus status;

    @OneToOne
    @MapsId
    private Order order;

    private LocalDateTime paymentConfirmation;

    public Payment() {
    }

    public Payment(Long id, PaymentMethod paymentMethod, PaymentStatus status) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public Payment(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public LocalDateTime getPaymentConfirmation() {
        return paymentConfirmation;
    }

    public void setPaymentConfirmation(LocalDateTime paymentConfirmation) {
        this.paymentConfirmation = paymentConfirmation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) && paymentMethod == payment.paymentMethod && status == payment.status && Objects.equals(order, payment.order) && Objects.equals(paymentConfirmation, payment.paymentConfirmation);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
