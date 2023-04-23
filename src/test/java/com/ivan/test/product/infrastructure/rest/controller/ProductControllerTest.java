package com.ivan.test.product.infrastructure.rest.controller;

import com.ivan.test.product.application.GetProduct;
import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Money;
import com.ivan.test.product.domain.model.Product;
import com.ivan.test.product.domain.model.ProductNotFoundException;
import com.ivan.test.product.infrastructure.rest.controller.mapper.RestMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private static final int PRODUCT_ID = 1;
    private static final int BRAND_ID = 2;
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    public static final String TIMESTAMP = "2020-06-14-00.00.00";
    private static final int PRICE_LIST = 1;
    private static final int PRIORITY = 5;
    private static final Money PRICE = Money.builder().amount(BigDecimal.valueOf(35.75)).currency("EUR").build();
    private static final Date DATE = new Date();


    @Spy
    private RestMapper restMapper = Mappers.getMapper(RestMapper.class);
    @Mock
    private GetProduct getProduct;
    @InjectMocks
    private ProductController productController;

    @Test
    void should_return_internal_server_error_given_a_request_when_get_product_uses_case_throws_not_managed_exception() throws ParseException {
        //GIVEN
        assertThat(productController).isNotNull();
        final ArgumentCaptor<GetProductParameters> argumentCaptor = ArgumentCaptor.forClass(GetProductParameters.class);

        given(getProduct.apply(any(GetProductParameters.class)))
                .willThrow(new RuntimeException("UNIT TEST"));

        //WHEN
        final Throwable throwable = catchThrowable(() -> productController.getProduct(PRODUCT_ID, BRAND_ID, TIMESTAMP));

        //THEN
        assertThat(throwable).isNotNull()
            .isInstanceOf(ResponseStatusException.class)
            .hasFieldOrPropertyWithValue("status", HttpStatus.INTERNAL_SERVER_ERROR);

        then(restMapper).should().mapToGetProductParameters(eq(PRODUCT_ID), eq(BRAND_ID), eq(TIMESTAMP));
        then(getProduct).should().apply(argumentCaptor.capture());

        assertGetProductParameters(argumentCaptor.getValue());
    }

    @Test
    void should_return_not_found_error_given_a_request_when_get_product_uses_case_throws_product_not_found_exception() throws ParseException {
        //GIVEN
        assertThat(productController).isNotNull();
        final ArgumentCaptor<GetProductParameters> argumentCaptor = ArgumentCaptor.forClass(GetProductParameters.class);

        given(getProduct.apply(any(GetProductParameters.class)))
                .willThrow(new ProductNotFoundException("UNIT TEST"));

        //WHEN
        final Throwable throwable = catchThrowable(() -> productController.getProduct(PRODUCT_ID, BRAND_ID, TIMESTAMP));

        //THEN
        assertThat(throwable).isNotNull()
                .isInstanceOf(ResponseStatusException.class)
                .hasFieldOrPropertyWithValue("status", HttpStatus.NOT_FOUND);

        then(restMapper).should().mapToGetProductParameters(eq(PRODUCT_ID), eq(BRAND_ID), eq(TIMESTAMP));
        then(getProduct).should().apply(argumentCaptor.capture());

        assertGetProductParameters(argumentCaptor.getValue());
    }

    @Test
    void should_return_a_product_given_a_request_when_get_product_uses_case_returns_a_product() throws ParseException {
        //GIVEN
        assertThat(productController).isNotNull();
        final ArgumentCaptor<GetProductParameters> argumentCaptor = ArgumentCaptor.forClass(GetProductParameters.class);
        final Product product = mockProduct();

        given(getProduct.apply(any(GetProductParameters.class)))
            .willReturn(product);

        //WHEN
        final Map<String, Object> response = productController.getProduct(PRODUCT_ID, BRAND_ID, TIMESTAMP);

        //THEN
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("productId", PRODUCT_ID)
            .hasFieldOrPropertyWithValue("brandId", BRAND_ID)
            .hasFieldOrPropertyWithValue("startDate", DATE.toString())
            .hasFieldOrPropertyWithValue("endDate", DATE.toString())
            .hasFieldOrPropertyWithValue("priceList", PRICE_LIST)
            .hasFieldOrPropertyWithValue("priority", PRIORITY)
            .hasFieldOrPropertyWithValue("price.amount", PRICE.getAmount())
            .hasFieldOrPropertyWithValue("price.currency", PRICE.getCurrency());

        then(restMapper).should().mapToGetProductParameters(eq(PRODUCT_ID), eq(BRAND_ID), eq(TIMESTAMP));
        then(getProduct).should().apply(argumentCaptor.capture());
        then(restMapper).should().mapToProductResponse(eq(product));

        assertGetProductParameters(argumentCaptor.getValue());
    }

    private static void assertGetProductParameters(GetProductParameters parameters) throws ParseException {
        assertThat(parameters).isNotNull()
            .hasFieldOrPropertyWithValue("productId", PRODUCT_ID)
            .hasFieldOrPropertyWithValue("brandId", BRAND_ID)
            .hasFieldOrPropertyWithValue("timestamp", DATE_FORMAT.parse(TIMESTAMP));
    }

    private Product mockProduct(){
        return Product.builder()
                .productId(PRODUCT_ID)
                .brandId(BRAND_ID)
                .startDate(DATE)
                .endDate(DATE)
                .priceList(PRICE_LIST)
                .priority(PRIORITY)
                .price(PRICE)
                .build();
    }
}
