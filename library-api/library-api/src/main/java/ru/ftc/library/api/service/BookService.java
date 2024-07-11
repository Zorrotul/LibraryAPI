package ru.ftc.library.api.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.model.entities.AddBookRequest;
import ru.ftc.library.api.model.entities.Book;

public interface BookService {
    void addNewBookToLibrary(AddBookRequest newBook);

    Book getBookById(Long id);

    void removeOneBookCopyById(Long bookId);

    @Transactional(propagation = Propagation.REQUIRED)
    void returnBookToLibrary(Long bookId);
}
