package com.audineia.sales_orders_service.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
public class OrderItemRequestDTO {
    private Long productId;
    private Integer quantity;
    private BigDecimal value;

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public OrderItemRequestDTO(Long productId, Integer quantity, BigDecimal value) {
        this.productId = productId;
        this.quantity = quantity;
        this.value = value;
    }
}
