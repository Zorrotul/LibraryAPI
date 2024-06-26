package ru.ftc.library.api.service;

import ru.ftc.library.api.jpa.AuthorEntity;
import ru.ftc.library.api.model.Author;

import java.util.List;

public interface AuthorService {
    void createNewAuthor(Author newAuthor);
    List<Author> getAllAuthors();
}
