package ru.ftc.library.api.error;

public class AuthorCreationException extends EntityCreationException {

    public AuthorCreationException(Throwable cause) {
        super(cause);
    }

    public AuthorCreationException(String message) {
        super(message);
    }


}
