package com.audineia.sales_orders_service.entity;

import com.audineia.sales_orders_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = "orderId"))
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public BigDecimal calculateTotal() {
        return items.stream()
                .map(OrderItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void applyTax(BigDecimal taxRate) {
        this.tax = calculateTotal().multiply(taxRate);
    }

    public Order(Long orderId, Long customerId, List<OrderItem> items,
                 BigDecimal tax, OrderStatus status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.tax = tax;
        this.status = status;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }
}
