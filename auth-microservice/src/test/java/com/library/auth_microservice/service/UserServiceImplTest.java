package com.library.auth_microservice.service;

import com.library.auth_microservice.dto.AuthResponseDTO;
import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UpdateRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;
import com.library.auth_microservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthService authService;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;

    private LoginRequestDTO loginRequestDTO;

    private UserDTO expectUser;

    private UpdateRequestDTO updateRequestDTO;

    @BeforeEach
    void setUp() {
        loginRequestDTO = LoginRequestDTO
                .builder()
                .email("mauricio@gmail.com")
                .password("12345678")
                .build();

        userEntity = UserEntity
                .builder()
                .id(1L)
                .email("mauricio@gmail.com")
                .password(passwordEncoder.encode("12345678"))
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isEnabled(true)
                .isCredentialsNonExpired(true)
                .build();

        expectUser = UserDTO
                .builder()
                .id(1L)
                .email("mauricio@gmail.com")
                .build();

        updateRequestDTO = UpdateRequestDTO
                .builder()
                .email("mau@gmail.com")
                .password("12345678")
                .newPassword("987654321")
                .build();
    }

    @Test
    @Order(1)
    public void saveTest() {
        // Precondition
        given(userRepository.save(any(UserEntity.class))).willReturn(userEntity);

        // Action
        UserDTO userSaved = userService.save(loginRequestDTO);

        // Verify output
        assertThat(userSaved).isNotNull();
        assertThat(userSaved.email()).isEqualTo(expectUser.email());

        // verifica que se haya llamado al método del repositorio
        verify(userRepository, times(1)).save(userEntity);
        System.out.println("Test 1 successfully saved");
    }

    @Test
    @Order(2)
    public void testGetUser() {
        //precondition
        given(userRepository.findById(1L)).willReturn(Optional.of(userEntity));

        //action
        UserDTO user = userService.getUser(1L);

        //verify output
        assertThat(user).isNotNull();

        //verify
        verify(userRepository, times(1)).findById(1L);
        System.out.println("Test 2 successfully get user");
    }

    @Test
    @Order(3)
    public void testGetAllUsers() {
        //precondition
        given(userRepository.findAll()).willReturn(List.of(userEntity));

        //action
        List<UserDTO> users = userService.getAllUsers();

        //verify output
        assertThat(users).isNotNull();
        assertThat(users.size()).isGreaterThan(0);
        verify(userRepository, times(1)).findAll();
        System.out.println("Test 3 successfully get users");
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        //precondition
        given(userRepository.findById(userEntity.getId())).willReturn(Optional.of(userEntity));

        //action
        userService.deleteUser(userEntity.getId());

        //verify
        verify(userRepository, times(1)).delete(userEntity);
        System.out.println("Test 4 successfully deleted");
    }
}
