package com.audineia.sales_orders_service.kafka;

import com.audineia.sales_orders_service.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);
    private final KafkaTemplate<String, Order> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(Order order) {
        kafkaTemplate.send("order-created", order);
        logger.info("Order published to Kafka: {}", order);
    }

    public void sendOrderProcessedEvent(Order order) {
        kafkaTemplate.send("order-processed", order);
        logger.info("Processed order published to Kafka: {}", order);
    }
}