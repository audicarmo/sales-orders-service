package com.audineia.sales_orders_service.unit;

import com.audineia.sales_orders_service.controller.OrderController;
import com.audineia.sales_orders_service.dto.request.OrderItemRequestDTO;
import com.audineia.sales_orders_service.dto.request.OrderRequestDTO;
import com.audineia.sales_orders_service.dto.response.OrderResponseDTO;
import com.audineia.sales_orders_service.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService orderService;
    private OrderRequestDTO orderRequest;
    private OrderResponseDTO orderResponse;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequestDTO(1L, 123L,
                List.of(new OrderItemRequestDTO(101L, 2, BigDecimal.ONE)));
        orderResponse = new OrderResponseDTO(1L, 1L, 123L,
                BigDecimal.ONE, com.audineia.sales_orders_service.enums.OrderStatus.CREATED, List.of());
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        when(orderService.processOrder(any(OrderRequestDTO.class))).thenReturn(orderResponse);

        ResponseEntity<OrderResponseDTO> response = orderController.createOrder(orderRequest);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(orderResponse, response.getBody());
    }

    @Test
    void shouldReturnBadRequestWhenServiceThrowsException() {
        when(orderService.processOrder(any(OrderRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Invalid order data"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderController.createOrder(orderRequest));
        assertEquals("Invalid order data", exception.getMessage());
    }
}
