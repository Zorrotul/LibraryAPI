package ru.ftc.library.api.service;

import ru.ftc.library.api.jpa.BooksIssueJnEntity;
import ru.ftc.library.api.model.UpdateJnIssueRequest;

import java.time.LocalDate;
import java.util.List;

public interface BooksIssueJnService {
    void createNewBooksIssueJn(UpdateJnIssueRequest newJnIssue);

    void returnBook(UpdateJnIssueRequest newJnIssue);

    List<BooksIssueJnEntity> getJournal(LocalDate dateFrom, LocalDate dateTo);
}
