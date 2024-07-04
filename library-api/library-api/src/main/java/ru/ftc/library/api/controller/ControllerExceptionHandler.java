package ru.ftc.library.api.controller;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.ftc.library.api.error.EntityCreationException;
import ru.ftc.library.api.model.ErrorResponse;

import java.sql.SQLNonTransientException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityCreationException.class)
    public ResponseEntity<ErrorResponse> readerAlreadyExists(EntityCreationException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .errorMessage(e.getMessage())
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

//    @ExceptionHandler(SQLNonTransientException.class)
//    public ResponseEntity<ErrorResponse> foreignKeyException(SQLNonTransientException e) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ErrorResponse.builder()
//                        .errorMessage(e.getMessage())
//                        .timestamp(LocalDateTime.now())
//                        .build());
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorResponse> foreignKeyException2(RuntimeException e) {
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ErrorResponse.builder()
//                        .errorMessage(e.getMessage())
//                        .timestamp(LocalDateTime.now())
//                        .build());
//    }

}
