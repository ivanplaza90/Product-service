package com.ivan.test.product.domain;

import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> find(GetProductParameters getProductParameters);
}
