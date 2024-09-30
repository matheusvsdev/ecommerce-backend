package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime moment;

    private Double subTotal;

    private Double total;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Shipping delivery;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private Double freightCost;

    public Order() {
    }

    public Order(Long id, LocalDateTime moment, OrderStatus status, User user, Shipping delivery, Address address) {
        this.id = id;
        this.moment = moment;
        this.user = user;
        this.address = address;
        this.delivery = delivery;
        this.status = status;
    }


    public Double getSubTotal() {
        double subTotal = 0.0;
        for (OrderItem itemDTO : items) {
            subTotal += itemDTO.getSubTotal();
        }

        return subTotal;
    }

    public Double getTotal() {
        if (total == null) {
            total = Math.round((getSubTotal() + freightCost) * 100.0) / 100.0;
        }
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public User getUser() {
        return user;
    }

    public Shipping getDelivery() {
        return delivery;
    }

    public Address getAddress() {
        return address;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public Payment getPayment() {
        return payment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order(Payment payment) {
        this.payment = payment;
        payment.setOrder(this);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getFreightCost() {
        if (getSubTotal() >= 800.0) {
            return 0.0;
        }
        return freightCost;
    }

    public void setFreightCost(Double freightCost) {
        this.freightCost = freightCost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDelivery(Shipping delivery) {
        this.delivery = delivery;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(moment, order.moment) && Objects.equals(subTotal, order.subTotal) && Objects.equals(total, order.total) && Objects.equals(payment, order.payment) && status == order.status && Objects.equals(user, order.user) && Objects.equals(delivery, order.delivery) && Objects.equals(items, order.items) && Objects.equals(address, order.address) && Objects.equals(freightCost, order.freightCost);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
