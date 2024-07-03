package com.library.users_microservice.utils;

import com.library.users_microservice.dto.StudentDTO;
import com.library.users_microservice.entities.StudentEntity;

import java.util.List;

public class StudentMapper {

    public static StudentDTO entityToDto(StudentEntity studentEntity) {
        return StudentDTO
                .builder()
                .id(studentEntity.getId())
                .phone(studentEntity.getPhone())
                .name(studentEntity.getName())
                .address(studentEntity.getAddress())
                .enrollmentNumber(studentEntity.getEnrollmentNumber())
                .lastname(studentEntity.getLastname())
                .email(studentEntity.getEmail())
                .build();
    }

    public static StudentEntity dtoToEntity(StudentDTO studentDTO) {
        return StudentEntity
                .builder()
                .address(studentDTO.address())
                .enrollmentNumber(studentDTO.enrollmentNumber())
                .lastname(studentDTO.lastname())
                .phone(studentDTO.phone())
                .name(studentDTO.name())
                .idLoan(null)
                .countLoans((byte) 0)
                .email(studentDTO.email())
                .build();
    }

    public static List<StudentDTO> listEntityToDto(List<StudentEntity> studentEntityList) {
        return studentEntityList
                .stream()
                .map(StudentMapper::entityToDto)
                .toList();
    }
}
