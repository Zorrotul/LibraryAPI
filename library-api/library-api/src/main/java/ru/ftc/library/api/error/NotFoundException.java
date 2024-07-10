package ru.ftc.library.api.error;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(String message) {
        super(message);
    }

}
