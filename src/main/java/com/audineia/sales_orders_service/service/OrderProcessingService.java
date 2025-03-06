package com.audineia.sales_orders_service.service;

import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.enums.OrderStatus;
import com.audineia.sales_orders_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderProcessingService {
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingService.class);
    private final OrderRepository orderRepository;
    private final StringRedisTemplate redisTemplate;
    private static final String TAX_REFORM_KEY = "taxReformActive";

    public OrderProcessingService(OrderRepository orderRepository, StringRedisTemplate redisTemplate) {
        this.orderRepository = orderRepository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public void processOrder(Order order) {
        try {
            logger.info("Processing order: {}", order.getOrderId());

            BigDecimal taxRate = isTaxReformActive() ? BigDecimal.valueOf(0.2) : BigDecimal.valueOf(0.3);
            order.applyTax(taxRate);
            order.setStatus(OrderStatus.PROCESSING);

            orderRepository.save(order);
            logger.info("Order processed and saved: {}", order.getOrderId());
        } catch (Exception e) {
            logger.error("Error processing order {}: {}", order.getOrderId(), e.getMessage());
            order.setStatus(OrderStatus.ERROR);
            orderRepository.save(order);
        }
    }

    private boolean isTaxReformActive() {
        return Optional.ofNullable(redisTemplate.opsForValue().get(TAX_REFORM_KEY))
                .map(Boolean::parseBoolean)
                .orElse(false);
    }
}
