package ru.ftc.library.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ftc.library.api.error.BookCreationException;
import ru.ftc.library.api.error.NoSuchBookException;
import ru.ftc.library.api.jpa.AuthorRepository;
import ru.ftc.library.api.jpa.BookAuthorLinksRepository;
import ru.ftc.library.api.jpa.BookEntity;
import ru.ftc.library.api.jpa.BookRepository;
import ru.ftc.library.api.model.entities.AddBookRequest;
import ru.ftc.library.api.model.entities.Author;
import ru.ftc.library.api.model.entities.Book;
import ru.ftc.library.api.model.entities.BookAuthorLinks;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceBean implements BookService {

    private final BookRepository bookRepository;
    private final AuthorServiceBean authorService;
    private final BookAuthorLinksServiceBean bookAuthorLinksService;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewBookToLibrary(AddBookRequest newBook) {
        try {
            BookEntity bookEntity = bookRepository.findByTitleAndDateOfOPublication(newBook.getTitle(), newBook.getDateOfOPublication())
                    .map(book -> {
                        book.setNumberOfOCopies(book.getNumberOfOCopies() + newBook.getNumberOfOCopies());
                        return book;
                    })
                    .orElse(BookEntity.builder()
                            .title(newBook.getTitle())
                            .dateOfOPublication(newBook.getDateOfOPublication())
                            .numberOfOCopies(newBook.getNumberOfOCopies())
                            .build());
            bookRepository.saveAndFlush(bookEntity);

            Long bookId = bookRepository.findByTitleAndDateOfOPublication(newBook.getTitle(), newBook.getDateOfOPublication()).get().getId();
            log.info("book id = {}",bookId);
            log.info("authors = {}",newBook.getAuthors());

            newBook.getAuthors().stream()
                    .peek(a->log.info("authors  = {}",a))
                    .map(author -> authorService.createNewAuthorAndGetId(new Author(author.getName(), author.getSurname(), author.getPatronymic())))
                    .peek(a->log.info("author Id = {}",a))
                    .peek(authorId -> {
                        bookAuthorLinksService.createNewBookAuthorLink(new BookAuthorLinks(bookId, authorId));
                    }).collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Cant create new book = {}, cause: {}", newBook, e.getMessage(), e);
            throw new BookCreationException(e);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Book getBookById(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        log.info("pass optional\n");
        log.info("id: {}", id);
        return bookEntity
                .map(b -> Book.builder()
                        .title(b.getTitle())
                        .dateOfOPublication(b.getDateOfOPublication())
                        .numberOfOCopies(b.getNumberOfOCopies())
                        .build())
                .orElseThrow(() -> new NoSuchBookException("Book with id " + id + " not found"));
    }
}
