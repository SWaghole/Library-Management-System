package com.example.LibraryProject.controller;

import com.example.LibraryProject.models.Book;
import com.example.LibraryProject.models.enums.BookFilterType;
import com.example.LibraryProject.models.request.BookCreateRequest;
import com.example.LibraryProject.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping()
    public ResponseEntity saveBook(@Valid @RequestBody BookCreateRequest bookCreateRequest) {
        return new ResponseEntity(bookService.saveBook(bookCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Book> searchBook(@RequestParam("filterType") BookFilterType bookFilterType,
                                 @RequestParam("filterValue") String filterValue) {
        return bookService.findBookByFilter(bookFilterType, filterValue);
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity updateBook(@PathVariable int bookId, @RequestBody BookCreateRequest bookCreateRequest) {
        return new ResponseEntity(bookService.updateBook(bookId, bookCreateRequest), HttpStatus.OK);
    }

    @DeleteMapping("delete/{bookId}")
    public ResponseEntity deleteBook(@PathVariable int bookId) {
        bookService.deleteBookById(bookId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getBookById/{bookId}")
    public ResponseEntity getBookById(@PathVariable int bookId) {
        return new ResponseEntity(bookService.getBookById(bookId), HttpStatus.OK);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity getAllBooks() {
        return new ResponseEntity(bookService.getAllBooks(), HttpStatus.OK);
    }
}
