package ru.ftc.library.api.error;

public class BookAuthorLinksCreationException extends EntityCreationException {

    public BookAuthorLinksCreationException(Throwable cause) {
        super(cause);
    }

    public BookAuthorLinksCreationException(String message){super(message);}
}
