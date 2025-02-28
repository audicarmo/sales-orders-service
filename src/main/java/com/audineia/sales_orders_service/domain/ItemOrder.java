package com.audineia.sales_orders_service.domain;

import java.math.BigDecimal;

public class ItemOrder {
    private Long orderId;
    private int quantity;
    private BigDecimal value;

    public ItemOrder(Long orderId, int quantity, BigDecimal value) {
        this.orderId = orderId;
        this.quantity = quantity;
        this.value = value;
    }

    public BigDecimal calcularSubtotal() {
        return value.multiply(BigDecimal.valueOf(quantity));
    }
}