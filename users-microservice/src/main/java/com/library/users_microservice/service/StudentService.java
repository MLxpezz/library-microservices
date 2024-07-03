package com.library.users_microservice.service;

import com.library.users_microservice.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO save(StudentDTO student);

    List<StudentDTO> findAll();

    StudentDTO findById(long id);

    String deleteById(long id);

    StudentDTO updateStudent(Long id, StudentDTO student);
}
