package com.example.LibraryProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.LibraryProject.models.Book;
import com.example.LibraryProject.models.Student;
import com.example.LibraryProject.models.Transaction;
import com.example.LibraryProject.models.enums.TransactionType;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepositoryInterf extends JpaRepository<Transaction, Integer> {

    Transaction findTopByBookAndStudentAndTransactionTypeOrderByIdDesc(Book book, Student student, TransactionType transactionType);
}