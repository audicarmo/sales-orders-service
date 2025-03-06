package com.audineia.sales_orders_service.dto.response;

import com.audineia.sales_orders_service.entity.OrderItem;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long productId;
    private int quantity;
    private BigDecimal value;
    private BigDecimal subtotal;

    public OrderItemResponseDTO(Long productId, int quantity,
                                BigDecimal value, BigDecimal subtotal) {
        this.productId = productId;
        this.quantity = quantity;
        this.value = value;
        this.subtotal = subtotal;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public static OrderItemResponseDTO fromEntity(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getProductId(),
                item.getQuantity(),
                item.getValue(),
                item.calculateSubtotal()
        );
    }
}

