package com.audineia.sales_orders_service.validation;

import com.audineia.sales_orders_service.dto.request.OrderRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {
    public void validateOrder(OrderRequestDTO orderRequestDTO) {
        if (orderRequestDTO.getOrderId() == null || orderRequestDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Order ID and Customer ID are mandatory.");
        }

        if (orderRequestDTO.getItems() == null || orderRequestDTO.getItems().isEmpty()) {
            throw new IllegalArgumentException("The order must contain at least one item.");
        }

        orderRequestDTO.getItems().forEach(item -> {
            if (item.getProductId() == null || item.getQuantity() == null || item.getValue() == null) {
                throw new IllegalArgumentException("Items must have productId, quantity, and value.");
            }
        });
    }
}
