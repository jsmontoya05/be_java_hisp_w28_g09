package com.mercadolibre.social.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.FollowersByUserDto;
import com.mercadolibre.social.dto.response.UserCountFollowersDto;
import com.mercadolibre.social.dto.response.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

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

    @Test
    @DisplayName("IT-02 -> US-03: Obtener un listado de todos los usuarios que siguen a un determinado vendedor")
    public void givenUser_whenGetFollowers_thenReturnCorrectFollowersList() throws Exception {
        //ARRANGE
        Integer parametroEntrada = 3;
        FollowersByUserDto bodyEsperadoDTO = new FollowersByUserDto(3, "bob_jones", List.of(new UserDto(1,"john_doe_test"), new UserDto(2,"alice_smith_test")));
        ResultMatcher statusEsperado = status().isOk();
        ResultMatcher contentTypeEsperado = content().contentType("application/json");
        ResultMatcher bodyEsperado = content().json(objectMapper.writeValueAsString(bodyEsperadoDTO));
        // ACT & ASSERT
        mockMvc.perform(get("/users/{parametroEntrada}/followers/list", parametroEntrada))
                .andExpect(statusEsperado)
                .andExpect(contentTypeEsperado)
                .andExpect(bodyEsperado)
                .andDo(print());

    }
}