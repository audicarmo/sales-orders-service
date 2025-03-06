package com.audineia.sales_orders_service.service;

import com.audineia.sales_orders_service.dto.request.OrderRequestDTO;
import com.audineia.sales_orders_service.dto.response.OrderResponseDTO;
import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.entity.OrderItem;
import com.audineia.sales_orders_service.enums.OrderStatus;
import com.audineia.sales_orders_service.kafka.OrderProducer;
import com.audineia.sales_orders_service.repository.OrderRepository;
import com.audineia.sales_orders_service.validation.RequestValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final RequestValidator requestValidator;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, RequestValidator requestValidator,
                        OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.requestValidator = requestValidator;
        this.orderProducer = orderProducer;
    }

    @Transactional
    public OrderResponseDTO processOrder(OrderRequestDTO orderRequestDTO) {
        requestValidator.validateOrder(orderRequestDTO);

        Optional<Order> existingOrder = orderRepository.findByOrderId(orderRequestDTO.getOrderId());
        if (existingOrder.isPresent()) {
            throw new IllegalArgumentException("Order already exists.");
        }

        List<OrderItem> items = Optional.ofNullable(orderRequestDTO.getItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(itemRequest -> new OrderItem(null, null, itemRequest.getProductId(),
                        itemRequest.getQuantity(), itemRequest.getValue()))
                .collect(Collectors.toList());

        Order order = new Order(
                orderRequestDTO.getOrderId(),
                orderRequestDTO.getCustomerId(),
                items,
                BigDecimal.ZERO,
                OrderStatus.CREATED
        );

        orderRepository.save(order);
        sendOrderCreatedEvent(order);
        logger.info("Order published to Kafka: {}", order);

        return OrderResponseDTO.fromEntity(order);
    }

    protected void sendOrderCreatedEvent(Order order) {
        orderProducer.sendOrderCreatedEvent(order);
    }
}