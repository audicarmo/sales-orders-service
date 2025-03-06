package com.audineia.sales_orders_service.kafka;

import com.audineia.sales_orders_service.dto.kafka.OrderDTO;
import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.util.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);
    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(Order order) {
        OrderDTO orderDTO = OrderMapper.toDTO(order);
        kafkaTemplate.send("order-created", orderDTO);
        logger.info("Order published to Kafka: {}", orderDTO);
    }

    public void sendOrderProcessedEvent(Order order) {
        OrderDTO orderDTO = OrderMapper.toDTO(order);
        kafkaTemplate.send("order-processed", orderDTO);
        logger.info("Processed order published to Kafka: {}", orderDTO);
    }
}