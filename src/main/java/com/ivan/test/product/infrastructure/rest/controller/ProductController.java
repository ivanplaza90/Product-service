package com.ivan.test.product.infrastructure.rest.controller;

import com.ivan.test.product.application.GetProduct;
import com.ivan.test.product.domain.model.ProductNotFoundException;
import com.ivan.test.product.infrastructure.rest.controller.mapper.RestMapper;
import com.ivan.test.product.infrastructure.rest.controller.model.GetProductRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

public class ProductController {

    private RestMapper restMapper;
    private GetProduct getProduct;
    public Map<String, Object> getProduct(GetProductRequest request) {
        try{
            return restMapper.mapToProductResponse(
                getProduct.apply(
                restMapper.mapToGetProductParameters(request)));
        }catch (ProductNotFoundException notFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
