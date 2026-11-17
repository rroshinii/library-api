package com.example.library.controller;

import com.example.library.dto.BookRequest;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // GET /api/books                      -> all books
    // GET /api/books?author=martin        -> search by author
    // GET /api/books?title=java           -> search by title
    @GetMapping
    public List<Book> list(@RequestParam(required = false) String author,
                           @RequestParam(required = false) String title) {
        return service.search(author, title);
    }

    @GetMapping("/{id}")
    public Book get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookRequest request) {
        Book created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);   // 201 Created
    }

    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return service.update(id, request);
    }

    @PostMapping("/{id}/checkout")
    public Book checkout(@PathVariable Long id) {
        return service.checkout(id);
    }

    @PostMapping("/{id}/return")
    public Book giveBack(@PathVariable Long id) {
        return service.giveBack(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
