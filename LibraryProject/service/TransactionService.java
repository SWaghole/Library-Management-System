package com.example.LibraryProject.service;

import com.example.LibraryProject.exception.TransactionalServiceException;
import com.example.LibraryProject.models.Book;
import com.example.LibraryProject.models.Student;
import com.example.LibraryProject.models.Transaction;
import com.example.LibraryProject.models.enums.TransactionType;
import com.example.LibraryProject.repository.TransactionRepositoryInterf;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepositoryInterf transactionRepositoryInterf;

    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    // Two types of transaction which are supported:
    //    1) Issue:
    //        - Check if a student is valid
    //        - Check if a book is valid
    //        - Check if a book is available
    //        - Make the book to student transaction
    //        - Make the book unavailable for others
    //    2) Return:
    //        - Check if a student is valid
    //        - Check if a book is valid
    //        - Check if the book is issued to the same student
    //        - Make the transaction

    public String transact(int studentId, int bookId, String transactionType) {
        Optional<Student> student = studentService.getStudent(studentId);

        if(student.isEmpty()) {
            throw new TransactionalServiceException("Student is not valid");
        }

        Optional<Book> bookById = bookService.findBookById(bookId);

        if(bookById.isEmpty()) {
            throw new TransactionalServiceException("Book is not valid");
        }

        return switch (transactionType) {
            case "ISSUE" -> issueBookTransaction(student, bookById);

            case "RETURN" -> returnBookTransaction(student, bookById);

            default -> throw new TransactionalServiceException("Transaction type is not supported.");

        };
    }

    private String issueBookTransaction(Optional<Student> student, Optional<Book> bookById) {
        if(bookById.get().getStudent() != null) {
            throw new TransactionalServiceException("Book is not available");
        }

        Transaction transaction = Transaction.builder()
                .externalId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .payment((double) bookById.get().getCost())
                .book(bookById.get()).student(student.get())
                .build();

        transactionRepositoryInterf.save(transaction);

        bookById.get().setStudent(student.get());
        bookService.save(bookById.get());

        return transaction.getExternalId();
    }

    private String returnBookTransaction(Optional<Student> student, Optional<Book> bookById) {
        // Check if the book is already issued
        if (bookById.get().getStudent() == null) {
            throw new TransactionalServiceException("Book is not issues to any student");
        }

        // Check if a book is issued to the same student
        if (bookById.get().getStudent().getId() != student.get().getId()) {
            throw new TransactionalServiceException("Book is issued to different student");
        }

        Transaction issueTransaction = transactionRepositoryInterf.findTopByBookAndStudentAndTransactionTypeOrderByIdDesc(
                bookById.get(), student.get(), TransactionType.ISSUE);

        Transaction transaction = Transaction.builder()
                .externalId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .payment((double) (bookById.get().getCost() - calculateFine(issueTransaction)))
                .book(bookById.get()).student(student.get())
                .build();
        transactionRepositoryInterf.save(transaction);

        bookById.get().setStudent(null);
        bookService.save(bookById.get());
        return transaction.getExternalId();
    }

    private long calculateFine(Transaction issueTransaction) {
        long bookIssueTime = issueTransaction.getCreatedOn().getTime();
        long bookReturnTime = System.currentTimeMillis();

        long diffInMillis = bookReturnTime - bookIssueTime;
        long daysPassed = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        if(daysPassed > 15) {
            return (daysPassed - 15) * 10L;
        }
        return 0;
    }
}
