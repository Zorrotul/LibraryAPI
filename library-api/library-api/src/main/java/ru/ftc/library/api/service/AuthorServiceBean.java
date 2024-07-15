package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.AuthorCreationException;
import ru.ftc.library.api.jpa.AuthorEntity;
import ru.ftc.library.api.jpa.AuthorRepository;
import ru.ftc.library.api.model.Author;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorServiceBean implements AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long findOrCreateNewAuthorAndGetId(Author newAuthor) {
        return getAuthorId(newAuthor)
                .orElseGet(() -> createNewAuthor(newAuthor).getId()
                );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AuthorEntity createNewAuthor(Author newAuthor) {
        try {
            AuthorEntity authorEntity = AuthorEntity.builder()
                    .name(newAuthor.getName())
                    .surname(newAuthor.getSurname())
                    .patronymic(newAuthor.getPatronymic())
                    .build();
            authorRepository.saveAndFlush(authorEntity);
            return authorEntity;
        } catch (Exception e) {
            log.error("Cant create new author = {},\n exception ={},\n cause: {}", newAuthor, e.getClass(), e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
            throw new AuthorCreationException(e);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<Long> getAuthorId(Author author) {
        try {
            return authorRepository.findByNameAndSurnameAndPatronymic(
                            author.getName(),
                            author.getSurname(),
                            author.getPatronymic())
                    .map(a -> {
                        log.info("find author = {}", a);
                        return a;
                    })
                    .map(AuthorEntity::getId)
                    .map(a -> {
                        log.info("author id={}", a);
                        return a;
                    });

        } catch (Exception e) {
            log.error("Cant findByNameAndSurnameAndPatronymic author = {}, cause: {}", e.getMessage(), e);
            throw new AuthorCreationException(e); //TODO не выкидывать эксепшн в стриме
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
