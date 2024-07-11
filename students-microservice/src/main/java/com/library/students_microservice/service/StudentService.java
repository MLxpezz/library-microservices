package com.library.students_microservice.service;

import com.library.students_microservice.dto.StudentDTO;

import java.util.List;

public interface StudentService {

    StudentDTO save(StudentDTO student);

    List<StudentDTO> findAll();

    StudentDTO findById(long id);

    String deleteById(long id);

    StudentDTO updateStudent(Long id, StudentDTO student);
}
