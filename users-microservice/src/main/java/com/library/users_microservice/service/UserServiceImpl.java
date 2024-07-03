package com.library.users_microservice.service;

import com.library.users_microservice.dto.LoginRequestDTO;
import com.library.users_microservice.dto.UpdateRequestDTO;
import com.library.users_microservice.dto.UserDTO;
import com.library.users_microservice.entities.UserEntity;
import com.library.users_microservice.repository.UserRepository;
import com.library.users_microservice.utils.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO save(LoginRequestDTO loginRequestDTO) {
        UserEntity userEntity = UserMapper.defaultUser(loginRequestDTO);
        return UserMapper.entityToDTO(userRepository.save(userEntity));
    }

    @Override
    public UserDTO getUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return UserMapper.entityToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return UserMapper.listEntityToDTO(userRepository.findAll());
    }

    @Override
    public String deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        userRepository.delete(user);
        return "User with id: " + id +" deleted";
    }

    @Override
    public UserDTO updateUser(Long id, UpdateRequestDTO updateRequestDTO) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        user.setEmail(updateRequestDTO.email());
        user.setPassword(updateRequestDTO.password());
        return UserMapper.entityToDTO(userRepository.save(user));
    }
}
