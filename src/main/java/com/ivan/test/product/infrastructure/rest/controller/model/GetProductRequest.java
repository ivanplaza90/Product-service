package com.ivan.test.product.infrastructure.rest.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductRequest {
    private int brandId;
    private int productId;
    private Instant timestamp;
}
