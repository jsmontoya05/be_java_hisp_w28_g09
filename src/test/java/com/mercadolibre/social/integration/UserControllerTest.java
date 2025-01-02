package com.mercadolibre.social.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.UserCountFollowersDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("IT-05 -> US-02: Obtener el resultado de la cantidad de usuarios que siguen a un determinado vendedor")
    void givenUserId_whenGetCountFollowers_thenReturnCorrectFollowersCount() throws Exception{
        // ARRANGE
        int userId = 3;
        UserCountFollowersDto response = new UserCountFollowersDto(String.valueOf(userId), "bob_jones", 2);

        ResultMatcher expectedStatus = status().isOk();
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);
        ResultMatcher expectedBody = content().json(objectMapper.writeValueAsString(response));

        // ACT & ASSERT
        mockMvc.perform(get("/users/{userId}/followers/count", userId))
                .andExpectAll(expectedStatus, expectedContentType, expectedBody)
                .andDo(print());

    }

    @Test
    @DisplayName("Carga de JSON segun el contexto.")
    public void pruebaTest() throws Exception {
        //ARRANGE
        UserCountFollowersDto bodyEsperadoDTO = new UserCountFollowersDto("1", "john_doe_test", 0);
        Integer parametroEntrada = 1;
        ResultMatcher statusEsperado = status().isOk();
        ResultMatcher contentTypeEsperado = content().contentType("application/json");
        ResultMatcher bodyEsperado = content().json(objectMapper.writeValueAsString(bodyEsperadoDTO));

        // ACT & ASSERT
        mockMvc.perform(get("/users/{parametroEntrada}/followers/count", parametroEntrada))
                .andExpect(statusEsperado)
                .andExpect(contentTypeEsperado)
                .andExpect(bodyEsperado)
                .andDo(print());
    }
}