package com.mercadolibre.social.service.impl;
import com.mercadolibre.social.dto.response.FollowUserResponseDto;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.exception.ConflictException;
import com.mercadolibre.social.exception.InvalidFormatException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.FollowedByUserDto;
import com.mercadolibre.social.dto.response.FollowersByUserDto;
import com.mercadolibre.social.dto.response.UserCountFollowersDto;
import com.mercadolibre.social.dto.response.UserDto;
import com.mercadolibre.social.exception.IllegalOperationException;
import com.mercadolibre.social.repository.IUserRepository;
import com.mercadolibre.social.service.IUserService;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository, ObjectMapper objectMapper) {
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

    public FollowersByUserDto followersByUser(Integer id) { //Retorna el listado de seguidores de un usuario especifico

        User user = userRepository.findById(id);
        List<User> followers = userRepository.findUsersByIds(user.getFollowers());

        return new FollowersByUserDto(
                user.getId(),
                user.getUsername(),
                followers.stream()
                        .map( us -> new UserDto(
                                us.getId(),
                                us.getUsername()
                        ))
                        .toList()
        );
    }

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

    @Override
    public FollowedByUserDto followedByUser(Integer id) { //Retorna el listado de los vendedores seguidos por un usuario específico

        User user = userRepository.findById(id);
        List<User> followers = userRepository.findUsersByIds(user.getFollowed());

        return new FollowedByUserDto(
                user.getId(),
                user.getUsername(),
                followers.stream()
                        .map( us -> new UserDto(
                                us.getId(),
                                us.getUsername()
                        ))
                        .toList()
        );
    }

    @Override
    public UserCountFollowersDto getCountFollowers(int userId) {
        User user = userRepository.findById(userId);
        Integer followersCount = user.getFollowers().size();
        return new UserCountFollowersDto(String.valueOf(user.getId()),user.getUsername(),followersCount);
    }
}
