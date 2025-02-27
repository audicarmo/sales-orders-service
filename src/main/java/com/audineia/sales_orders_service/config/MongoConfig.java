package com.audineia.sales_orders_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.audineia.sales_orders_service.infrastructure")
public class MongoConfig {

}