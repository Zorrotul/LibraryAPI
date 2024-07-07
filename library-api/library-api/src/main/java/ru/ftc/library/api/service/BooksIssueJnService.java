package ru.ftc.library.api.service;

import ru.ftc.library.api.jpa.BooksIssueJnEntity;
import ru.ftc.library.api.model.entities.BooksIssueJn;
import ru.ftc.library.api.model.entities.JournalEntry;

import java.time.LocalDate;
import java.util.List;

public interface BooksIssueJnService {
    void createNewBooksIssueJn(BooksIssueJn newBooksIssueJn);

    void returnBook(BooksIssueJn newJournal);

    List<BooksIssueJnEntity> getJournal(LocalDate dateFrom, LocalDate dateTo);
}
