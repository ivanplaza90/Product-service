package com.ivan.test.product.infrastructure.repository.sql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private int id;
    private int productId;
    private int brandId;
    private Instant startDate;
    private Instant endDate;
    private int priceList;
    private int priority;
    private BigDecimal price;
    private String currency;
}
