package com.ivan.test.product.infrastructure.repository.sql;

import com.ivan.test.product.domain.ProductRepository;
import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Product;
import com.ivan.test.product.infrastructure.repository.sql.mapper.EntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private ProductJpaRepository productJpaRepository;
    private EntityMapper entityMapper;


    @Override
    public Optional<Product> find(GetProductParameters getProductParameters) {
        return productJpaRepository
            .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                getProductParameters.getProductId(), getProductParameters.getBrandId(), getProductParameters.getTimestamp(), getProductParameters.getTimestamp())
            .map(entityMapper::mapToProduct);
    }
}
