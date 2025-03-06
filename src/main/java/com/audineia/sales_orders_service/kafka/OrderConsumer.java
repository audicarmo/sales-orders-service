package com.audineia.sales_orders_service.kafka;

import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.service.OrderProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);
    private final OrderProcessingService orderProcessingService;

    public OrderConsumer(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void consumeOrder(Order order) {
        logger.info("Order received from Kafka: {}", order);
        orderProcessingService.processOrder(order);
    }
}