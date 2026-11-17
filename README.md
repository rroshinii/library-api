# Library API

A REST API for managing a library's books, built with Spring Boot, Spring Data JPA and H2.

## Requirements
- JDK 17+
- Maven (or open in IntelliJ IDEA, which includes it)

## Run

    mvn spring-boot:run

The API runs at http://localhost:8081 and starts with 3 sample books.

## Endpoints

| Method | URL | Description | Success |
|--------|-----|-------------|---------|
| GET | /api/books | List all books (`?author=` or `?title=` to search) | 200 |
| GET | /api/books/{id} | Get one book | 200 |
| POST | /api/books | Add a book | 201 |
| PUT | /api/books/{id} | Update a book | 200 |
| POST | /api/books/{id}/checkout | Mark a book as checked out | 200 |
| POST | /api/books/{id}/return | Mark a book as returned | 200 |
| DELETE | /api/books/{id} | Delete a book | 204 |

## Error responses

| Status | When |
|--------|------|
| 400 | Invalid input (blank title, author or ISBN...) |
| 404 | Book id does not exist |
| 409 | Duplicate ISBN, or checking out a book that is already out |

## Example

    POST /api/books
    {
      "title": "Head First Java",
      "author": "Kathy Sierra",
      "isbn": "978-0596009205",
      "publishedYear": 2005
    }

Ready-made requests are in `requests.http` (run them from IntelliJ).

## Tests

    mvn test
