package com.ivan.test.product.application;

import com.ivan.test.product.domain.ProductRepository;
import com.ivan.test.product.domain.exception.ProductServiceException;
import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Product;
import com.ivan.test.product.domain.model.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
@AllArgsConstructor
public class GetProduct implements Function<GetProductParameters, Product> {

    private ProductRepository productRepository;
    @Override
    public Product apply(GetProductParameters getProductParameters) {
        return getStoredProduct(getProductParameters)
            .orElseThrow(() -> new ProductNotFoundException("The product is not found"));
    }

    private Optional<Product> getStoredProduct(GetProductParameters getProductParameters) {
        try{
            return productRepository.find(getProductParameters);
        } catch (Exception e) {
            log.error("An error occur while get stored products", e);
            throw new ProductServiceException(e);
        }
    }
}