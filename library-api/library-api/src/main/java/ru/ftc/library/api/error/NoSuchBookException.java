package ru.ftc.library.api.error;

public class NoSuchBookException extends RuntimeException {

    public NoSuchBookException(Throwable cause) {
        super(cause);
    }

    public NoSuchBookException(String message){super(message);}
}
