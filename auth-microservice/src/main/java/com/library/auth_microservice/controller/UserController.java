package com.library.auth_microservice.controller;

import com.library.auth_microservice.config.jwt.JwtUtils;
import com.library.auth_microservice.dto.AuthResponseDTO;
import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UpdateRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
import com.library.auth_microservice.entity.UserEntity;
import com.library.auth_microservice.service.AuthService;
import com.library.auth_microservice.service.UserService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthService authService;

    public UserController(UserService userService, JwtUtils jwtUtils, AuthService authService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> info(Authentication authentication) {
        try {
            UserDTO user = userService.getUserByEmail(authentication.getName());
            return ResponseEntity.ok(Map.of("email", user.email(), "id", user.id()));
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no fue encontrado.");
        }
    }

    @PostMapping("/microservice-token")
    public ResponseEntity<?> generateMicroserviceToken() {
        String token = jwtUtils.createMicroserviceToken("msvc-loans");
        return ResponseEntity.ok(token);
    }


    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        String token = authHeader.substring(7);

        try {
            String emailFromToken = jwtUtils.getUsernameFromToken(token);
            UserDTO userDTO = userService.getUserByEmail(emailFromToken);

            if (userDTO != null && jwtUtils.validateToken(token)) {
                return ResponseEntity.ok(true);
            }
        } catch (EntityNotFoundException | JwtException exception) {
            System.out.println("Ocurrio un error: " + exception.getMessage());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(userService.save(loginRequestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        try {
            return ResponseEntity.ok(authService.login(loginRequestDTO.email(), loginRequestDTO.password()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error de autenticación: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateRequestDTO updateRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(id, updateRequestDTO));
    }
}
