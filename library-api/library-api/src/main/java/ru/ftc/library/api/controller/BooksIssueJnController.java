package ru.ftc.library.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.model.entities.BooksIssueJn;
import ru.ftc.library.api.service.BooksIssueJnService;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/booksIssueJn/")
public class BooksIssueJnController {

    private final BooksIssueJnService booksIssueJnsService;

    @PostMapping("/addBooksIssueJn/")
    @ResponseStatus(HttpStatus.CREATED)
    void addBooksIssueJn(@RequestBody @Valid BooksIssueJn newBooksIssueJn) {
        log.info("addBooksIssueJn <- newBooksIssueJn = {}", newBooksIssueJn);
        booksIssueJnsService.createNewBooksIssueJn(newBooksIssueJn);
    }

    @PostMapping("/returnBook/")
    @ResponseStatus(HttpStatus.OK)
    void returnBook(@RequestBody @Valid BooksIssueJn newBooksIssueJn) {
        log.info("returnBook <- returnedBook = {}", newBooksIssueJn.getBookId());
        booksIssueJnsService.returnBook(newBooksIssueJn);
    }

}




