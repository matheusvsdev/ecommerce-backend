package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tb_cart")
public class Cart {

    @Id
    private Long id;

    private Double total;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "id.cart")
    private Set<CartItem> items = new HashSet<>();

    public Cart() {
    }

    public Cart(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Double getTotal() {
        double sum = 0.0;
        for (CartItem itemDTO : items) {
            sum += itemDTO.getSubTotal();
        }
        total = sum;
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }

    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }
}
