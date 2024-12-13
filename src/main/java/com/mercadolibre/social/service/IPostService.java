package com.mercadolibre.social.service;

import com.mercadolibre.social.dto.request.PostPromotionRequestDto;
import com.mercadolibre.social.dto.request.PostRequestDto;

public interface IPostService {
    String createPost(PostRequestDto postRequestDTO);

    String createPostPromotion(PostPromotionRequestDto postPromotionRequestDto);
}
