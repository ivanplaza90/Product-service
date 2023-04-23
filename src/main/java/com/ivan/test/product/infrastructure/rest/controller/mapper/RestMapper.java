package com.ivan.test.product.infrastructure.rest.controller.mapper;

import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Money;
import com.ivan.test.product.domain.model.Product;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.Map;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR, unmappedTargetPolicy = IGNORE)
public interface RestMapper {
    GetProductParameters mapToGetProductParameters(int productId, int brandId, Long timestamp);

    default Instant mapToInstant(Long timestamp) {
        if(timestamp == null)
            return null;
        return Instant.ofEpochMilli(timestamp);
    }

    default Map<String, Object> mapToProductResponse(Product product) {
        return Map.of(
        "productId", product.getProductId(),
        "brandId", product.getBrandId(),
        "startDate", product.getStartDate(),
        "endDate", product.getEndDate(),
        "priceList", product.getPriceList(),
        "priority", product.getPriority(),
        "price", mapToMoneyResponse(product.getPrice())
        );
    }

    default Map<String, Object> mapToMoneyResponse(Money money) {
        return Map.of(
            "amount", money.getAmount(),
            "currency", money.getCurrency()
        );
    }
}
