package com.mercadolibre.social.service;

import com.mercadolibre.social.dto.response.FollowersByUserDto;

public interface IUserService {

    FollowersByUserDto followersByUser(Integer id);
    String unfollowUser(int userId, int userIdToUnfollow);
}
