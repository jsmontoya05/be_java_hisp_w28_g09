package com.mercadolibre.social.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Order(1)
    @DisplayName("IT-01: Validar acción de “Follow” (seguir) a un determinado usuario")
    public void givenUser_whenFollowAnotherUser_thenUserIsFollowed() throws Exception {
        // ARRANGE

        // Parametro del seguidor
        Integer followerParam = 1;
        // Parametro del seguido
        Integer followedParam = 2;
        FollowUserResponseDto response = new FollowUserResponseDto(followerParam, followedParam);
        ResultMatcher expectedStatus = status().isOk();
        ResultMatcher expectedContentType = content().contentType(MediaType.APPLICATION_JSON);
        ResultMatcher expectedBody = content().json(objectMapper.writeValueAsString(response));


        // ACT AND ASSERT
        mockMvc.perform(post("/users/{paramFollower}/follow/{followerParam}", followerParam, followedParam))
                .andExpectAll(
                        expectedStatus,
                        expectedContentType,
                        expectedBody
                ).andDo(print());
    }



    @Test
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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

    @Test
    @Order(5)
    @DisplayName("IT-03 -> US-07: Poder realizar la acción de “Unfollow” (dejar de seguir) a un determinado vendedor.")
    public void givenUser_whenUnFollow_thenReturnCorrectMessage() throws Exception {
        //ARRANGE
        int userId = 1;
        int userIdToUnfollow = 4;
        MessageDto expected = new MessageDto("User " + userId + " successfully unfollowed User " + userIdToUnfollow);
        ResultMatcher statusExpected = status().isOk();
        ResultMatcher contentTypeExpected = content().contentType("application/json");
        ResultMatcher bodyExpected = content().json(objectMapper.writeValueAsString(expected));

        // ACT & ASSERT
        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToUnfollow}", userId, userIdToUnfollow))
                .andExpectAll(statusExpected, contentTypeExpected, bodyExpected)
                .andDo(print());

    }
}