package ru.ftc.library.api.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.model.entities.Author;

import java.util.List;

public interface AuthorService {
    void createNewAuthor(Author newAuthor);

    @Transactional(propagation = Propagation.REQUIRED)
    Long createNewAuthorAndGetId(Author newAuthor);

    List<Author> getAllAuthors();
}
