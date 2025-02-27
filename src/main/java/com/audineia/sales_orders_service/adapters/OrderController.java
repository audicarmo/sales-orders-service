package com.audineia.sales_orders_service.adapters;

import com.audineia.sales_orders_service.domain.Order;
import com.audineia.sales_orders_service.infrastructure.OrderRepository;
import com.audineia.sales_orders_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        orderService.processRequest(order, false);
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }
}