package com.audineia.sales_orders_service.adapters;

import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.repository.OrderRepository;
import com.audineia.sales_orders_service.service.OrderService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return ResponseEntity.ok(order);
    }

    @PostMapping("/batch")
    public ResponseEntity<String> createOrdersInBatch(@RequestBody List<Order> orders) {
        for (Order order : orders) {
            order.setStatus("Created");
            orderRepository.save(order);
        }
        return ResponseEntity.ok("Batch orders received and will be processed.");
    }

    @GetMapping("/{id}")
    @Cacheable(value = "order", key = "#id")
    public ResponseEntity<Order> searchOrder(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam String status) {
        return ResponseEntity.ok(orderRepository.findByStatus(status));
    }
}