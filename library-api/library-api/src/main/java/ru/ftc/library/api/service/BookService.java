package ru.ftc.library.api.service;

import ru.ftc.library.api.model.entities.Book;

public interface BookService {
    void addNewBookToLibrary(Book newBook);
    Book getBookById(Long id);
}
