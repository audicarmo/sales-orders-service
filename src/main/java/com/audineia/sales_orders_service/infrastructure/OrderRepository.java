package com.audineia.sales_orders_service.infrastructure;

import com.audineia.sales_orders_service.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

}