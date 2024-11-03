package com.library.auth_microservice.service;

import com.library.auth_microservice.TestDataProvider;
import com.library.auth_microservice.dto.AuthResponseDTO;
import com.library.auth_microservice.dto.UpdateRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;
import com.library.auth_microservice.exceptions.PasswordNotMatchesException;
import com.library.auth_microservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    @Order(1)
    public void saveTest() {
        //simulamos el comportamiento de nuestra dependencia y asi no usamos el metodo real
        //porque el dia de mañana los datos pueden cambiar
        when(userRepository.save(any(UserEntity.class))).thenReturn(TestDataProvider.userEntityProvider());
        when(passwordEncoder.encode("12345678")).thenReturn("encodedPassword");

        //obtenemos el resultado de la simulacion
        UserDTO expectedUser = userService.save(TestDataProvider.loginRequestDTOProvider());

        //verificamos que el resultado no sea nulo
        assertNotNull(expectedUser);
        //verificamos que el metodo se haya llamado almenos una vez
        verify(userRepository, times(1)).save(any(UserEntity.class));
        System.out.println("Test save successful");
    }

    @Test
    @Order(2)
    public void getUserTest() {
        //objetos o variables que ocupamos para el test
        Long id = 1L;

        //Simulamos el comportamiento que debe tener nuestro repository y lo que devuelve
        when(userRepository.findById(id)).thenReturn(Optional.of(TestDataProvider.userEntityProvider()));

        //obtenemos el resultado que debe devovler nuestro service
        UserDTO expectedUser = userService.getUser(id);

        //verificamos que no sea nulo
        assertNotNull(expectedUser);
        //verificamos que el metodo sea llamado almenos una vez
        verify(userRepository, times(1)).findById(id);
        System.out.println("Test getUser successful");
    }

    @Test
    @Order(3)
    public void getUserNotFoundTest() {
        Long id = 1L;

        assertThrows(EntityNotFoundException.class, () -> userService.getUser(id));
        System.out.println("Test getUserNotFound successful");
    }


    @Test
    @Order(4)
    public void getAllUsersTest() {

        //Simulamos el comportamiento del repository con la base de datos
        when(userRepository.findAll()).thenReturn(TestDataProvider.userEntityListProvider());

        //obtenemos el resultado que debe devolver nuestro service
        List<UserDTO> userList = userService.getAllUsers();

        //verificamos que la lista no venga nula
        assertNotNull(userList);
        //verificamos que la lista no este vacia
        assertThat(userList).isNotEmpty();
        //verificamos que el email del primer elemento sea igual al email proporcionado
        assertThat(userList.get(0).email()).isEqualTo("mauricio@gmail.com");
        //verificamos que el metodo sea llamado almenos una vez
        verify(userRepository, times(1)).findAll();
        System.out.println("Test findAll successful");
    }

    @Test
    @Order(5)
    public void deleteUserTest() {
        Long id = 1L;
        UserEntity user = TestDataProvider.userEntityProvider();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        String message = userService.deleteUser(id);

        assertThat(message).isEqualTo("User with id: " + id +" deleted");
        verify(userRepository, times(1)).deleteById(anyLong());
        System.out.println("Test delete successful");
    }

    @Test
    @Order(6)
    public void deleteUserNotFoundTest() {
        Long id = 1L;

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(id));

        System.out.println("Test deleteUserNotFound successful");
    }

    @Test
    @Order(7)
    public void getUserByEmailTest() {
        String email = "mauricio@gmail.com";

        when(userRepository.findUserEntityByEmail(email)).thenReturn(Optional.ofNullable(TestDataProvider.userEntityProvider()));

        UserDTO expectedUser = userService.getUserByEmail(email);

        assertNotNull(expectedUser);
        assertThat(expectedUser.email()).isEqualTo(email);
        verify(userRepository, times(1)).findUserEntityByEmail(email);
        System.out.println("Test getUserByEmail successful");
    }

    @Test
    @Order(8)
    public void getUserByEmailNotFoundTest() {
        String email = "mauricio@gmail.com";
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByEmail(email));
        System.out.println("Test getUserByEmailNotFound successful");
    }

    @Test
    @Order(9)
    public void updateUserTest() {
        Long id = 1L;
        UpdateRequestDTO updateUser = TestDataProvider.updateRequestDTOProdiver();
        UserEntity newUser = TestDataProvider.userEntityProvider();

        when(userRepository.findById(id)).thenReturn(Optional.of(TestDataProvider.userEntityProvider()));
        when(passwordEncoder.matches(updateUser.password(), newUser.getPassword())).thenReturn(true);
        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);
        when(authService.login(newUser.getEmail(), newUser.getPassword())).thenReturn(TestDataProvider.authResponseDTOProvider());

        AuthResponseDTO response = userService.updateUser(id, updateUser);

        assertNotNull(response);
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.token()).isNotEmpty();
        verify(authService, times(1)).login(newUser.getEmail(), newUser.getPassword());
        System.out.println("Test update successful");
    }

    @Test
    @Order(10)
    public void updateUserPasswordNotMatchesTest() {
        Long id = 1L;
        UpdateRequestDTO updatedUser = TestDataProvider.updateRequestDTOProdiver();
        UserEntity userStored = TestDataProvider.userEntityProvider();

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(userStored));
        assert userStored != null;
        when(passwordEncoder.matches(updatedUser.password(), userStored.getPassword())).thenReturn(false);

        assertThrows(PasswordNotMatchesException.class, () -> {
            userService.updateUser(id, updatedUser);
        });
        System.out.println("Test PasswordNotMatches successful");
    }

    @Test
    @Order(11)
    public void updateUserNewPasswordInvalidTest() {
        Long id = 1L;
        UpdateRequestDTO updatedUser = TestDataProvider.updateRequestDTOPasswordInvalidProdiver();
        UserEntity storedUser = TestDataProvider.userEntityProvider();

        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(TestDataProvider.userEntityProvider()));
        when(passwordEncoder.matches(updatedUser.password(), storedUser.getPassword())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(id, updatedUser);
        });

        System.out.println("Test updateUserNewPassword Successful");
    }
}
