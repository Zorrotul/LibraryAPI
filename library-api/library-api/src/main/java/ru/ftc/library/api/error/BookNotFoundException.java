package ru.ftc.library.api.error;

public class BookNotFoundException extends NotFoundException {

    public BookNotFoundException(Throwable cause) {
        super(cause);
    }

    public BookNotFoundException(String message) {
        super(message);
    }

}
