package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.AuthorCreationException;
import ru.ftc.library.api.jpa.AuthorEntity;
import ru.ftc.library.api.jpa.AuthorRepository;
import ru.ftc.library.api.model.entities.Author;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceBean implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createNewAuthor(Author newAuthor) {
        AuthorEntity authorEntity = AuthorEntity.builder()
                .name(newAuthor.getName())
                .surname(newAuthor.getSurname())
                .patronymic(newAuthor.getPatronymic())
                .build();
        try {
            authorRepository.saveAndFlush(authorEntity);
        } catch (Exception e) {
            log.error("Cant create new author = {}, cause: {}", newAuthor, e.getMessage(), e);
            throw new AuthorCreationException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.getAuthors().stream()
                .map(a -> Author.builder()
                        .name(a.getName())
                        .surname(a.getSurname())
                        .patronymic(a.getPatronymic())
                        .build())
                .collect(Collectors.toList());
    }

}
