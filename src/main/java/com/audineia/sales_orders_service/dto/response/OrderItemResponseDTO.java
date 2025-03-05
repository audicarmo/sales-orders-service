package com.audineia.sales_orders_service.dto.response;

import com.audineia.sales_orders_service.entity.OrderItem;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDTO {
    private Long productId;
    private int quantity;
    private Double value;
    private Double subtotal;

    public OrderItemResponseDTO(Long productId, int quantity,
                                Double value, Double subtotal) {
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

    public Double getValue() {
        return value;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public static OrderItemResponseDTO fromEntity(OrderItem item) {
        return new OrderItemResponseDTO(
                item.getProductId(),
                item.getQuantity(),
                item.getValue(),
                item.calculateSubtotal().doubleValue()
        );
    }
}

