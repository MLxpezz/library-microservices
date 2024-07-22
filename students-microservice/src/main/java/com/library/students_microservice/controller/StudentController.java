package com.library.students_microservice.controller;

import com.library.students_microservice.dto.StudentDTO;
import com.library.students_microservice.service.StudentService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> createStudent(@RequestBody @Valid StudentDTO student) {
        return ResponseEntity.ok(studentService.save(student));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDTO student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    @PutMapping("/student-loan/{id}")
    public ResponseEntity<?> studentLoan(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.studentLoan(id));
    }

    @PutMapping("/student-return-book/{id}")
    public ResponseEntity<?> studentReturnBook(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.studentReturnBook(id));
    }
}
