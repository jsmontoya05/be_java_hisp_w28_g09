package com.mercadolibre.social.service.impl;

import com.mercadolibre.social.repository.IPostRepository;
import com.mercadolibre.social.service.IPostService;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {

    private final IPostRepository postRepository;

    public PostService(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }
}
