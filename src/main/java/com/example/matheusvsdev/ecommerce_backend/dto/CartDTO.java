package com.example.matheusvsdev.ecommerce_backend.dto;

import com.example.matheusvsdev.ecommerce_backend.entities.Cart;
import com.example.matheusvsdev.ecommerce_backend.entities.CartItem;
import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private Long cartId;

    private Double total;

    private List<CartItemDTO> items = new ArrayList<>();

    public CartDTO() {
    }

    public CartDTO(Long cartId, Double total) {
        this.cartId = cartId;
        this.total = total;
    }

    public CartDTO(Cart cart) {
        cartId = cart.getId();
        total = cart.getTotal();

        for (CartItem item : cart.getItems()) {
            CartItemDTO cartItemDTO = new CartItemDTO(item);
            items.add(cartItemDTO);
        }
    }

    public Long getCartId() {
        return cartId;
    }

    public Double getTotal() {
        return total;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }
}
