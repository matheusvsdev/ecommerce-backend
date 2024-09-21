package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime moment;

    private Double orderedAmount;

    private Double total;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private Double freightCost;

    public Order() {
    }

    public Order(Long id, LocalDateTime moment, Double orderedAmount, Double total, Payment payment, User client, Address address, Double freightCost) {
        this.id = id;
        this.moment = moment;
        this.orderedAmount = orderedAmount;
        this.total = total;
        this.payment = payment;
        this.client = client;
        this.address = address;
        this.freightCost = freightCost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Double getOrderedAmount() {
        orderedAmount = items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();

        return orderedAmount;
    }

    public Double getTotal() {
        if (orderedAmount > 800.0) {
            freightCost = 0.0;
        }
        total = orderedAmount + freightCost;

        return total;
    }

    public Double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(Double freightCost) {
        this.freightCost = freightCost;
    }

    public void setOrderedAmount(Double orderedAmount) {
        this.orderedAmount = orderedAmount;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }
}
