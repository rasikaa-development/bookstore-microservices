package com.example.book;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BookController {

    private final Map<Long, Book> books = Map.of(
            1L, new Book(1, "Clean Code", "Robert C. Martin"),
            2L, new Book(2, "Effective Java", "Joshua Bloch"),
            3L, new Book(3, "Spring in Action", "Craig Walls"));

    /** GET /books/{id} -> Book JSON, or 404 when missing. */
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        return book;
    }
}
