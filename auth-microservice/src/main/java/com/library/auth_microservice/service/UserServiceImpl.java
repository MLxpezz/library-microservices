package com.library.auth_microservice.service;

import com.library.auth_microservice.dto.AuthResponseDTO;
import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UpdateRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;
import com.library.auth_microservice.exceptions.PasswordNotMatchesException;
import com.library.auth_microservice.repository.UserRepository;
import com.library.auth_microservice.utils.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthService authService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

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
    public AuthResponseDTO updateUser(Long id, UpdateRequestDTO updateRequestDTO) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        if(!passwordEncoder.matches(updateRequestDTO.password(), user.getPassword())){
            throw new PasswordNotMatchesException("La contraseña actual no es correcta.");
        }

        if(!updateRequestDTO.newPassword().isEmpty()) {
            if(updateRequestDTO.newPassword().length() < 8) {
                throw new IllegalArgumentException("La nueva contraseña debe tener minimo 8 caracteres.");
            }
            user.setPassword(passwordEncoder.encode(updateRequestDTO.newPassword()));
        }

        user.setEmail(updateRequestDTO.email());

        UserEntity newUser = userRepository.save(user);

        String newOrOldPassword = updateRequestDTO.newPassword().isEmpty() ? updateRequestDTO.password() : updateRequestDTO.newPassword();

        AuthResponseDTO authResponseDTO = authService.login(newUser.getEmail(), newOrOldPassword);

        return authResponseDTO;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
       UserEntity user = userRepository
               .findUserEntityByEmail(email).orElseThrow(() -> new EntityNotFoundException("El usuario con email: " + email + " no se encontro o no existe."));

       return UserMapper.entityToDTO(user);
    }
}
