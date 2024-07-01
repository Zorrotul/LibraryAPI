package ru.ftc.library.api.jpa;

import java.io.Serializable;
import java.time.LocalDate;

public class BooksIssueJnKey implements Serializable {

    private Long readerId;

    private Long bookId;

    private LocalDate dateOfIssue;
}
