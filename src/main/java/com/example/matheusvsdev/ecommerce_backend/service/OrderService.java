package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderItemDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FreightService freightService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Transactional
    public OrderDTO createOrder(OrderDTO dto) {
        Order order = new Order();
        order.setMoment(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CONFIRMADO);

        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPayment().getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.CONFIRMANDO_PAGAMENTO);

        order.setPayment(payment);

        Address address = addressRepository.findById(dto.getAddressId()).orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        authService.validateSelfOrAdmin(address.getClient().getId());
        order.setAddress(address);

        Double freightCost = freightService.calculateFreight(address.getState());
        order.setFreightCost(freightCost);

        User user = userService.autenthicated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }

        orderRepository.save(order);

        Delivery delivery = new Delivery();
        delivery.setDeliveryStatus(DeliveryStatus.PREPARANDO);
        delivery.setOrderUpdateDate(LocalDateTime.now());
        delivery.setEstimatedDeliveryDate(LocalDateTime.now().plusDays(15));
        delivery.setOrderId(order.getId());

        order.setDelivery(delivery);

        deliveryRepository.save(delivery);
        orderItemRepository.saveAll(order.getItems());

        emailService.orderConfirmation(order);

        return new OrderDTO(order);
    }
}
