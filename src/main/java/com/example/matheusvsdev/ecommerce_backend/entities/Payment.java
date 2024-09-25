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

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToOne
    @MapsId
    private Order order;

    private LocalDateTime paymentConfirmationDate;

    public Payment() {
    }

    public Payment(Long id, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentConfirmationDate() {
        return paymentConfirmationDate;
    }

    public void setPaymentConfirmationDate(LocalDateTime paymentConfirmationDate) {
        this.paymentConfirmationDate = paymentConfirmationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) && paymentMethod == payment.paymentMethod && paymentStatus == payment.paymentStatus && Objects.equals(order, payment.order) && Objects.equals(paymentConfirmationDate, payment.paymentConfirmationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
