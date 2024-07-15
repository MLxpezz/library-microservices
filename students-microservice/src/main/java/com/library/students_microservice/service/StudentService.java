package com.library.students_microservice.service;

import com.library.students_microservice.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO save(StudentDTO student);

    List<StudentDTO> findAll();

    StudentDTO findById(Long id);

    String deleteById(Long id);

    StudentDTO updateStudent(Long id, StudentDTO student);
}
