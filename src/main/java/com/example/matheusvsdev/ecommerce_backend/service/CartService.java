package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.CartDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.CartItemDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.CartItemRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.CartRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public CartDTO addItemToCart(CartItemDTO cartItemDTO) {
        User user = authService.autenthicated();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewCartForUser(user));

        Product product = productRepository.findById(cartItemDTO.getProductId()).get();

        Optional<CartItem> cartItem = cart.getItems().stream()
                .filter(x -> x.getProduct().getId().equals(cartItemDTO.getProductId()))
                .findFirst();

        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            item.setQuantity(item.getQuantity() + cartItemDTO.getQuantity());
        } else {
            CartItem newItem = new CartItem(cart, product, cartItemDTO.getQuantity(), product.getPrice());
            cart.addItem(newItem);

            cartItemRepository.save(newItem);
        }

        cart.setTotal(cart.getTotal());

        cartRepository.save(cart);

        return new CartDTO(cart);
    }

    @Transactional(readOnly = true)
    public Cart findById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        authService.validateSelfOrAdmin(cart.getUser().getId());
        return cart;
    }

    private Cart createNewCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setTotal(0.0);

        cartRepository.save(newCart);

        return newCart;
    }

    public void validateCart(Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("O carrinho está vazio ou não existe.");
        }
    }

    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    public Cart findCartByAuthenticatedUser() {
        User user = authService.autenthicated();
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o usuário"));
    }
}
