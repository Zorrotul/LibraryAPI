package ru.ftc.library.api.error;

public abstract class EntityCreationException extends RuntimeException {
    public EntityCreationException(Throwable cause) {
        super(cause);
    }

    public EntityCreationException(String message) {
        super(message);
    }

}
