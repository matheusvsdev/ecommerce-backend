package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.CartDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.CartItemDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.CartItemRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.CartRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.ProductRepository;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AuthService authService;

    @Transactional
    public CartDTO addItemToCart(CartItemDTO cartItemDTO) {
        User user = authService.authenticated();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> createNewCartForUser(user));

        Product product = productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

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

    @Transactional
    public CartDTO removetemToCart(Long productId) {
        User user = authService.authenticated();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Adicione itens primeiro"));

        Optional<CartItem> cartItem = cart.getItems().stream()
                .filter(x -> x.getProduct().getId().equals(productId))
                .findFirst();

        CartItem item = cartItem
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        cart.removeItem(item);
        cart.setTotal(cart.getTotal());

        cart.setTotal(cart.getTotal());

        cartRepository.save(cart);

        return new CartDTO(cart);
    }

    @Transactional
    public CartDTO clearCart() {
        User user = authService.authenticated();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Adicione itens primeiro"));

        cart.clearItems();

        cart.setTotal(0.0);

        cartRepository.save(cart);

        return new CartDTO(cart);
    }

    @Transactional(readOnly = true)
    public CartDTO getCartByUser() {
        User user = authService.authenticated();
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Adicione itens primeiro"));
        return new CartDTO(cart);
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

    @Transactional(readOnly = true)
    public Cart findCartByAuthenticatedUser() {
        User user = authService.authenticated();
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado para o usuário"));
    }
}
