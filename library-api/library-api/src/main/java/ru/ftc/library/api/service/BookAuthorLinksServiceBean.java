package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BookAuthorLinksCreationException;
import ru.ftc.library.api.jpa.BookAuthorLinksEntity;
import ru.ftc.library.api.jpa.BookAuthorLinksRepository;
import ru.ftc.library.api.model.BookAuthorLinks;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookAuthorLinksServiceBean implements BookAuthorLinksService {

    private final BookAuthorLinksRepository bookAuthorLinksRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createNewBookAuthorLink(BookAuthorLinks newBookAuthorLinks) {
        try {
            bookAuthorLinksRepository.findByBookIdAndAuthorId(// язнаю что можно не делать данной проверки но я в логах избавлялся от ненуждных эксепшенов
                            newBookAuthorLinks.getBookId(),
                            newBookAuthorLinks.getAuthorId())
                    .map(a -> {
                        log.info("link: {} already exist", a);
                        return a;
                    })
                    .orElseGet(() -> {
                        createLink(newBookAuthorLinks.getBookId(),
                                newBookAuthorLinks.getAuthorId());
                        return null;//TODO how to delete?
                    });
        } catch (DataAccessException e) {
            log.error("bookAuthorLinks = {}, already exist, cause: {}", newBookAuthorLinks, e.getMessage());
            throw new BookAuthorLinksCreationException(e);
        } catch (Exception e) {
            log.error("Cant create new bookAuthorLinks = {}, cause: {}", newBookAuthorLinks, e.getMessage(), e);
            throw new BookAuthorLinksCreationException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    void createLink(Long bookId, Long authorId) {
        try {
            BookAuthorLinksEntity bookAuthorLinksEntity = BookAuthorLinksEntity.builder()
                    .bookId(bookId)
                    .authorId(authorId)
                    .build();
            bookAuthorLinksRepository.saveAndFlush(bookAuthorLinksEntity);
        } catch (Exception e) {
            log.error("Create link exception, cause: {}", e.getMessage());
        }
    }

}
