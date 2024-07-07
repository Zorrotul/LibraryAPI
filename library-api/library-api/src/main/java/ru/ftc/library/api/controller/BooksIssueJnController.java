package ru.ftc.library.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.jpa.BooksIssueJnEntity;
import ru.ftc.library.api.model.entities.BooksIssueJn;
import ru.ftc.library.api.model.entities.GetReportRequest;
import ru.ftc.library.api.model.entities.JournalEntry;
import ru.ftc.library.api.service.BooksIssueJnService;

import java.time.LocalDate;
import java.util.List;

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
    void returnBook(@RequestBody @Valid BooksIssueJn newBooksIssueJn) {//Скорее всего тут нужно передавать только ID
        log.info("returnBook <- returnedBook = {}", newBooksIssueJn.getBookId());
        booksIssueJnsService.returnBook(newBooksIssueJn);
    }

    @GetMapping("/getReport/")
    @ResponseStatus(HttpStatus.OK)
    List<BooksIssueJnEntity> returnBook(@RequestBody @Valid GetReportRequest reportRequest) {
        log.info("getReport <- request = {}", reportRequest);
        return booksIssueJnsService.getJournal(reportRequest.getDateFrom(), reportRequest.getDateTo());
    }

}




