package ru.ftc.library.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.ftc.library.api.error.AuthorCreationException;
import ru.ftc.library.api.error.NoSuchBookException;
import ru.ftc.library.api.error.ReaderCreationException;
import ru.ftc.library.api.model.ErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ReaderCreationException.class)
    public ResponseEntity<ErrorResponse> readerAlreadyExists(ReaderCreationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .errorMessage("reader already exists")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(AuthorCreationException.class)
    public ResponseEntity<ErrorResponse> AuthorAlreadyExists(AuthorCreationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .errorMessage("author already exists")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> invalidRequest(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .errorMessage(errors.toString())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(NoSuchBookException.class)
    public ResponseEntity<ErrorResponse> noSuchBook(NoSuchBookException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .errorMessage(e.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}
