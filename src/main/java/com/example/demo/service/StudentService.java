package com.example.demo.service;

import com.example.demo.constants.Constants;
import com.example.demo.model.db.Student;
import com.example.demo.model.request.StudentRequest;
import com.example.demo.model.request.UpdateStudentRequest;
import com.example.demo.model.response.StudentResponse;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.constants.Constants.STUDENT_UPDATE_SUCCESS;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public StudentResponse createStudent(StudentRequest request) {
        StudentResponse response = new StudentResponse();
        try {
            request.validate();
        } catch (IllegalArgumentException e) {
            response.setMessage(e.getMessage());
            return response;
        }
        Student student = new Student();
        student.setName(request.getName());
        student.setClassRoom(request.getClassRoom());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        student.setDescription(request.getDescription());
        studentRepository.save(student);
        response.setMessage(Constants.STUDENT_ADD_SUCCESS);
        return response;
    }

    public StudentResponse deleteStudent(Integer id) {
        StudentResponse response = new StudentResponse();
        try {
            studentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            response.setMessage(Constants.STUDENT_DOES_NOT_EXIST);
            return response;
        }
        response.setMessage(Constants.STUDENT_DELETE_SUCCESS);
        return response;
    }

    public StudentResponse updateStudent(UpdateStudentRequest request) {
        StudentResponse response = new StudentResponse();
        try {
            request.validate();
        } catch (IllegalArgumentException e) {
            response.setMessage(e.getMessage());
            return response;
        }

        Optional<Student> opt = studentRepository.findById(request.getId());
        if (!opt.isPresent()) {
            response.setMessage(Constants.STUDENT_DOES_NOT_EXIST);
            return response;
        }

        opt.map(
                student -> {
                    student.setName(request.getName());
                    student.setClassRoom(request.getClassRoom());
                    student.setPhone(request.getPhone());
                    student.setEmail(request.getEmail());
                    student.setDescription(request.getDescription());
                    studentRepository.save(student);
                    response.setMessage(STUDENT_UPDATE_SUCCESS);
                    return response;
                }
        );
        return response;
    }
}
