package com.example.library.service;

import com.example.library.dto.BookRequest;
import com.example.library.exception.BookConflictException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository repository;   // dependency injected through the constructor

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Book> search(String author, String title) {
        if (author != null && !author.isBlank()) {
            return repository.findByAuthorContainingIgnoreCase(author.trim());
        }
        if (title != null && !title.isBlank()) {
            return repository.findByTitleContainingIgnoreCase(title.trim());
        }
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Book get(Long id) {
        return find(id);
    }

    public Book create(BookRequest req) {
        repository.findByIsbn(req.isbn().trim()).ifPresent(b -> {
            throw new BookConflictException("A book with ISBN " + req.isbn() + " already exists");
        });
        Book book = new Book(req.title().trim(), req.author().trim(), req.isbn().trim(), req.publishedYear());
        return repository.save(book);
    }

    public Book update(Long id, BookRequest req) {
        Book book = find(id);
        repository.findByIsbn(req.isbn().trim())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new BookConflictException("A book with ISBN " + req.isbn() + " already exists");
                });
        book.setTitle(req.title().trim());
        book.setAuthor(req.author().trim());
        book.setIsbn(req.isbn().trim());
        book.setPublishedYear(req.publishedYear());
        return book;   // saved automatically when the transaction commits
    }

    public Book checkout(Long id) {
        Book book = find(id);
        if (!book.isAvailable()) {
            throw new BookConflictException("Book " + id + " is already checked out");
        }
        book.setAvailable(false);
        return book;
    }

    public Book giveBack(Long id) {
        Book book = find(id);
        if (book.isAvailable()) {
            throw new BookConflictException("Book " + id + " is already in the library");
        }
        book.setAvailable(true);
        return book;
    }

    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Book find(Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }
}
