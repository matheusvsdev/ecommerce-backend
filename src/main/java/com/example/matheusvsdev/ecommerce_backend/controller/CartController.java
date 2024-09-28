package com.example.matheusvsdev.ecommerce_backend.controller;

import com.example.matheusvsdev.ecommerce_backend.dto.CartDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.CartItemDTO;
import com.example.matheusvsdev.ecommerce_backend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping("/addItem")
    public ResponseEntity<CartDTO> addItemToCart(@RequestBody CartItemDTO dto) {
        CartDTO cartDTO = cartService.addItemToCart(dto);

        return ResponseEntity.ok(cartDTO);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        CartDTO cartList = cartService.getCartByUser();
        return ResponseEntity.ok().body(cartList);
    }

    @PreAuthorize(value = "hasRole('ROLE_CLIENT')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CartDTO> update(@PathVariable Long id, @RequestParam int quantity) {
        CartDTO cartDTO = cartService.updateCart(id, quantity);
        return ResponseEntity.ok(cartDTO);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @DeleteMapping("/clear")
    public ResponseEntity<CartDTO> clearCart() {
        CartDTO clearedCart = cartService.clearCart();
        return ResponseEntity.ok(clearedCart);
    }
}
