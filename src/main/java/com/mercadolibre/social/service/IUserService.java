package com.mercadolibre.social.service;

public interface IUserService {
    String unfollowUser(int userId, int userIdToUnfollow);
}
