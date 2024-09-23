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

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private Double freightCost = 0.0;

    public Order() {
    }

    public Order(Long id, LocalDateTime moment, OrderStatus orderStatus, User user, Delivery delivery, Address address) {
        this.id = id;
        this.moment = moment;
        this.user = user;
        this.address = address;
        this.delivery = delivery;
        this.orderStatus = orderStatus;
    }


    public Double getOrderedAmount() {
        double sum = 0.0;
        for (OrderItem itemDTO : items) {
            sum += itemDTO.getSubTotal();
        }
        orderedAmount = sum;
        return orderedAmount;
    }

    public Double getTotal() {
        if (getOrderedAmount() > 800.0) {
            freightCost = 0.0;
        }
        total = getOrderedAmount() + freightCost;

        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Delivery getDelivery() {
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Order(Payment payment) {
        this.payment = payment;
        payment.setOrder(this);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(Double freightCost) {
        this.freightCost = freightCost;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }
}
