package com.library.users_microservice.controller;

import com.library.users_microservice.entities.StudentEntity;
import com.library.users_microservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestBody StudentEntity student) {
        return ResponseEntity.ok(studentService.save(student));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteById(id));
    }
}
