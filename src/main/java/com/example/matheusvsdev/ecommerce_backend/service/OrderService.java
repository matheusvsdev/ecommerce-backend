package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.*;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private CartService cartService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public OrderResponseDTO placeOrderFromCart(OrderDTO orderDTO) {

        Cart cart = cartService.findCartByAuthenticatedUser();
        cartService.validateCart(cart);

        Order order = new Order();
        order.setMoment(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CONFIRMADO);

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getPrice());
            order.addItem(orderItem);
        }

        User user = authService.autenthicated();
        order.setUser(user);

        Address address = addressRepository.findById(orderDTO.getEnderecoId()).get();
        authService.validateSelfOrAdmin(address.getClient().getId());

        order.setAddress(address);
        address.getOrders().add(order);

        Payment payment = createPayment(orderDTO.getPagamento());
        order.setPayment(payment);
        payment.setOrder(order);

        order.setTotal(cart.getTotal());

        Double freightCost = deliveryService.calculateFreight(address.getState());
        order.setFreightCost(freightCost);

        Delivery delivery = createDelivery();
        delivery.setFreightCost(freightCost);
        delivery.setOrder(order);
        order.setDelivery(delivery);

        orderRepository.save(order);

        for (CartItem cartItem : cart.getItems()) {
            cartItemRepository.delete(cartItem);
        }

        cart.getItems().clear();
        cart.setTotal(0.0);
        cartService.updateCart(cart);

        deliveryRepository.save(delivery);
        orderItemRepository.saveAll(order.getItems());

        return new OrderResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        authService.validateSelfOrAdmin(order.getUser().getId());
        return new OrderDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        User user = authService.autenthicated();

        List<Order> orders;

        if (user.hasRole("ROLE_ADMIN")) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.findByUserId(user.getId());
        }
        return orders.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
    }

    private Payment createPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.CONFIRMANDO_PAGAMENTO);
        return payment;
    }

    private Delivery createDelivery() {
        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.PREPARANDO);
        delivery.setOrderUpdateDate(LocalDateTime.now());
        delivery.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(15));
        return delivery;
    }
}
