package com.audineia.sales_orders_service.domain;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private Long id;
    private Long clientId;
    private List<ItemOrder> itens;
    private BigDecimal tax;
    private String status;

    public Order(Long id, Long clientId, List<ItemOrder> itens, BigDecimal tax, String status) {
        this.id = id;
        this.clientId = clientId;
        this.itens = itens;
        this.tax = tax;
        this.status = status;
    }

    public BigDecimal calculateTotal() {
        return itens.stream()
                .map(ItemOrder::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void applyTax(BigDecimal fee) {
        this.tax = calculateTotal().multiply(fee);
    }
}