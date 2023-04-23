package com.ivan.test.product.infrastructure.repository.sql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "PRODUCT")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    private int id;
    @Column(name = "PRODUCT_ID")
    private int productId;
    @Column(name = "BRAND_ID")
    private int brandId;
    @Column(name = "START_DATE")
    private Instant startDate;
    @Column(name = "END_DATE")
    private Instant endDate;
    @Column(name = "PRICE_LIST")
    private int priceList;
    @Column(name = "PRIORITY")
    private int priority;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "CURRENCY")
    private String currency;
}
