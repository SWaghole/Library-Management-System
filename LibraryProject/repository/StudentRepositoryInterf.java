package com.example.LibraryProject.repository;

import com.example.LibraryProject.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryInterf extends JpaRepository<Student, Integer> {

}
