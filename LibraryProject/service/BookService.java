package com.example.LibraryProject.service;

import com.example.LibraryProject.controller.BookController;
import com.example.LibraryProject.models.Author;
import com.example.LibraryProject.models.Book;
import com.example.LibraryProject.models.enums.BookFilterType;
import com.example.LibraryProject.models.request.BookCreateRequest;
import com.example.LibraryProject.repository.AuthorRepositoryInterf;
import com.example.LibraryProject.repository.BookRepositoryInterf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepositoryInterf bookRepositoryInterf;

    @Autowired
    AuthorRepositoryInterf authorRepositoryInterf;

    private Author resolveAuthor(Author author) {
        Author authorFormDb = authorRepositoryInterf.findByEmail(author.getEmail());

        if(author == null) {
            authorFormDb = authorRepositoryInterf.save(author);
        }
        return authorFormDb;
    }

    public Book saveBook(BookCreateRequest bookCreateRequest) {
        Book book = bookCreateRequest.toBook();
        Author author = book.getAuthor();

        Author authorFromDb = authorRepositoryInterf.findByEmail(author.getEmail());
        if(authorFromDb == null) {
            authorFromDb = authorRepositoryInterf.save(author);
        }
        book.setAuthor(authorFromDb);
        return bookRepositoryInterf.save(book);
    }

    public List<Book> findBookByFilter(BookFilterType bookFilterType, String filterValue) {
        switch (bookFilterType) {
            case TITLE -> {
                return bookRepositoryInterf.findByTitle(filterValue);
            }

            case AUTHOR_NAME -> {
                return bookRepositoryInterf.findByAuthor_name(filterValue);
            }

            case COST -> {
                return bookRepositoryInterf.findByCost(Integer.parseInt(filterValue));
            }

            case GENRE -> {
                return bookRepositoryInterf.findByGenre(filterValue);
            }
        }
        return null;
    }

    public Book updateBook(int bookId, BookCreateRequest bookCreateRequest) {
        Book book = bookCreateRequest.toBook();

        Author authorResolver = resolveAuthor(book.getAuthor());

        book.setAuthor(authorResolver);
        book.setId(bookId);
        return bookRepositoryInterf.save(book);
    }

    public void deleteBookById(int bookId) {
        bookRepositoryInterf.deleteById(bookId);
    }

    public Book getBookById(int bookId) {
        return bookRepositoryInterf.getById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepositoryInterf.findAll();
    }

    public Optional<Book> findBookById(int bookId) {
        return bookRepositoryInterf.findById(bookId);
    }

    public Book save(Book book) {
        return bookRepositoryInterf.save(book);
    }
}
