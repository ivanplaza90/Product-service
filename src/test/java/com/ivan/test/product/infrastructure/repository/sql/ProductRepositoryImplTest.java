package com.ivan.test.product.infrastructure.repository.sql;

import com.ivan.test.product.domain.model.GetProductParameters;
import com.ivan.test.product.domain.model.Product;
import com.ivan.test.product.infrastructure.repository.sql.mapper.EntityMapper;
import com.ivan.test.product.infrastructure.repository.sql.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    private static final int PRODUCT_ID = 1;
    private static final int BRAND_ID = 2;
    private static final Date TIMESTAMP = new Date();
    private static final int PRICE_LIST = 1;
    private static final int PRIORITY = 5;
    private static final String EUR_CURRENCY = "EUR";
    private static final BigDecimal PRICE = BigDecimal.valueOf(35.75);
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
    public static final String START_DATE = "2020-06-14-00.00.00";
    public static final String END_DATE = "2020-12-31-23.59.59";

    @Mock
    private ProductJpaRepository productJpaRepository;
    @Spy
    private EntityMapper entityMapper = Mappers.getMapper(EntityMapper.class);

    @InjectMocks
    private ProductRepositoryImpl productRepositoryImpl;


    @Test
    void should_throws_an_exception_given_get_product_parameters_when_jpa_repository_throws_an_exception() {
        //GIVEN
        assertThat(productRepositoryImpl).isNotNull();
        final RuntimeException jpaRepositoryException = new RuntimeException("UNIT TEST");

        given(productJpaRepository
            .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                PRODUCT_ID, BRAND_ID, TIMESTAMP, TIMESTAMP)).willThrow(jpaRepositoryException);

        //WHEN
        final Throwable throwable = catchThrowable(() -> productRepositoryImpl.find(mockGetProductParameters()));

        //THEN
        assertThat(throwable).isNotNull()
                        .isEqualTo(jpaRepositoryException);
        then(productJpaRepository).should()
            .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(eq(PRODUCT_ID), eq(BRAND_ID), eq(TIMESTAMP), eq(TIMESTAMP));
    }

    @Test
    void should_return_empty_given_get_product_parameters_when_jpa_repository_returns_empty() {
        //GIVEN
        assertThat(productRepositoryImpl).isNotNull();

        given(productJpaRepository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        PRODUCT_ID, BRAND_ID, TIMESTAMP, TIMESTAMP)).willReturn(Optional.empty());

        //WHEN
        final Optional<Product> response = productRepositoryImpl.find(mockGetProductParameters());

        //THEN
        assertThat(response).isNotNull().isEmpty();
        then(productJpaRepository).should()
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(eq(PRODUCT_ID), eq(BRAND_ID), eq(TIMESTAMP), eq(TIMESTAMP));
    }

    @Test
    void should_return_a_product_given_get_product_parameters_when_jpa_repository_returns_a_result() throws ParseException {
        //GIVEN
        assertThat(productRepositoryImpl).isNotNull();
        final ProductEntity storedProduct = mockProductEntity();

        given(productJpaRepository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        PRODUCT_ID, BRAND_ID, TIMESTAMP, TIMESTAMP)).willReturn(Optional.of(storedProduct));

        //WHEN
        final Optional<Product> response = productRepositoryImpl.find(mockGetProductParameters());

        //THEN
        assertThat(response).isNotNull().isNotEmpty().get()
            .hasFieldOrPropertyWithValue("productId", PRODUCT_ID)
            .hasFieldOrPropertyWithValue("brandId", BRAND_ID)
            .hasFieldOrPropertyWithValue("startDate", DATE_FORMAT.parse(START_DATE))
            .hasFieldOrPropertyWithValue("endDate", DATE_FORMAT.parse(END_DATE))
            .hasFieldOrPropertyWithValue("priceList", PRICE_LIST)
            .hasFieldOrPropertyWithValue("priority", PRIORITY)
            .hasFieldOrPropertyWithValue("price.amount", PRICE)
            .hasFieldOrPropertyWithValue("price.currency", EUR_CURRENCY);

        then(productJpaRepository).should()
            .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(eq(PRODUCT_ID), eq(BRAND_ID), eq(TIMESTAMP), eq(TIMESTAMP));
        then(entityMapper).should().mapToProduct(eq(storedProduct));
    }

    private GetProductParameters mockGetProductParameters() {
        return GetProductParameters.builder()
            .productId(PRODUCT_ID)
            .brandId(BRAND_ID)
            .timestamp(TIMESTAMP)
            .build();
    }

    private ProductEntity mockProductEntity() throws ParseException {
        return ProductEntity.builder()
            .productId(PRODUCT_ID)
            .brandId(BRAND_ID)
            .startDate(DATE_FORMAT.parse(START_DATE))
            .endDate(DATE_FORMAT.parse(END_DATE))
            .priceList(PRICE_LIST)
            .priority(PRIORITY)
            .price(PRICE)
            .currency(EUR_CURRENCY)
            .build();
    }
}
