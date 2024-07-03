package com.library.users_microservice.service;

import com.library.users_microservice.dto.StudentDTO;
import com.library.users_microservice.entities.StudentEntity;
import com.library.users_microservice.repository.StudentRepository;
import com.library.users_microservice.utils.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO save(StudentDTO student) {
        StudentEntity newUser = StudentMapper.dtoToEntity(student);
        return StudentMapper.entityToDto(studentRepository.save(newUser));
    }

    @Override
    public List<StudentDTO> findAll() {
        return StudentMapper.listEntityToDto(studentRepository.findAll());
    }

    @Override
    public StudentDTO findById(long id) {
        StudentEntity student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
        return StudentMapper.entityToDto(student);
    }

    @Override
    public String deleteById(long id) {
        studentRepository.deleteById(id);
        return "Student with id " + id + " deleted";
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        StudentEntity student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " not found"));
        student.setAddress(studentDTO.address());
        student.setEmail(studentDTO.email());
        student.setName(studentDTO.name());
        student.setPhone(studentDTO.phone());
        student.setLastname(studentDTO.lastname());
        student.setEnrollmentNumber(studentDTO.enrollmentNumber());
        return StudentMapper.entityToDto(studentRepository.save(student));
    }
}
