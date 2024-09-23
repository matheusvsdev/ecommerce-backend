package com.example.matheusvsdev.ecommerce_backend.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cart_item")
public class CartItem {

    @EmbeddedId
    private CartItemPK id = new CartItemPK();

    private Integer quantity;
    private Double price;

    public CartItem() {
    }

    public CartItem(Cart cart, Product product, Integer quantity, Double price) {
        id.setCart(cart);
        id.setProduct(product);
        this.price = price;
        this.quantity = quantity;
    }

    public Cart getCart() {
        return id.getCart();
    }

    public void setCart(Cart cart) {
        id.setCart(cart);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSubTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return getProduct().getName() +
                "\nQuantidade = " + quantity +
                "\nPreço = " + price +
                "\nSubtotal = " + getSubTotal() + "\n";
    }
}
