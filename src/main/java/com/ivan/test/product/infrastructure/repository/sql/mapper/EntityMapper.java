package com.ivan.test.product.infrastructure.repository.sql.mapper;

import com.ivan.test.product.domain.model.Product;
import com.ivan.test.product.infrastructure.repository.sql.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR, unmappedTargetPolicy = IGNORE)
public interface EntityMapper {

    @Mapping(target = "price.amount", source = "price")
    @Mapping(target = "price.currency", source = "currency")
    Product mapToProduct(ProductEntity entity);
}
