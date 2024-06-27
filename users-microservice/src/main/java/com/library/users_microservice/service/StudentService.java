package com.library.users_microservice.service;

import com.library.users_microservice.entities.StudentEntity;

import java.util.List;

public interface StudentService {

    StudentEntity save(StudentEntity student);

    List<StudentEntity> findAll();

    StudentEntity findById(long id);

    String deleteById(long id);
}
