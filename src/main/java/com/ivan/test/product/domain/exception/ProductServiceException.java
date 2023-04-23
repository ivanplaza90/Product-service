package com.ivan.test.product.domain.exception;

public class ProductServiceException extends RuntimeException{

    public ProductServiceException(Exception exception){
        super(exception);
    }
    public ProductServiceException(String message){
        super(message);
    }
}
