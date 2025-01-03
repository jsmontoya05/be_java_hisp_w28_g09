package com.mercadolibre.social.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mercadolibre.social.dto.request.PostPromotionRequestDto;
import com.mercadolibre.social.dto.request.PostRequestDto;
import com.mercadolibre.social.dto.request.ProductDto;
import com.mercadolibre.social.dto.response.MessageDto;
import com.mercadolibre.social.dto.response.ProductCountPromoPostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    @DisplayName("IT-08 -> US-10 Llevar a cabo la publicación de un nuevo producto en promoción")
    public void givenPostPromotionRequestDto_whenPostPromotion_thenSuccess() throws Exception {
        // ARRANGE
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
        // ACT & ASSERT
        mockMvc.perform(post("/products/promo-post")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postPromotionRequestDto)))
                .andExpect(statusEsperado)
                .andExpect(contentTypeEsperado)
                .andExpect(bodyEsperado)
                .andDo(print());
    }

    @Test
    @DisplayName("IT-07 -> US-11: Obtener la cantidad de productos en promoción de un determinado vendedor")
    public void givenSellerWithPromotionalProducts_whenGetPromotionProductCount_thenReturnCorrectCount() throws Exception {
        //ARRANGE

        ProductCountPromoPostDto response = new ProductCountPromoPostDto(1, "john_doe_test", 1);
        ResultMatcher expectedStatus = status().isOk();
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);
        ResultMatcher expectedBody = content().json(objectMapper.writeValueAsString(response));
        //ACT AND ASSERT
        mockMvc.perform(get("/products/promo-post/count")
                .param("user_id", "1"))
                .andExpectAll(
                        expectedStatus,
                        expectedContentType,
                        expectedBody
                ).andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("IT-06 -> US-05 Dar de alta una nueva publicación")
    public void givenPostRequestDto_whenPost_thenSuccess() throws Exception {
        // ARRANGE
        LocalDate currentDate = LocalDate.now();

        MessageDto messageDto = new MessageDto("The post with id 101 has been created correctly");
        ProductDto productDto = new ProductDto(100, "productName", "type", "brand", "color", "notes");

        PostRequestDto postRequestDto = new PostRequestDto(
                1,
                currentDate,
                productDto,
                1,
                10.00
        );


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        ResultMatcher statusEsperado = status().isOk();
        ResultMatcher contentTypeEsperado = content().contentType("application/json");
        ResultMatcher bodyEsperado = content().json(objectMapper.writeValueAsString(messageDto));
        // ACT & ASSERT
        mockMvc.perform(post("/products/post")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(statusEsperado)
                .andExpect(contentTypeEsperado)
                .andExpect(bodyEsperado)
                .andDo(print());
    }

}
