package com.audineia.sales_orders_service.dto.response;

import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.enums.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@NoArgsConstructor
public class OrderResponseProcessDTO {
    private Long id;
    private Long orderId;
    private Long customerId;
    private OrderStatus status;
    private List<OrderItemResponseDTO> items;

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public OrderResponseProcessDTO(Long id, Long orderId, Long customerId, BigDecimal tax,
                                   OrderStatus status, List<OrderItemResponseDTO> items) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.items = items;
    }

    public static OrderResponseProcessDTO fromEntity(Order order) {
        return new OrderResponseProcessDTO(
                order.getId(),
                order.getOrderId(),
                order.getCustomerId(),
                order.getTax(),
                order.getStatus(),
                order.getItems().stream()
                        .map(OrderItemResponseDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}
