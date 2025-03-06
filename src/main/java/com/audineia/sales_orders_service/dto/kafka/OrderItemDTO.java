package com.audineia.sales_orders_service.dto.kafka;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private BigDecimal value;

    public OrderItemDTO() {}

    public OrderItemDTO(Long id, Long productId, int quantity, BigDecimal value) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
