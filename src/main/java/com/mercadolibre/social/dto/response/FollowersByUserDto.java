package com.mercadolibre.social.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowersByUserDto {
    private Integer userId;
    private String userName;
    private List<UserDto> followers;

}
