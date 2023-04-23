package com.ivan.test.product.infrastructure.rest.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;


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
}
