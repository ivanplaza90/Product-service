package com.ivan.test.product.domain.model;

import com.ivan.test.product.domain.exception.ProductServiceException;

public class ProductNotFoundException extends ProductServiceException {
    public ProductNotFoundException(Exception exception) {
        super(exception);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
