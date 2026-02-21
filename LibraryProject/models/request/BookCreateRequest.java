package com.example.LibraryProject.models.request;

import com.example.LibraryProject.models.Author;
import com.example.LibraryProject.models.Book;
import com.example.LibraryProject.models.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookCreateRequest {

    @NotBlank
    private String title;

    @Positive
    private int cost;

    @NotNull
    private Genre genre;

    @NotNull
    private Author author;

    public Book toBook() {
        return Book.builder()
                .title(this.title)
                .cost(this.cost)
                .genre(this.genre)
                .author(this.author)
                .build();
    }
}
