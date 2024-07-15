package ru.ftc.library.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ftc.library.api.jpa.BooksIssueJnEntity;
import ru.ftc.library.api.model.UpdateJnIssueRequest;
import ru.ftc.library.api.model.GetReportRequest;
import ru.ftc.library.api.service.BooksIssueJnService;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/booksIssueJn/")
public class BooksIssueJnController {

    private final BooksIssueJnService booksIssueJnService;

    @PostMapping("/addBooksIssueJn/")
    @ResponseStatus(HttpStatus.CREATED)
    void addBooksIssueJn(@RequestBody @Valid UpdateJnIssueRequest newJnIssue) {
        log.info("addBooksIssueJn <- newBooksIssueJn = {}", newJnIssue);
        booksIssueJnService.createNewBooksIssueJn(newJnIssue);
    }

    @PostMapping("/returnBook/")
    @ResponseStatus(HttpStatus.OK)
    void returnBook(@RequestBody @Valid UpdateJnIssueRequest newJnIssue) {//Скорее всего тут нужно передавать только ID
        log.info("returnBook <- returnedBook = {}", newJnIssue.getBookId());
        booksIssueJnService.returnBook(newJnIssue);
    }

    @GetMapping("/getReport/")
    @ResponseStatus(HttpStatus.OK)
    List<BooksIssueJnEntity> getReport(@RequestBody @Valid GetReportRequest reportRequest) {
        log.info("getReport <- request = {}", reportRequest);
        return booksIssueJnService.getJournal(reportRequest.getDateFrom(), reportRequest.getDateTo());
    }

}




