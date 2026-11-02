package com.example.order;

/** Mirror of BookService's JSON: {id, title, author}. */
public record Book(long id, String title, String author) {}
