package com.mercadolibre.social.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.FollowUserResponseDto;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.exception.NotFoundException;
import com.mercadolibre.social.repository.impl.PostRepository;
import com.mercadolibre.social.repository.impl.UserRepository;
import com.mercadolibre.social.service.impl.PostService;
import com.mercadolibre.social.service.impl.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DisplayName("UT-01 Verificar que el usuario a seguir exista.")
    public void givenUser_whenCheckFollowUser_thenUserExists() {
        //ARRANGE
        FollowUserResponseDto expectedResponse = new FollowUserResponseDto(2,1);
        // el seguidor.
        Integer userFollowerIdParam = 2;
        // el seguido.
        Integer userFollowedIdParam = 1;
        //mock del seguido
        User followedMock = new User(1, "john_doe_test", new HashSet<>(Set.of(3)), new HashSet<>());
        //mock del seguidor
        User followerMock = new User(2, "alice_smith_test", new HashSet<>(Set.of(3)), new HashSet<>());

        //ACT
        Mockito.when(userRepository.findById(userFollowedIdParam)).thenReturn(followedMock);
        Mockito.when(userRepository.findById(userFollowerIdParam)).thenReturn(followerMock);
        FollowUserResponseDto responseObtained =  userService.followUser(userFollowerIdParam, userFollowedIdParam);

        //ASSERT
        assertEquals(expectedResponse, responseObtained);
    }

    @Test
    @DisplayName("UT-01 Verificar que el usuario a seguir no exista.")
    public void givenUser_whenCheckFollowUser_thenUserDoesNotExist() {
        //ARRANGE

        // el seguidor.
        Integer userFollowerIdParam = 2;
        // el seguido.
        Integer userFollowedIdParam = 10;

        String expectedResponse = "User not found with ID: " + userFollowedIdParam;
        //mock del seguido
        Integer followedMockId = userFollowedIdParam;
        //mock del seguidor
        User followerMock = new User(2, "alice_smith_test", new HashSet<>(Set.of(3)), new HashSet<>());

        //ACT
        Mockito.when(userRepository.findById(userFollowedIdParam)).thenThrow(new NotFoundException("User not found with ID: " + followedMockId));
        Mockito.when(userRepository.findById(userFollowerIdParam)).thenReturn(followerMock);
        NotFoundException exceptionObtained = assertThrows(NotFoundException.class, ()-> userService.followUser(userFollowerIdParam, userFollowedIdParam));

        //ASSERT
        assertEquals(expectedResponse, exceptionObtained.getMessage());

    }
}