package com.audineia.sales_orders_service.controller;

import com.audineia.sales_orders_service.util.Constants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    private final StringRedisTemplate redisTemplate;

    public ConfigController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/tax-reform/{status}")
    public void setTaxReform(@PathVariable boolean status) {
        redisTemplate.opsForValue().set(Constants.TAX_REFORM_KEY, String.valueOf(status));
    }

    @GetMapping("/tax-reform")
    public boolean isTaxReformActive() {
        return Boolean.parseBoolean(redisTemplate.opsForValue().get(Constants.TAX_REFORM_KEY));
    }
}
