package com.library.auth_microservice.service;

import com.library.auth_microservice.dto.AuthResponseDTO;
import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UpdateRequestDTO;
import com.library.auth_microservice.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO save(LoginRequestDTO loginRequestDTO);
    UserDTO getUser(Long id);
    List<UserDTO> getAllUsers();
    String deleteUser(Long id);
    AuthResponseDTO updateUser(Long id, UpdateRequestDTO updateRequestDTO);
    UserDTO getUserByEmail(String email);

}
