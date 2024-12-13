package com.mercadolibre.social.service;

import com.mercadolibre.social.dto.request.PostPromotionRequestDto;
import com.mercadolibre.social.dto.request.PostRequestDto;
import com.mercadolibre.social.dto.response.ProductCountPromoPostDto;

public interface IPostService {
    String createPost(PostRequestDto postRequestDTO);

    ProductCountPromoPostDto getCountPromoPost(int userId);

    String createPostPromotion(PostPromotionRequestDto postPromotionRequestDto);
}
