package com.library.users_microservice.service;

import com.library.users_microservice.dto.LoginRequestDTO;
import com.library.users_microservice.dto.UpdateRequestDTO;
import com.library.users_microservice.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO save(LoginRequestDTO loginRequestDTO);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
    String deleteUser(Long id);
    UserDTO updateUser(Long id, UpdateRequestDTO updateRequestDTO);

}
