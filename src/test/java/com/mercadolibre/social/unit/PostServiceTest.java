package com.mercadolibre.social.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.PostDetailsDTO;
import com.mercadolibre.social.dto.response.ProductResponseDTO;
import com.mercadolibre.social.dto.response.UserPostsResponseDTO;
import com.mercadolibre.social.entity.Post;
import com.mercadolibre.social.entity.Product;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.exception.BadRequestException;
import com.mercadolibre.social.exception.NotFoundException;
import com.mercadolibre.social.repository.impl.PostRepository;
import com.mercadolibre.social.repository.impl.ProductRepository;
import com.mercadolibre.social.repository.impl.UserRepository;
import com.mercadolibre.social.service.impl.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PostService postService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("UT-05: Verificar que el tipo de ordenamiento por fecha exista")
    void givenOrder_whenOrderExists_thenReturnOrderedPosts() {
        // ARRANGE
        String order = "date_desc";
        Integer userId = 1;
        Integer followedUserId = 2;

        User expectedUser = new User(userId, "John Doe", Set.of(followedUserId), Set.of());
        when(userRepository.findById(expectedUser.getId())).thenReturn(expectedUser);

        List<Post> expectedPosts = List.of(
                new Post(1, followedUserId, LocalDate.now(), 1, 1, 10.0, false, 0.0),
                new Post(2, followedUserId, LocalDate.now().minusDays(1), 2, 1, 10.0, false, 0.0)
        );
        when(postRepository.findAll()).thenReturn(expectedPosts);

        Integer productId1 = 1;
        Integer productId2 = 2;

        when(productRepository.findById(productId1)).thenReturn(
                new Product(
                1,
                "Name",
                "Type",
                "Brand",
                "Color",
                "Notes"
        ));

        when(productRepository.findById(productId2)).thenReturn(
                new Product(
                        2,
                        "Name",
                        "Type",
                        "Brand",
                        "Color",
                        "Notes"
                ));

        List<PostDetailsDTO> expectedPostDetailsDTO = List.of(
                new PostDetailsDTO(
                        followedUserId,
                        1,
                        LocalDate.now(),
                        new ProductResponseDTO(
                                1,
                                "Name",
                                "Type",
                                "Brand",
                                "Color",
                                "Notes"
                        ),
                        1,
                        10.0

                ),
                new PostDetailsDTO(
                        followedUserId,
                        2,
                        LocalDate.now().minusDays(1),
                        new ProductResponseDTO(
                                2,
                                "Name",
                                "Type",
                                "Brand",
                                "Color",
                                "Notes"
                        ),
                        1,
                        10.0

                )
        );

        UserPostsResponseDTO expectedPostsResponseDTO = new UserPostsResponseDTO(expectedUser.getId(), expectedPostDetailsDTO);

        // ACT
        UserPostsResponseDTO actualPostsResponseDTO = postService.getPostsByFollowedUsers(expectedUser.getId(), order);

        // ASSERT
        assertEquals(expectedPostsResponseDTO, actualPostsResponseDTO);

    }

    @Test
    @DisplayName("UT-05: Verificar que si no existe orden, lanza una excepción")
    void givenOrder_whenOrderNotExists_thenThrowException() {
        // ARRANGE
        String order = "";
        Integer userId = 1;
        Integer followedUserId = 2;

        User expectedUser = new User(userId, "John Doe", Set.of(followedUserId), Set.of());
        when(userRepository.findById(expectedUser.getId())).thenReturn(expectedUser);

        List<Post> expectedPosts = List.of(
                new Post(1, followedUserId, LocalDate.now(), 1, 1, 10.0, false, 0.0),
                new Post(2, followedUserId, LocalDate.now().minusDays(1), 2, 1, 10.0, false, 0.0)
        );
        when(postRepository.findAll()).thenReturn(expectedPosts);

        String messageExpected = "Parametro no valido, debe ingresar 'name_desc' o 'name_asc' para que sea valido";

        // ACT & ASSERT
        Exception exception = assertThrows(
                BadRequestException.class,
                () -> postService.getPostsByFollowedUsers(expectedUser.getId(), order));

        // ASSERT
        assertEquals(messageExpected, exception.getMessage());


    }

    @Test
    @DisplayName("UT-06: Verificar el correcto ordenamiento ascendente por fecha")
    void givenUser_whenGetPostsByFollowedUsersAsc_thenReturnUserPostsResponseDTOSortedByDateAsc(){
        //Arrange
        int id = 1;
        int followedUserId = 2;
        String order = "date_asc";

        User expectedUser = new User(id, "John Doe", Set.of(followedUserId), Set.of());
        when(userRepository.findById(expectedUser.getId())).thenReturn(expectedUser);

        List<Post> expectedPosts = List.of(
                new Post(1, followedUserId, LocalDate.now(), 1, 1, 10.0, false, 0.0),
                new Post(2, followedUserId, LocalDate.now().minusDays(1), 2, 1, 10.0, false, 0.0)
        );
        when(postRepository.findAll()).thenReturn(expectedPosts);

        int productId1 = 1;
        int productId2 = 2;

        when(productRepository.findById(productId1)).thenReturn(
                new Product(1,"Product 1","Type","Brand","Color","Notes"));

        when(productRepository.findById(productId2)).thenReturn(
                new Product(2,"Product 2","Type","Brand","Color","Notes"));

        List<PostDetailsDTO> expectedPostDetailsDTO = List.of(
                new PostDetailsDTO(followedUserId,1,LocalDate.now(),
                        new ProductResponseDTO(1,"Product 1","Type","Brand","Color","Notes"),
                        1,10.0 ),
                new PostDetailsDTO(followedUserId,2,LocalDate.now().minusDays(1),
                        new ProductResponseDTO(2,"Product 2","Type","Brand","Color","Notes"),
                        1,10.0));

        UserPostsResponseDTO expectedPostsResponseDTO = new UserPostsResponseDTO(expectedUser.getId(), expectedPostDetailsDTO);

        //Act

        UserPostsResponseDTO result = postService.getPostsByFollowedUsers(id, order);

        //Assert

        assertNotNull(result);
        assertEquals(expectedPostsResponseDTO.getPosts().size(), result.getPosts().size());
        assertEquals("Product 2", result.getPosts().getFirst().getProduct().getProductName());


        verify(userRepository, times(1)).findById(anyInt());
        verify(postRepository, times(1)).findAll();
        verify(productRepository, times(expectedPosts.size())).findById(anyInt());
    }

    @Test
    @DisplayName("UT-06: Verificar el correcto ordenamiento descendente por fecha")
    void givenUser_whenGetPostsByFollowedUsersDesc_thenReturnUserPostsResponseDTOSortedByDateDesc(){
        //Arrange
        int id = 1;
        int followedUserId = 2;
        String order = "date_desc";

        User expectedUser = new User(id, "John Doe", Set.of(followedUserId), Set.of());
        when(userRepository.findById(expectedUser.getId())).thenReturn(expectedUser);

        List<Post> expectedPosts = List.of(
                new Post(1, followedUserId, LocalDate.now(), 1, 1, 10.0, false, 0.0),
                new Post(2, followedUserId, LocalDate.now().minusDays(1), 2, 1, 10.0, false, 0.0)
        );
        when(postRepository.findAll()).thenReturn(expectedPosts);

        int productId1 = 1;
        int productId2 = 2;

        when(productRepository.findById(productId1)).thenReturn(
                new Product(1,"Product 1","Type","Brand","Color","Notes"));

        when(productRepository.findById(productId2)).thenReturn(
                new Product(2,"Product 2","Type","Brand","Color","Notes"));

        List<PostDetailsDTO> expectedPostDetailsDTO = List.of(
                new PostDetailsDTO(followedUserId,1,LocalDate.now(),
                        new ProductResponseDTO(1,"Product 1","Type","Brand","Color","Notes"),
                        1,10.0 ),
                new PostDetailsDTO(followedUserId,2,LocalDate.now().minusDays(1),
                        new ProductResponseDTO(2,"Product 2","Type","Brand","Color","Notes"),
                        1,10.0));

        UserPostsResponseDTO expectedPostsResponseDTO = new UserPostsResponseDTO(expectedUser.getId(), expectedPostDetailsDTO);

        //Act

        UserPostsResponseDTO result = postService.getPostsByFollowedUsers(id, order);

        //Assert

        assertNotNull(result);
        assertEquals(expectedPostsResponseDTO.getPosts().size(), result.getPosts().size());
        assertEquals("Product 1", result.getPosts().getFirst().getProduct().getProductName());


        verify(userRepository, times(1)).findById(anyInt());
        verify(postRepository, times(1)).findAll();
        verify(productRepository, times(expectedPosts.size())).findById(anyInt());
    }

}