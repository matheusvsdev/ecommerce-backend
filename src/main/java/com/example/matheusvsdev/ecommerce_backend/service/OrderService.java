package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderDTO;
import com.example.matheusvsdev.ecommerce_backend.dto.OrderItemDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.*;
import com.example.matheusvsdev.ecommerce_backend.repository.OrderItemRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.OrderRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.ProductRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Transactional
    public OrderDTO createOrder(OrderDTO dto) {
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setPayment(dto.getPayment());
        if (order.getPayment() == PaymentMethod.BOLETO) {
            order.setStatus(OrderStatus.AGUARDANDO_PAGAMENTO);
        }

        order.setStatus(OrderStatus.CONFIRMANDO_PAGAMENTO);
        User user = userService.autenthicated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }

        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }

    @Transactional
    public void processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (order.getStatus() == OrderStatus.AGUARDANDO_PAGAMENTO || order.getStatus() == OrderStatus.CONFIRMANDO_PAGAMENTO) {
            order.setStatus(OrderStatus.PAGAMENTO_APROVADO);
            orderRepository.save(order);

            try {
                invoiceService.generateInvoice(new OrderDTO(order));
            } catch (Exception e) {
                throw new IllegalStateException("Erro ao gerar nota fiscal: " + e.getMessage());
            }
        } else {
            throw new IllegalStateException("O pagamento não pode ser processado neste status");
        }
    }
}
