package com.example.LibraryProject.controller;

import com.example.LibraryProject.models.Student;
import com.example.LibraryProject.models.request.StudentCreateRequest;
import com.example.LibraryProject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentService studentService;


    @PostMapping()
    public ResponseEntity<Student> saveStudent(@RequestBody StudentCreateRequest studentCreateRequest) {
        return new ResponseEntity<>(studentService.saveStudent(studentCreateRequest), HttpStatus.OK);
    }
}
