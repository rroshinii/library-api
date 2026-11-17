package com.example.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** The JSON a client sends when creating or updating a book. */
public record BookRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be at most 200 characters")
        String title,

        @NotBlank(message = "Author is required")
        @Size(max = 100, message = "Author must be at most 100 characters")
        String author,

        @NotBlank(message = "ISBN is required")
        @Size(max = 20, message = "ISBN must be at most 20 characters")
        String isbn,

        @Min(value = 1, message = "Published year must be positive")
        @Max(value = 2100, message = "Published year is too far in the future")
        Integer publishedYear
) {}
