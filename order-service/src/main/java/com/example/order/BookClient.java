package com.example.order;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
public class BookClient {

    private final WebClient webClient;

    public BookClient(WebClient bookServiceClient) {
        this.webClient = bookServiceClient;
    }

    /** GET {base}/books/{id}. Emits the Book, or completes empty when BookService returns 404. */
    public Mono<Book> findById(long id) {
        return webClient.get()
                .uri("/books/{id}", id)
                .retrieve()
                .bodyToMono(Book.class)
                // retrieve() raises 404 as NotFound; translate to "no book" (empty Mono)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> Mono.empty());
    }
}
