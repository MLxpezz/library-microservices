package com.library.users_microservice.service;

import com.library.users_microservice.entities.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity save(UserEntity user);
    UserEntity getUser(Long id);
    List<UserEntity> getAllUsers();
    String deleteUser(Long id);

}
