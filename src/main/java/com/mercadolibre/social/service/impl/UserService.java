package com.mercadolibre.social.service.impl;

import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.exception.IllegalOperationException;
import com.mercadolibre.social.repository.IUserRepository;
import com.mercadolibre.social.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String unfollowUser(int userId, int userIdToUnfollow) {
        // Busca los usuarios correspondientes
        User user = userRepository.findById(userId);
        User userToUnfollow = userRepository.findById(userIdToUnfollow);

        // Valida si el usuario ya no sigue al otro
        if (!user.getFollowed().contains(userIdToUnfollow)) {
            throw new IllegalOperationException("User with ID " + userId + " is not following user with ID " + userIdToUnfollow);
        }

        // Realiza las actualizaciones en las listas
        user.getFollowed().remove(userIdToUnfollow);
        userToUnfollow.getFollowers().remove(userId);
        return "User " + userId + " successfully unfollowed User " + userIdToUnfollow;
    }
}
