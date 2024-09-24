package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.CartDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.CartItemDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addItem")
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody CartItemDTO dto) {
        CartDTO cartDTO = cartService.addItemToCart(dto);

        return ResponseEntity.ok(cartDTO);
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        CartDTO cartList = cartService.getCartByUser();
        return ResponseEntity.ok().body(cartList);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartDTO> removeItemFromCart(@PathVariable Long productId) {
        CartDTO updatedCart = cartService.removetemToCart(productId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCart() {
        CartDTO clearedCart = cartService.clearCart();
        return ResponseEntity.ok(clearedCart);
    }
}
