package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BookCreationException;
import ru.ftc.library.api.error.NoSuchBookException;
import ru.ftc.library.api.jpa.BookEntity;
import ru.ftc.library.api.jpa.BookRepository;
import ru.ftc.library.api.model.Sex;
import ru.ftc.library.api.model.entities.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceBean implements BookService {

    private final BookRepository bookRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewBookToLibrary(Book newBook) {
        BookEntity bookEntity = BookEntity.builder()
                .title(newBook.getTitle())
                .dateOfOPublication(newBook.getDateOfOPublication())
                .build();

        try {
            bookRepository.saveAndFlush(bookEntity);
        } catch (Exception e) {
            log.error("Cant create new book = {}, cause: {}", newBook, e.getMessage(), e);
            throw new BookCreationException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Book getBookById(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findBookById(id);
        log.info("pass optional\n");
        log.info("id: {}", id);
        return bookEntity
                .map(b -> Book.builder()
                        .title(b.getTitle())
                        .dateOfOPublication(b.getDateOfOPublication())
                        .numberOfOCopies(b.getNumberOfOCopies())
                        .build())
                .orElseThrow(()-> new NoSuchBookException("Book with id " + id + " not found"));
    }
}
