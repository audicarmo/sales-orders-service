package com.audineia.sales_orders_service.util;

import com.audineia.sales_orders_service.dto.kafka.OrderDTO;
import com.audineia.sales_orders_service.dto.kafka.OrderItemDTO;
import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.entity.OrderItem;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getOrderId(),
                order.getCustomerId(),
                order.getTax(),
                order.getStatus(),
                order.getItems().stream().map(OrderMapper::toDTO).collect(Collectors.toList())
        );
    }

    public static OrderItemDTO toDTO(OrderItem item) {
        return new OrderItemDTO(
                item.getId(),
                item.getProductId(),
                item.getQuantity(),
                item.getValue()
        );
    }

    public static Order toEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderId(dto.getOrderId());
        order.setCustomerId(dto.getCustomerId());
        order.setTax(dto.getTax());
        order.setStatus(dto.getStatus());
        order.setItems(dto.getItems().stream().map(OrderMapper::toEntity).collect(Collectors.toList()));
        return order;
    }

    public static OrderItem toEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        item.setProductId(dto.getProductId());
        item.setQuantity(dto.getQuantity());
        item.setValue(dto.getValue());
        return item;
    }
}
