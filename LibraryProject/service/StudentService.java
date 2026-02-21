package com.example.LibraryProject.service;

import com.example.LibraryProject.models.Student;
import com.example.LibraryProject.models.request.StudentCreateRequest;
import com.example.LibraryProject.repository.StudentRepositoryInterf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepositoryInterf studentRepositoryInterf;

    public Student saveStudent(StudentCreateRequest studentCreateRequest) {
        Student student = studentCreateRequest.toStudent();
        return studentRepositoryInterf.save(student);
    }

    public Optional<Student> getStudent(int studentId) {
        return studentRepositoryInterf.findById(studentId);
    }
}
