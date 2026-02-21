package com.example.LibraryProject.repository;

import com.example.LibraryProject.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepositoryInterf extends JpaRepository<Author, Integer> {
    Author findByEmail(String email);
}
