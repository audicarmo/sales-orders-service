package com.audineia.sales_orders_service.dto.response;

import com.audineia.sales_orders_service.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private Long orderId;
    private Long customerId;
    private BigDecimal tax;
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

    public BigDecimal getTax() {
        return tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public OrderResponseDTO(Long id, Long orderId, Long customerId, BigDecimal tax,
                            OrderStatus status, List<OrderItemResponseDTO> items) {
        this.id = id;
        this.orderId = orderId;
        this.customerId = customerId;
        this.tax = tax;
        this.status = status;
        this.items = items;
    }
}
