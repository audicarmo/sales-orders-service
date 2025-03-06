package com.audineia.sales_orders_service.unit;

import com.audineia.sales_orders_service.dto.request.OrderItemRequestDTO;
import com.audineia.sales_orders_service.dto.request.OrderRequestDTO;
import com.audineia.sales_orders_service.dto.response.OrderResponseDTO;
import com.audineia.sales_orders_service.enums.OrderStatus;
import com.audineia.sales_orders_service.entity.Order;
import com.audineia.sales_orders_service.kafka.OrderProducer;
import com.audineia.sales_orders_service.repository.OrderRepository;
import com.audineia.sales_orders_service.service.OrderService;
import com.audineia.sales_orders_service.validation.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RequestValidator requestValidator;
    @Mock
    private OrderProducer orderProducer;
    private OrderRequestDTO orderRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        orderRequest = new OrderRequestDTO(1L, 123L,
                List.of(new OrderItemRequestDTO(101L, 2, BigDecimal.ONE)));

        orderService = new OrderService(orderRepository, requestValidator, orderProducer);
    }

    @Test
    void shouldProcessOrderSuccessfully() {
        when(orderRepository.findByOrderId(anyLong())).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDTO response = orderService.processOrder(orderRequest);

        assertNotNull(response);
        assertEquals(OrderStatus.CREATED.name(), response.getStatus().name());
    }

    @Test
    void shouldThrowExceptionForDuplicateOrder() {
        when(orderRepository.findByOrderId(anyLong())).thenReturn(Optional.of(new Order()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.processOrder(orderRequest));

        assertEquals("Order already exists.", exception.getMessage());
    }

    @Test
    void shouldValidateAndSaveOrderWithoutKafka() {
        doNothing().when(requestValidator).validateOrder(any(OrderRequestDTO.class));
        when(orderRepository.findByOrderId(anyLong())).thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDTO response = orderService.processOrder(orderRequest);

        assertNotNull(response);
        assertEquals(OrderStatus.CREATED.name(), response.getStatus().name());
    }
}
