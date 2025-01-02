package com.mercadolibre.social.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.social.dto.request.PostPromotionRequestDto;
import com.mercadolibre.social.dto.request.ProductDto;
import com.mercadolibre.social.dto.response.MessageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("IT-08 -> US-10 Llevar a cabo la publicación de un nuevo producto en promoción")
    public void givenPostPromotionRequestDto_whenPostPromotion_thenSuccess() throws Exception {
        //ARRANGE
        LocalDate currentDate = LocalDate.now();

        MessageDto messageDto = new MessageDto("The post with promotion with id 100 has been successfully created");
        ProductDto productDto = new ProductDto(100, "productName", "type", "brand", "color", "notes");

        PostPromotionRequestDto postPromotionRequestDto = new PostPromotionRequestDto(
                1,
                currentDate,
                productDto,
                1,
                10.00,
                true,
                0.5
        );


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ResultMatcher statusEsperado = status().isOk();
        ResultMatcher contentTypeEsperado = content().contentType("application/json");
        ResultMatcher bodyEsperado = content().json(objectMapper.writeValueAsString(messageDto));
//      ACT & ASSERT
        mockMvc.perform(post("/products/promo-post")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postPromotionRequestDto))) // Usar ObjectMapper configurado
                .andExpect(statusEsperado)
                .andExpect(contentTypeEsperado)
                .andExpect(bodyEsperado)
                .andDo(print());
    }
}