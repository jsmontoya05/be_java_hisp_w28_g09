package com.mercadolibre.social.service;

import com.mercadolibre.social.dto.response.FollowedByUserDto;
import com.mercadolibre.social.dto.response.FollowersByUserDto;

public interface IUserService {

    FollowersByUserDto followersByUser(Integer id);
    String unfollowUser(int userId, int userIdToUnfollow);
    FollowedByUserDto followedByUser(Integer id);
}
