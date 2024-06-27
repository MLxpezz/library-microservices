package com.library.users_microservice.utils;

import com.library.users_microservice.entities.UserEntity;

public class UserMapper {

    public static UserEntity defaultUser(UserEntity user) {
        UserEntity newUser = UserEntity
                .builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .username(user.getUsername())
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .build();

        return newUser;
    }
}
