package com.example.LibraryProject.repository;

import com.example.LibraryProject.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepositoryInterf extends JpaRepository<Book, Integer> {
    List<Book> findByTitle(String name);
    List<Book> findByCost(int cost);
    List<Book> findByAuthor_name(String authorName);
    List<Book> findByGenre(String genre);
}
