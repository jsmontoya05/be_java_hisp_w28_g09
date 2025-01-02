package com.mercadolibre.social.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.FollowUserResponseDto;
import com.mercadolibre.social.exception.NotFoundException;
import com.mercadolibre.social.dto.response.MessageDto;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.dto.response.UserCountFollowersDto;

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
import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
  
    @Test
    @DisplayName("UT-02: Verificar que el usuario a dejar de seguir exista")
    void givenUserAndUserToUnfollow_whenUnfollow_thenReturnCorrectMessage() {
        // ARRANGE
        int userId = 1;
        int userIdToUnfollow = 2;

        User existingUser = new User(1, "John Doe", new HashSet<>(Set.of(2)), new HashSet<>());
        User userToUnfollow = new User(2, "Jane Smith", new HashSet<>(), new HashSet<>(Set.of(1)));

        MessageDto messageExpected = new MessageDto("User " + userId + " successfully unfollowed User " + userIdToUnfollow);
        when(userRepository.findById(userId)).thenReturn(existingUser);
        when(userRepository.findById(userIdToUnfollow)).thenReturn(userToUnfollow);

        // ACT
        MessageDto actualMessage = userService.unfollowUser(userId, userIdToUnfollow);

        // ASSERT
        assertEquals(messageExpected, actualMessage);
    }

    @Test
    @DisplayName("UT-02: Verificar que se lance una excepción si el usuario a dejar de seguir no existe.")
    void givenUserAndNonExistentUserToUnfollow_whenUnfollow_thenThrowException() {

        // ARRANGE
        int userId = 1;
        int userIdToUnfollow = 2;

        User existingUser = new User(1, "John Doe", new HashSet<>(Set.of(2)), new HashSet<>());

        String messageExpected = "User not found with ID: " + userIdToUnfollow;
        when(userRepository.findById(userId)).thenReturn(existingUser);
        when(userRepository.findById(userIdToUnfollow))
                .thenThrow(new NotFoundException("User not found with ID: " + userIdToUnfollow));

        // ACT
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> userService.unfollowUser(userId, userIdToUnfollow)
        );

        // ASSERT
        assertEquals(messageExpected, exception.getMessage());
    }

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