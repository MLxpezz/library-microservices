package com.library.students_microservice.dto;

import lombok.Builder;

@Builder
public record StudentDTO(Long id, String name, String lastname, String email, String phone, String enrollmentNumber, String address) {
}
