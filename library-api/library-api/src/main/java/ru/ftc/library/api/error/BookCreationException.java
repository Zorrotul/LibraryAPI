package ru.ftc.library.api.error;

public class BookCreationException extends EntityCreationException {

    public BookCreationException(Throwable cause) {
        super(cause);
    }

    public BookCreationException(String message) {
        super(message);
    }

}
