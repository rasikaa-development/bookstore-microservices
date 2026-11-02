package com.example.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestController
public class OrderController {

    private final BookClient bookClient;

    public OrderController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @GetMapping("/orders/create/{bookId}")
    public Mono<ResponseEntity<String>> createOrder(@PathVariable long bookId) {
        return bookClient.findById(bookId)
                .map(book -> ResponseEntity.ok("Order created successfully for book: " + book.title()))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Book not found, order cannot be created."))
                .onErrorResume(WebClientRequestException.class, e -> Mono.just(
                        ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("BookService is unavailable, order cannot be created.")))
                .onErrorResume(WebClientResponseException.class, e -> Mono.just(
                        ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                .body("BookService error (" + e.getStatusCode().value() + "), order cannot be created.")));
    }
}
