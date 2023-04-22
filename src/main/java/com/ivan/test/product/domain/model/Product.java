package com.ivan.test.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int productId;
    private int brandId;
    private Instant startDate;
    private Instant endDate;
    private int priceList;
    private int priority;
    private Money price;
}
