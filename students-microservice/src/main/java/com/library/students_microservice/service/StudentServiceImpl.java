package com.library.students_microservice.service;

import com.library.students_microservice.dto.StudentDTO;
import com.library.students_microservice.entities.StudentEntity;
import com.library.students_microservice.repository.StudentRepository;
import com.library.students_microservice.utils.StudentMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    private StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante con id: " + id + " no existe."));
    }

    @Override
    public StudentDTO save(StudentDTO student) {
        StudentEntity newUser = StudentMapper.dtoToEntity(student);
        newUser.setCountLoans((byte) 0);
        return StudentMapper.entityToDto(studentRepository.save(newUser));
    }

    @Override
    public List<StudentDTO> findAll() {
        return StudentMapper.listEntityToDto(studentRepository.findAll());
    }

    @Override
    public StudentDTO findById(Long id) {
        StudentEntity student = getStudentById(id);
        return StudentMapper.entityToDto(student);
    }

    @Override
    public String deleteById(Long id) {
        if(id != null) {
            StudentEntity student = getStudentById(id);
            studentRepository.delete(student);
        }

        return "Estudiante con id: " + id + " eliminado.";
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        StudentEntity student = getStudentById(id);
        student.setAddress(studentDTO.address());
        student.setEmail(studentDTO.email());
        student.setName(studentDTO.name());
        student.setPhone(studentDTO.phone());
        student.setLastname(studentDTO.lastname());
        student.setEnrollmentNumber(studentDTO.enrollmentNumber());
        return StudentMapper.entityToDto(studentRepository.save(student));
    }
}
