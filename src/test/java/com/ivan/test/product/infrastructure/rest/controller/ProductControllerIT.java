package com.ivan.test.product.infrastructure.rest.controller;

import com.ivan.test.product.domain.ProductRepository;
import com.ivan.test.product.infrastructure.repository.sql.ProductJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SqlGroup({
        @Sql(value = "classpath:data/reset.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "classpath:data/product_data.sql", executionPhase = BEFORE_TEST_METHOD)
})
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductJpaRepository productJpaRepository;


    @Test
    void should_return_not_found_given_a_request_when_the_product_is_not_into_the_repository() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .param("productId", "12")
            .param("brandId", "1")
            .param("timestamp", "1655200800000"))
        //THEN
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void should_return_a_product_given_a_request_when_the_product_exists_on_database() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/api/product")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .param("productId", "35455")
            .param("brandId", "1")
            .param("timestamp", "1655200800000"))
        //THEN
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.productId").value(35455))
            .andExpect(jsonPath("$.brandId").value(1))
            .andExpect(jsonPath("startDate").value("2020-06-14T15:00:00Z"))
            .andExpect(jsonPath("endDate").value("2020-06-14T18:30:00Z"))
            .andExpect(jsonPath("priority").value(1))
            .andExpect(jsonPath("price.amount").value(25.45))
            .andExpect(jsonPath("price.currency").value("EUR"))
            .andExpect(jsonPath("priceList").value(2));
    }
}
