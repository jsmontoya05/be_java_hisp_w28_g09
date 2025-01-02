package com.mercadolibre.social.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.UserCountFollowersDto;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.repository.impl.PostRepository;
import com.mercadolibre.social.repository.impl.UserRepository;
import com.mercadolibre.social.service.impl.PostService;
import com.mercadolibre.social.service.impl.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("UT-07: Verifica que la cantidad de seguidores de un usuario sea correcta.")
    void givenUser_whenGetCountFollowers_thenReturnCorrectFollowersCount() {
        // ARRANGE
        UserCountFollowersDto expectedFollowerCount = new UserCountFollowersDto(
                "1",
                "John Doe",
                2
        );

        User expectedUser = new User(1, "John Doe", Set.of(), Set.of(2, 3));

        when(userRepository.findById(1)).thenReturn(expectedUser);

        // ACT
        UserCountFollowersDto actualFollowerCount = userService.getCountFollowers(1);

        // ASSERT
        assertEquals(expectedFollowerCount, actualFollowerCount);
    }

    @Test
    @DisplayName("UT-07: Verifica que la cantidad de seguidores de un usuario no sea incorrecta.")
    void givenUser_whenGetCountFollowers_thenReturnIncorrectFollowersCount() {
        // ARRANGE
        UserCountFollowersDto expectedFollowerCount = new UserCountFollowersDto(
                "1",
                "John Doe",
                0
        );

        User expectedUser = new User(1, "John Doe", Set.of(), Set.of(2, 3));

        when(userRepository.findById(1)).thenReturn(expectedUser);

        // ACT
        UserCountFollowersDto actualFollowerCount = userService.getCountFollowers(1);

        // ASSERT
        assertNotEquals(expectedFollowerCount, actualFollowerCount);
    }
}