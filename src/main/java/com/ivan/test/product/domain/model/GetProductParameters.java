package com.ivan.test.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductParameters {

    private int brandId;
    private int productId;
    private Instant timestamp;
}
