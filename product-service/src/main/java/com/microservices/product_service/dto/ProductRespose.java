package com.microservices.product_service.dto;

import java.math.BigDecimal;

public record ProductRespose(String id, String name, String description, BigDecimal price) {
}
