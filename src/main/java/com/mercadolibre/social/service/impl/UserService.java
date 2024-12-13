package com.mercadolibre.social.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.social.dto.response.FollowersByUserDto;
import com.mercadolibre.social.dto.response.UserDto;
import com.mercadolibre.social.entity.User;
import com.mercadolibre.social.repository.IUserRepository;
import com.mercadolibre.social.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
    }

    @Override
    public FollowersByUserDto followersByUser(Integer id) { //Retorna el listado de seguidores de un usuario especifico

        User user = userRepository.findById(id);
        List<User> followers = userRepository.followersByUser(user.getFollowers());
        System.out.println(followers);

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
}
