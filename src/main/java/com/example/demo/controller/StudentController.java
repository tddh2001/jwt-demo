package com.example.demo.controller;


import com.example.demo.model.db.Student;
import com.example.demo.model.request.StudentRequest;
import com.example.demo.model.request.UpdateStudentRequest;
import com.example.demo.model.response.StudentResponse;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public List<Student> listStudent() {
        return studentService.getAllStudent();
    }

    @PostMapping("/")
    public StudentResponse createStudent(@RequestBody StudentRequest request) {
        return studentService.createStudent(request);
    }

    @DeleteMapping("/{id}")
    public StudentResponse deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }

    @PutMapping("/")
    public StudentResponse updateStudent(@RequestBody UpdateStudentRequest request) {
        return studentService.updateStudent(request);
    }
}
