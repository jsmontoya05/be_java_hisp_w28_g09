package com.mercadolibre.social.repository;

import com.mercadolibre.social.entity.User;

import java.util.List;

public interface IUserRepository {
    List<User> findAll();
    User save(User user);
    User findById(Integer id);

}
