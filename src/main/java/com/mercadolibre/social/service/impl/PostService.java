package com.mercadolibre.social.service.impl;

import com.mercadolibre.social.dto.request.PostRequestDto;
import com.mercadolibre.social.entity.Post;
import com.mercadolibre.social.repository.IPostRepository;
import com.mercadolibre.social.repository.IUserRepository;
import com.mercadolibre.social.service.IPostService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        return "Se ha creado correctamente el post con id " + post.getId();
    }
}
