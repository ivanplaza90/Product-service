package com.ivan.test.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int productId;
    private int brandId;
    private Date startDate;
    private Date endDate;
    private int priceList;
    private int priority;
    private Money price;
}
