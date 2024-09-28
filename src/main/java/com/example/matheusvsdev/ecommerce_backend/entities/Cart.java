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

    @OneToMany(mappedBy = "id.cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> items = new HashSet<>();

    public Cart() {
    }

    public Cart(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    // Método que calcula e retorna o total do carrinho
    public Double getTotal() {
        double sum = 0.0;
        for (CartItem itemDTO : items) {
            sum += itemDTO.getSubTotal();
        }
        total = sum;
        return Math.round(total * 100.0) / 100.0;
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

    // Método que adiciona um item ao carrinho
    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    // Método que limpa todos os itens do carrinho
    public void clearItems() {
        this.items.clear();
    }

    public List<Product> getProducts() {
        return items.stream().map(x -> x.getProduct()).toList();
    }
}
