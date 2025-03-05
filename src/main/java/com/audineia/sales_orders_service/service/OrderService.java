package com.audineia.sales_orders_service.service;

import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final OrderRepository orderRepository;

    public OrderService(KafkaTemplate<String, Order> kafkaTemplate, OrderRepository orderRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderRepository = orderRepository;
    }

    public void processRequest(Order order, boolean taxReform) {
        BigDecimal fee = taxReform ? new BigDecimal("0.2") : new BigDecimal("0.3");
        order.applyTax(fee);
        orderRepository.save(order);
        kafkaTemplate.send("orders-topic", order);
        logger.info("Request sent to Kafka: {}", order);
    }

    @KafkaListener(topics = "orders-topic", groupId = "order-group")
    public void consumeOrder(Order order) {
        logger.info("Request received from Kafka: {}", order);
        order.setStatus("Processed");
        orderRepository.save(order);
    }

    @Scheduled(fixedRate = 60000)
    public void batchProcessOrders() {
        List<Order> pendingOrders = orderRepository.findByStatus("Created");
        for (Order order : pendingOrders) {
            processRequest(order, false);
        }
        logger.info("Processed batch of {} orders.", pendingOrders.size());
    }
}