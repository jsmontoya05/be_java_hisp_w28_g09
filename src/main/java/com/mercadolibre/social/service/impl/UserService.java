package com.mercadolibre.social.service.impl;

import com.mercadolibre.social.dto.response.FollowUserResponseDto;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.exception.ConflictException;
import com.mercadolibre.social.exception.InvalidFormatException;
import com.mercadolibre.social.exception.NotFoundException;
import com.mercadolibre.social.repository.IUserRepository;
import com.mercadolibre.social.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public FollowUserResponseDto followUser(int userId, int userIdToFollow) {
        if (userId <= 0 || userIdToFollow <= 0) {
            throw new InvalidFormatException("Id is invalid.");
        }
        User user = userRepository.findById(userId);
        Optional<Integer> isFollowed = user.getFollowed().stream()
                .filter(userFollowedId -> userFollowedId.equals(userIdToFollow))
                .findFirst();
        if (isFollowed.isPresent()) {
            throw new ConflictException("The user is already followed.");
        }
        User userToFollow = userRepository.findById(userIdToFollow);
        Optional<Integer> isFollower = userToFollow.getFollowers().stream()
                .filter(userFollowerId -> userFollowerId.equals(userId))
                .findFirst();
        if (isFollower.isPresent()){
            throw new ConflictException("The user is already follower. ");
        }
        user.getFollowed().add(userIdToFollow);
        userToFollow.getFollowers().add(userId);
        return new FollowUserResponseDto(userId, userIdToFollow);
    }
}
