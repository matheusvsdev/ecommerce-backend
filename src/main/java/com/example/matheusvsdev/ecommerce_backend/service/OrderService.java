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

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Transactional
    public OrderResponseDTO createOrder(OrderDTO dto) {

        Order order = new Order();

        order.setMoment(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CONFIRMADO);

        addOrderItems(order, dto.getItems());

        User user = authService.autenthicated();
        order.setClient(user);

        Address address = addressService.findById(dto.getEnderecoId());
        authService.validateSelfOrAdmin(address.getClient().getId());

        order.setAddress(address);
        address.getOrders().add(order);

        Payment payment = createPayment(dto.getPagamento());
        order.setPayment(payment);
        payment.setOrder(order);

        orderRepository.save(order);

        Double freightCost = freightService.calculateFreight(address.getState());
        order.setFreightCost(freightCost);

        Delivery delivery = createDelivery();
        order.setDelivery(delivery);
        delivery.setOrder(order);

        deliveryRepository.save(delivery);
        orderItemRepository.saveAll(order.getItems());

        emailService.orderConfirmation(order);

        return new OrderResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        User user = authService.autenthicated();

        List<Order> orders;

        if (user.hasRole("ROLE_ADMIN")) {
            orders = orderRepository.findAll();
        } else {
            orders = orderRepository.findByClientId(user.getId());
        }
        return orders.stream().map(x -> new OrderDTO(x)).collect(Collectors.toList());
    }

    private void addOrderItems(Order order, List<OrderItemDTO> itemDTOs) {
        for (OrderItemDTO itemDTO : itemDTOs) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }
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

    //public OrderResponseDTO getOrderDetails(Long orderId) {
        //Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        //return new OrderResponseDTO(order);
    //}
}
