package com.mercadolibre.social.controller;

import com.mercadolibre.social.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/users"))
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    //01, Poder realizar la acción de “Follow” (seguir) a un determinado usuario
    @PostMapping("/users/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable int userId, @PathVariable int userIdToFollow){
        return ResponseEntity.ok().body(userService.followUser(userId, userIdToFollow));
    }
}
