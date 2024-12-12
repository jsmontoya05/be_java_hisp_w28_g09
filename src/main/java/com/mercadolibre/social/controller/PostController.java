package com.mercadolibre.social.controller;

import com.mercadolibre.social.service.IPostService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }
}
