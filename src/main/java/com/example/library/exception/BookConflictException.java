package com.example.library.exception;

/** Thrown when the request is valid but clashes with the current state (duplicate ISBN, book already checked out...). */
public class BookConflictException extends RuntimeException {
    public BookConflictException(String message) {
        super(message);
    }
}
