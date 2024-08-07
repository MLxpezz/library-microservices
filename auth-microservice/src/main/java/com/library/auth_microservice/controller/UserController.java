package com.library.auth_microservice.controller;

import com.library.auth_microservice.config.jwt.JwtUtils;
import com.library.auth_microservice.dto.AuthResponseDTO;
import com.library.auth_microservice.dto.LoginRequestDTO;
import com.library.auth_microservice.dto.UpdateRequestDTO;
import com.library.auth_microservice.dto.UserDTO;
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

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

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
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password())
            );
            String token = jwtUtils.createToken(authentication);

            AuthResponseDTO authResponse = AuthResponseDTO
                    .builder()
                    .message("Autenticacion exitosa!")
                    .token(token)
                    .isSuccess(true)
                    .build();

            return ResponseEntity.ok(authResponse);

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
