package com.library.users_microservice.utils;

import com.library.users_microservice.dto.LoginRequestDTO;
import com.library.users_microservice.dto.UserDTO;
import com.library.users_microservice.entities.UserEntity;

import java.util.Collections;
import java.util.List;

public class UserMapper {

    public static UserEntity defaultUser(LoginRequestDTO loginRequestDTO) {
        return UserEntity
                .builder()
                .email(loginRequestDTO.email())
                .password(loginRequestDTO.password())
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .build();
    }

    public static UserDTO entityToDTO(UserEntity user) {
        return UserDTO
                .builder()
                .email(user.getEmail())
                .id(user.getId())
                .build();
    }

    public static List<UserDTO> listEntityToDTO(List<UserEntity> users) {
        return users.stream()
                .map(UserMapper::entityToDTO)
                .toList();
    }
}
