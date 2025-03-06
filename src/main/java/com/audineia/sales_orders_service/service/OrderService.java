package com.audineia.sales_orders_service.service;

import com.audineia.sales_orders_service.dto.request.OrderRequestDTO;
import com.audineia.sales_orders_service.dto.response.OrderResponseProcessDTO;
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
import java.util.ArrayList;
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
    public OrderResponseProcessDTO processOrder(OrderRequestDTO orderRequestDTO) {
        requestValidator.validateOrder(orderRequestDTO);
        ensureOrderDoesNotExist(orderRequestDTO.getOrderId());

        Order savedOrder = saveInitialOrder(orderRequestDTO);
        List<OrderItem> items = createOrderItems(orderRequestDTO, savedOrder);
        savedOrder.setItems(items);
        orderRepository.save(savedOrder);

        sendOrderCreatedEvent(savedOrder);
        logger.info("Order published to Kafka: {}", savedOrder);

        return OrderResponseProcessDTO.fromEntity(savedOrder);
    }

    private void ensureOrderDoesNotExist(Long orderId) {
        if (orderRepository.findByOrderId(orderId).isPresent()) {
            throw new IllegalArgumentException("Order already exists.");
        }
    }

    private Order saveInitialOrder(OrderRequestDTO orderRequestDTO) {
        Order order = new Order(
                orderRequestDTO.getOrderId(),
                orderRequestDTO.getCustomerId(),
                Collections.emptyList(),
                BigDecimal.ZERO,
                OrderStatus.CREATED
        );
        return orderRepository.save(order);
    }

    private List<OrderItem> createOrderItems(OrderRequestDTO orderRequestDTO, Order order) {
        return Optional.ofNullable(orderRequestDTO.getItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(itemRequest -> new OrderItem(null, order, itemRequest.getProductId(),
                        itemRequest.getQuantity(), itemRequest.getValue()))
                .collect(Collectors.toList());
    }

    protected void sendOrderCreatedEvent(Order order) {
        orderProducer.sendOrderCreatedEvent(order);
    }
}