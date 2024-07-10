package ru.ftc.library.api.service;

import ru.ftc.library.api.model.entities.AddBookRequest;
import ru.ftc.library.api.model.entities.Book;

public interface BookService {
    void addNewBookToLibrary(AddBookRequest newBook);

    Book getBookById(Long id);
}
