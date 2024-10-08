package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.*;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.projection.OrderProjection;
import com.example.matheusvsdev.ecommerce_backend.repository.*;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.ResourceNotFoundException;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CartService cartService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private StripePaymentService stripePaymentService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        Cart cart = cartService.findCartByAuthenticatedUser();
        cartService.validateCart(cart);

        Order order = insertDataIntoTheOrder(orderDTO, cart);

        orderItemRepository.saveAll(order.getItems());

        updateCartAfterOrder(cart);

        return new OrderResponseDTO(order);
    }

    private Order insertDataIntoTheOrder(OrderDTO orderDTO, Cart cart) {
        Order order = new Order();
        order.setMoment(LocalDateTime.now());
        order.setStatus(OrderStatus.PROCESSING);

        User user = authService.authenticated();
        order.setUser(user);

        Address address = findAddress(orderDTO.getAddressId(), user);
        order.setAddress(address);
        address.getOrders().add(order);

        addItemsToOrder(cart, order);

        setFreightAndDelivery(order, address);

        order.setSubTotal(order.getSubTotal());
        order.setFreightCost(order.getFreightCost());
        order.setTotal(order.getTotal());

        processPayment(order, orderDTO.getPayment());

        if (order.getPayment().getStatus() == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.FAILED);
        }

        orderRepository.save(order);

        return order;
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        authService.validateSelfOrAdmin(order.getUser().getId());
        return new OrderResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> findAll(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(OrderResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<OrderAdminDTO> searchOrders(Long clientId, String cpf, String status, String paymentMethod, String startDate, String endDate, Pageable pageable) {

        Page<OrderProjection> orderProjections = orderRepository.searchOrders(clientId, cpf, status, paymentMethod, startDate, endDate, pageable);

        Map<Long, OrderAdminDTO> orderMap = new HashMap<>();

        orderProjections.forEach(projection -> {
            Long orderId = projection.getOrderId();

            OrderAdminDTO orderResponseDTO = orderMap.getOrDefault(orderId, new OrderAdminDTO(projection));

            // Adicionando os itens ao pedido
            OrderItemDTO orderItemDTO = new OrderItemDTO(
                    projection.getProductId(),
                    projection.getProductName(),
                    projection.getProductPrice(),
                    projection.getProductQuantity(),
                    projection.getProductImg()
            );

            orderResponseDTO.getItems().add(orderItemDTO);

            orderMap.put(orderId, orderResponseDTO);
        });

        return new PageImpl<>(new ArrayList<>(orderMap.values()), pageable, orderProjections.getTotalElements());
    }

    private void addItemsToOrder(Cart cart, Order order) {
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getPrice());
            order.addItem(orderItem);

            inventoryService.updateStock(cartItem.getProduct().getId(), cartItem.getQuantity());
        }
    }

    private void processPayment(Order order, PaymentDTO paymentDTO) {
        try {
            // Configurar o pagamento
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            payment.setStatus(PaymentStatus.PENDING);
            order.setPayment(payment);

            Double totalOrder = order.getTotal();
            int amount = (int) (totalOrder * 100);

            String token = paymentDTO.getToken();
            String chargeId = stripePaymentService.createCharge(token, amount);

            // Atualizar o pagamento com o resultado do Stripe
            payment.setTransactionId(chargeId);
            payment.setToken(token);
            payment.setAmount(amount);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.SUCCESS);
        } catch (StripeException e) {
            // Caso ocorra erro no pagamento
            order.getPayment().setStatus(PaymentStatus.FAILED);
        }
    }

    private Shipping createDelivery() {
        Shipping delivery = new Shipping();
        delivery.setStatus(ShippingStatus.PREPARANDO);
        delivery.setOrderUpdate(LocalDateTime.now());
        delivery.setDeliveryTime(LocalDateTime.now().plusDays(15));
        return delivery;
    }

    private Address findAddress(Long addressId, User user) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
        authService.validateSelfOrAdmin(address.getClient().getId());
        return address;
    }

    private void setFreightAndDelivery(Order order, Address address) {
        Double freightCost = deliveryService.calculateFreight(address.getState());
        order.setFreightCost(freightCost);

        Shipping delivery = createDelivery();
        delivery.setFreightCost(freightCost);
        delivery.setOrder(order);

        order.setDelivery(delivery);

    }

    private void updateCartAfterOrder(Cart cart) {
        for (CartItem cartItem : cart.getItems()) {
            cartItemRepository.delete(cartItem);
        }

        cart.getItems().clear();
        cart.setTotal(0.0);

        cartRepository.save(cart);
    }
}
