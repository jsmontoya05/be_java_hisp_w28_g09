package com.mercadolibre.social.service;

import com.mercadolibre.social.dto.response.FollowUserResponseDto;

public interface IUserService {
    FollowUserResponseDto followUser(int userId, int userIdToFollow);
}
