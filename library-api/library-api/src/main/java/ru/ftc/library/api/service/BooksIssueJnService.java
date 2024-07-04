package ru.ftc.library.api.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.model.entities.BooksIssueJn;
import ru.ftc.library.api.model.entities.Reader;

import java.util.List;

public interface BooksIssueJnService {
    void createNewBooksIssueJn(BooksIssueJn newBooksIssueJn);

    void returnBook(BooksIssueJn newJournal);
}
