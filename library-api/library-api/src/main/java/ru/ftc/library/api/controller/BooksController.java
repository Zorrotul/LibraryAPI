package ru.ftc.library.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.error.BookCreationException;
import ru.ftc.library.api.model.AddBookRequest;
import ru.ftc.library.api.model.Book;
import ru.ftc.library.api.service.BookService;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/books/")
public class BooksController {

    private final BookService bookService;

    @PostMapping("/addBook/")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<String> addBook(@RequestBody @Valid AddBookRequest newBook) {
        try {
            log.info("addBook <- newBook = {}", newBook);
            bookService.addNewBookToLibrary(newBook);
            return new ResponseEntity<>(HttpStatus.CREATED.getReasonPhrase(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY.getReasonPhrase(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/getBook/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Book> getBook(@PathVariable Long id) {
        log.info("getBook <-");
        try {
            Book book = bookService.getBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new BookCreationException(e);
        }
    }
}




