package com.audineia.sales_orders_service.dto.kafka;

import com.audineia.sales_orders_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long orderId;
    private Long customerId;
    private BigDecimal tax;
    private OrderStatus status;
    private List<OrderItemDTO> items;

    public OrderDTO() {}

    public OrderDTO(Long id, Long orderId, Long customerId, BigDecimal tax, OrderStatus status, List<OrderItemDTO> items) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.tax = tax;
        this.status = status;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}