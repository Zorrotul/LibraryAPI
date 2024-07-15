package ru.ftc.library.api.service;

import ru.ftc.library.api.model.Author;

import java.util.List;

public interface AuthorService {

    Long findOrCreateNewAuthorAndGetId(Author newAuthor);

    List<Author> getAllAuthors();
}
