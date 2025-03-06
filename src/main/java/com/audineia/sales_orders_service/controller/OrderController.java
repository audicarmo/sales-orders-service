package com.audineia.sales_orders_service.controller;
import com.audineia.sales_orders_service.dto.request.OrderRequestDTO;
import com.audineia.sales_orders_service.dto.response.OrderResponseProcessDTO;
import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.enums.OrderStatus;
import com.audineia.sales_orders_service.repository.OrderRepository;
import com.audineia.sales_orders_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<OrderResponseProcessDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseProcessDTO response = orderService.processOrder(orderRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/processed")
    public ResponseEntity<List<Order>> getProcessedOrders() {
        List<Order> processedOrders = orderRepository.findByStatus(OrderStatus.PROCESSING);
        return ResponseEntity.ok(processedOrders);
    }
}