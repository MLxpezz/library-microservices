package com.library.auth_microservice.service;

import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UpdateRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;
import com.library.auth_microservice.repository.UserRepository;
import com.library.auth_microservice.utils.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO save(LoginRequestDTO loginRequestDTO) {
        UserEntity userEntity = UserMapper.defaultUser(loginRequestDTO);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
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

    @Override
    public UserDTO getUserByEmail(String email) {
       UserEntity user = userRepository
               .findUserEntityByEmail(email).orElseThrow(() -> new EntityNotFoundException("El usuario con email: " + email + " no se encontro o no existe."));

       return UserMapper.entityToDTO(user);
    }
}
