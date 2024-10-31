package com.library.auth_microservice.service;

import com.library.auth_microservice.TestDataProvider;
import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;
import com.library.auth_microservice.repository.UserRepository;
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
        System.out.println("Test findById successful");
    }

    @Test
    @Order(3)
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

}
