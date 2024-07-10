package ru.ftc.library.api.service;

import ru.ftc.library.api.jpa.BooksIssueJnEntity;
import ru.ftc.library.api.model.entities.BooksIssueJnResponse;

import java.time.LocalDate;
import java.util.List;

public interface BooksIssueJnService {
    void createNewBooksIssueJn(BooksIssueJnResponse newBooksIssueJnResponse);

    void returnBook(BooksIssueJnResponse newJournal);

    List<BooksIssueJnEntity> getJournal(LocalDate dateFrom, LocalDate dateTo);
}
