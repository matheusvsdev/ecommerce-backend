package com.example.matheusvsdev.ecommerce_backend.repository;

import com.example.matheusvsdev.ecommerce_backend.dto.OrderItemDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.Order;
import com.example.matheusvsdev.ecommerce_backend.projection.OrderProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value = """
    SELECT o.id AS orderId,
           o.moment AS orderMoment,
           o.status AS orderStatus,
           o.sub_total AS subTotal,
           o.freight_cost AS freightCost,
           o.total AS total,
           a.state AS state,
           a.city AS city,
           pm.payment_method AS paymentMethod,
           pm.status AS paymentStatus,
           pm.transaction_id AS transactionId,
           u.first_name AS userName,
           oi.product_id AS productId,
           p.name AS productName,
           oi.quantity AS productQuantity,
           oi.price AS productPrice,
           p.img AS productImg,
           s.status AS shippingStatus,
           s.delivery_time AS deliveryTime
    FROM tb_order o
    LEFT JOIN tb_user u ON u.id = o.user_id
    LEFT JOIN tb_address a ON a.id = o.address_id
    LEFT JOIN tb_payment pm ON pm.order_id = o.id
    LEFT JOIN tb_order_item oi ON oi.order_id = o.id
    LEFT JOIN tb_product p ON p.id = oi.product_id
    LEFT JOIN tb_shipping s ON s.id = o.id
    WHERE (:clientId IS NULL OR o.user_id = :clientId)
    AND (:cpf IS NULL OR u.cpf = :cpf)
    AND (:status IS NULL OR o.status = :status)
    AND (:paymentMethod IS NULL OR pm.payment_method = :paymentMethod)
    AND (:startDate IS NULL OR o.moment >= CAST(:startDate AS DATE))
    AND (:endDate IS NULL OR o.moment <= CAST(:endDate AS DATE))
    ORDER BY o.moment DESC
""")
    Page<OrderProjection> searchOrders(
            @Param("clientId") Long clientId,
            @Param("cpf") String cpf,
            @Param("status") String status,
            @Param("paymentMethod") String paymentMethod,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            Pageable pageable
    );

    List<Order> findByUserId(Long userId);

    /*SELECT o.*, oi.product_id, quantity, price
    FROM tb_order o
    JOIN tb_order_item oi ON oi.order_id = o.id
    WHERE CAST(o.moment AS DATE) = '2024-02-12';*/
}
