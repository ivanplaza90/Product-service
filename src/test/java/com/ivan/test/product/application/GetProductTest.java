package com.ivan.test.product.application;

import com.ivan.test.product.domain.ProductRepository;
import com.ivan.test.product.domain.exception.ProductServiceException;
import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Product;
import com.ivan.test.product.domain.model.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class GetProductTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private GetProduct getProduct;

    @Test
    void should_throw_manged_exception_given_a_get_product_parameters_when_product_repository_throws_an_exception() {
        //GIVEN
        assertThat(getProduct).isNotNull();
        final GetProductParameters getProductParameters = new GetProductParameters();
        final RuntimeException repositoryException = new RuntimeException("UNIT TEST");

        given(productRepository.find(getProductParameters))
            .willThrow(repositoryException);

        //WHEN
        final Throwable throwable = catchThrowable(() -> getProduct.apply(getProductParameters));

        //THEN
        assertThat(throwable).isNotNull()
            .isInstanceOf(ProductServiceException.class)
            .getCause().isNotNull().isEqualTo(repositoryException);

        then(productRepository).should().find(eq(getProductParameters));
    }

    @Test
    void should_throw_product_not_found_exception_given_a_get_product_parameters_when_product_repository_returns_empty_result() {
        //GIVEN
        assertThat(getProduct).isNotNull();
        final GetProductParameters getProductParameters = new GetProductParameters();

        given(productRepository.find(getProductParameters))
            .willReturn(Optional.empty());

        //WHEN
        final Throwable throwable = catchThrowable(() -> getProduct.apply(getProductParameters));

        //THEN
        assertThat(throwable).isNotNull()
                .isInstanceOf(ProductNotFoundException.class);

        then(productRepository).should().find(eq(getProductParameters));
    }

    @Test
    void should_return_product_given_get_product_parameters_when_product_repository_returns_a_product() {
        //GIVEN
        assertThat(getProduct).isNotNull();
        final GetProductParameters getProductParameters = new GetProductParameters();
        final Product storedProduct = new Product();

        given(productRepository.find(getProductParameters))
                .willReturn(Optional.of(storedProduct));

        //WHEN
        final Product response = getProduct.apply(getProductParameters);

        //THEN
        assertThat(response).isNotNull()
            .isEqualTo(storedProduct);

        then(productRepository).should().find(eq(getProductParameters));
    }
}
