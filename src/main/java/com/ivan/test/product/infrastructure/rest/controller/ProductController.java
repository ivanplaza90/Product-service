package com.ivan.test.product.infrastructure.rest.controller;

import com.ivan.test.product.application.GetProduct;
import com.ivan.test.product.domain.model.ProductNotFoundException;
import com.ivan.test.product.infrastructure.rest.controller.mapper.RestMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@AllArgsConstructor
public class ProductController {

    private RestMapper restMapper;
    private GetProduct getProduct;

    @GetMapping("api/product")
    public Map<String, Object> getProduct(@RequestParam("productId") Integer productId,
                                          @RequestParam("brandId") Integer brandId,
                                          @RequestParam("timestamp") Long timestamp) {
        try{
            return restMapper.mapToProductResponse(
                getProduct.apply(
                restMapper.mapToGetProductParameters(productId, brandId, timestamp)));
        }catch (ProductNotFoundException notFoundException) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
