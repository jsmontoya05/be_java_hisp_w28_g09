package com.mercadolibre.social.service.impl;

import com.mercadolibre.social.dto.request.PostPromotionRequestDto;
import com.mercadolibre.social.dto.request.PostRequestDto;
import com.mercadolibre.social.dto.response.ProductCountPromoPostDto;
import com.mercadolibre.social.entity.Post;

import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.exception.BadRequestException;
import com.mercadolibre.social.repository.IPostRepository;
import com.mercadolibre.social.repository.IUserRepository;
import com.mercadolibre.social.service.IPostService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService implements IPostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    public PostService(IPostRepository postRepository, IUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createPost(PostRequestDto postRequestDTO) {
        LocalDate date = postRequestDTO.getDate();

        //Se valida si el usuario ingresado existe en el sistema
        userRepository.findById(postRequestDTO.getUserId());
        // Crea el objeto Post
        Post post = new Post();
        post.setUserId(postRequestDTO.getUserId());
        post.setDate(date);
        post.setProductId(postRequestDTO.getProduct().getProductId());
        post.setCategory(postRequestDTO.getCategory());
        post.setPrice(postRequestDTO.getPrice());
        post.setHasPromo(false);
        post.setDiscount(0.0);


        post = postRepository.save(post);

        // Retorna el mensaje de éxito
        return "The post with id "+ post.getId() + " has been created correctly";
    }

    @Override
    public ProductCountPromoPostDto getCountPromoPost(int userId) {
        // TODOS LOS POST
        List<Post> posts = postRepository.findAll();

        // FILTRAR POR USUARIO
        List<Post> postsFilterByUser = posts.stream()
                .filter(post -> post.getUserId().equals(userId))
                .toList();

        // FILTRAR LOS POST QUE TENGA PROMO
        List<Post> postFilterByUserAndPromoPost = postsFilterByUser.stream().
                filter(Post::getHasPromo)
                .toList();

        // CONTAR LOS POST QUE TENGA PROMO
        Integer countPromoPost = postFilterByUserAndPromoPost.size();

        // CONSEGUIR EL USERNAME O EL USUARIO POR EL ID
        User user = userRepository.findById(userId);

        // ARMAR LA RESPUESTA EN EL FORMATO DTO
        return new ProductCountPromoPostDto(
                user.getId(), user.getUsername(), countPromoPost
        );
    }

    public String createPostPromotion(PostPromotionRequestDto postPromotionRequestDto) {
        Integer userId = postPromotionRequestDto.getUserId();

        userRepository.findById(postPromotionRequestDto.getUserId());

        // Validación del descuento: debe estar entre 0 y 1 (porcentaje entre 0% y 100%)
        if (postPromotionRequestDto.getHasPromo() != null && postPromotionRequestDto.getHasPromo() &&
                (postPromotionRequestDto.getDiscount() == null || postPromotionRequestDto.getDiscount() < 0 || postPromotionRequestDto.getDiscount() > 1)) {
            throw new BadRequestException("The discount must be between 0 and 1");
        }

        // Crea el objeto Post
        LocalDate date = postPromotionRequestDto.getDate();
        Post post = new Post();
        post.setUserId(userId);
        post.setDate(date);
        post.setProductId(postPromotionRequestDto.getProduct().getProductId());
        post.setCategory(postPromotionRequestDto.getCategory());
        post.setPrice(postPromotionRequestDto.getPrice());
        post.setHasPromo(postPromotionRequestDto.getHasPromo());
        post.setDiscount(postPromotionRequestDto.getDiscount() != null ? postPromotionRequestDto.getDiscount() : 0.0);

        post = postRepository.save(post);

        return "The post with promotion with id " + post.getId() + " has been successfully created";
    }
}
