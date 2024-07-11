package com.library.auth_microservice.utils;

import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;

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
