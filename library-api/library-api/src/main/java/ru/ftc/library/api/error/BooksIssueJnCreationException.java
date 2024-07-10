package ru.ftc.library.api.error;

public class BooksIssueJnCreationException extends EntityCreationException {

    public BooksIssueJnCreationException(Throwable cause) {
        super(cause);
    }

    public BooksIssueJnCreationException(String message) {
        super(message);
    }
}
