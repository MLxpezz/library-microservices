package com.library.auth_microservice;

import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;

import java.util.List;

public class TestDataProvider {

    public static UserEntity userEntityProvider() {
        return UserEntity.builder()
                .id(1L)
                .email("mauricio@gmail.com")
                .password("encodedPassword")
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .isCredentialsNonExpired(true)
                .build();
    }

    public static List<UserEntity> userEntityListProvider() {
        return List.of(
                UserEntity.builder()
                        .id(1L)
                        .email("mauricio@gmail.com")
                        .password("encodedPassword")
                        .isAccountNonExpired(true)
                        .isAccountNonLocked(true)
                        .isEnabled(true)
                        .isCredentialsNonExpired(true)
                        .build(),
                UserEntity.builder()
                        .id(2L)
                        .email("prueba@gmail.com")
                        .password("encodedPasswordprueba")
                        .isAccountNonExpired(true)
                        .isAccountNonLocked(true)
                        .isEnabled(true)
                        .isCredentialsNonExpired(true)
                        .build()
        );
    }

    public static LoginRequestDTO loginRequestDTOProvider() {
        return LoginRequestDTO.builder()
                .email("mauricio@gmail.com")
                .password("12345678")
                .build();
    }

    public static UserDTO userDTOProvider() {
        return UserDTO.builder()
                .id(1L)
                .email("mauricio@gmail.com")
                .build();
    }
}
