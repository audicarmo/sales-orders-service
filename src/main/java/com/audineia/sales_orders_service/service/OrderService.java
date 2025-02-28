package com.audineia.sales_orders_service.service;

import com.audineia.sales_orders_service.domain.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {
    public void processRequest(Order order, boolean taxReform) {
        BigDecimal fee = taxReform ? new BigDecimal("0.2") : new BigDecimal("0.3");
        order.applyTax(fee);
    }
}
