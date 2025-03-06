package com.audineia.sales_orders_service.dto.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderRequestDTO {
    private Long orderId;
    private Long customerId;
    private List<OrderItemRequestDTO> items;

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderRequestDTO(Long orderId, Long customerId, List<OrderItemRequestDTO> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
    }
}
