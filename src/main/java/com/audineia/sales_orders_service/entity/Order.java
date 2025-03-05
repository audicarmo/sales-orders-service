package com.audineia.sales_orders_service.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
    @Table(name = "orders")
    public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "customer_id", nullable = false)
        private Long customerId;

        @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<OrderItem> items;

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal tax;

        @Column(nullable = false)
        private String status;

        public Order() {}

    public Order(Long customerId, List<OrderItem> items, BigDecimal tax, String status) {
        this.customerId = customerId;
        this.items = items;
        this.tax = tax;
        this.status = status;
    }

    public BigDecimal calculateTotal() {
        return items.stream()
                .map(OrderItem::calculateSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void applyTax(BigDecimal taxa) {
        this.tax = calculateTotal().multiply(taxa);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
