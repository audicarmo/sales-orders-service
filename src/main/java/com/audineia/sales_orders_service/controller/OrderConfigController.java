package com.audineia.sales_orders_service.controller;

import com.audineia.sales_orders_service.dto.response.OrderResponseDTO;
import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.enums.OrderStatus;
import com.audineia.sales_orders_service.repository.OrderRepository;
import com.audineia.sales_orders_service.util.Constants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config")
public class OrderConfigController {
    private final StringRedisTemplate redisTemplate;
    private final OrderRepository orderRepository;

    public OrderConfigController(StringRedisTemplate redisTemplate, OrderRepository orderRepository) {
        this.redisTemplate = redisTemplate;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(@RequestParam OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        List<OrderResponseDTO> response = orders.stream()
                .map(OrderResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tax-reform")
    public boolean isTaxReformActive() {
        return Boolean.parseBoolean(redisTemplate.opsForValue().get(Constants.TAX_REFORM_KEY));
    }
}
