package com.mercadolibre.social.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.FollowersByUserDto;
import com.mercadolibre.social.dto.response.UserDto;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.exception.BadRequestException;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("UT-03: Verificar que el tipo de ordenamiento alfabético ascendente exista y sea correcto")
    void givenUser_whenGetCountFollowersSortedAsc_thenReturnCorrectFollowersCountSortedAsc(){
        //ARRANGE
        int id = 1;
        String order = "name_asc";

        User user = new User(1, "Pedro", new HashSet<>(789, 126), new HashSet<>(123,456));

        List<User> users = new ArrayList<>(Arrays.asList(new User(123, "juan", new HashSet<>(789, 126), new HashSet<>(123, 456)),
                new User(456, "pepito", new HashSet<>(789, 126), new HashSet<>(123, 456)),
                new User(789, "ana", new HashSet<>(789, 126), new HashSet<>(123, 456))));

        FollowersByUserDto expected = new FollowersByUserDto(1,"Pedro",
                List.of(new UserDto(123, "juan"),
                        new UserDto(456, "pepito"),
                        new UserDto(789,"ana")));

        //ACT
        when(userRepository.findById(anyInt())).thenReturn(user);
        when(userRepository.findUsersByIds(anySet())).thenReturn(users);

        FollowersByUserDto result = userService.followersByUser(id, order);

        //ASSERT
        assertNotNull(result, "El resultado no debe de ser nulo");
        assertEquals(expected.getFollowers().size(), result.getFollowers().size(), "No coincide la cantidad de seguidores");
        assertEquals(expected.getUserName(), result.getUserName(), "No coincide el nombre de los usuarios");
        assertEquals("ana",result.getFollowers().getFirst().getUserName(), "No se esta ordenando correctamente la lista de seguidores");

        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).findUsersByIds(anySet());
    }

    @Test
    @DisplayName("UT-03: Verificar que el tipo de ordenamiento alfabético descendente exista y sea correcto")
    void givenUser_whenGetCountFollowersSortedDesc_thenReturnCorrectFollowersCountSortedDesc(){
        //ARRANGE
        int id = 1;
        String order = "name_desc";

        User user = new User(1, "Pedro", new HashSet<>(789, 126), new HashSet<>(123,456));

        List<User> users = new ArrayList<>(Arrays.asList(new User(123, "juan", new HashSet<>(789, 126), new HashSet<>(123, 456)),
                new User(456, "pepito", new HashSet<>(789, 126), new HashSet<>(123, 456)),
                new User(789, "ana", new HashSet<>(789, 126), new HashSet<>(123, 456))));

        FollowersByUserDto expected = new FollowersByUserDto(1,"Pedro",
                List.of(new UserDto(123, "juan"),
                        new UserDto(456, "pepito"),
                        new UserDto(789,"ana")));

        //ACT
        when(userRepository.findById(anyInt())).thenReturn(user);
        when(userRepository.findUsersByIds(anySet())).thenReturn(users);

        FollowersByUserDto result = userService.followersByUser(id, order);

        //ASSERT
        assertNotNull(result, "El resultado no debe de ser nulo");
        assertEquals(expected.getFollowers().size(), result.getFollowers().size(), "No coincide la cantidad de seguidores");
        assertEquals(expected.getUserName(), result.getUserName(), "No coincide el nombre de los usuarios");
        assertEquals("pepito",result.getFollowers().getFirst().getUserName(), "No se esta ordenando correctamente la lista de seguidores");

        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).findUsersByIds(anySet());
    }

    @Test
    @DisplayName("UT-03: Verificar que el tipo de ordenamiento alfabético no exista y lance una excepcion")
    void givenUser_whenGetCountFollowersSortedIncorrect_thenReturnBadRequestException(){
        //ARRANGE
        int id = 1;
        String order = "incorrect";

        User user = new User(1, "Pedro", new HashSet<>(789, 126), new HashSet<>(123,456));

        List<User> users = new ArrayList<>(Arrays.asList(new User(123, "juan", new HashSet<>(789, 126), new HashSet<>(123, 456)),
                new User(456, "pepito", new HashSet<>(789, 126), new HashSet<>(123, 456)),
                new User(789, "ana", new HashSet<>(789, 126), new HashSet<>(123, 456))));

        //ACT
        when(userRepository.findById(anyInt())).thenReturn(user);
        when(userRepository.findUsersByIds(anySet())).thenReturn(users);

        //ASSERT
        assertThrows(BadRequestException.class, () -> userService.followersByUser(id,order), "Deberia lanzar una excepcion");

        verify(userRepository, times(1)).findById(anyInt());
        verify(userRepository, times(1)).findUsersByIds(anySet());
    }
}