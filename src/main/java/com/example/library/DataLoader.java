package com.example.library;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Adds 3 sample books on startup if the database is empty, so the API has something to show. */
@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository repository;

    public DataLoader(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Book("Clean Code", "Robert C. Martin", "978-0132350884", 2008));
            repository.save(new Book("Effective Java", "Joshua Bloch", "978-0134685991", 2018));
            repository.save(new Book("The Pragmatic Programmer", "Andrew Hunt", "978-0135957059", 2019));
        }
    }
}
